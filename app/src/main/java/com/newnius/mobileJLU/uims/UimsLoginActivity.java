package com.newnius.mobileJLU.uims;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.newnius.mobileJLU.Config;
import com.newnius.mobileJLU.R;
import com.newnius.mobileJLU.uims.UimsGetStuInfoTask;
import com.newnius.mobileJLU.uims.UimsSession;
import com.newnius.mobileJLU.util.GeneralMethods;

/**
 * A login screen that offers login via email/password.
 */
public class UimsLoginActivity extends AppCompatActivity{

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText classNoView;
    private EditText passwordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        classNoView = (EditText) findViewById(R.id.classNo);
        populateAutoComplete();

        passwordView = (EditText) findViewById(R.id.password);

        Button signInButton = (Button) findViewById(R.id.uims_sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    //remember classNo and password and fill it
    private void populateAutoComplete() {
        return ;
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        classNoView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String classNo = classNoView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid classNo.
        if (TextUtils.isEmpty(classNo)) {
            classNoView.setError(getString(R.string.error_field_required));
            focusView = classNoView;
            cancel = true;
        } else if (!isClassNoValid(classNo)) {
            classNoView.setError(getString(R.string.error_invalid_classno));
            focusView = classNoView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(classNo, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isClassNoValid(String classNo) {
        return classNo.length()==8;
    }

    private boolean isPasswordValid(String password) {
        return password.length()>0;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String classNo;
        private final String password;

        UserLoginTask(String classNo, String password) {
            this.classNo = classNo;
            this.password = GeneralMethods.md5("UIMS" + classNo + password, false, false);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                final String data = "j_username=" + classNo + "&j_password=" + password;
                URL url = new URL("http://cjcx.jlu.edu.cn/score/action/security_check.php");
                if(!Config.getInCampus() && !Config.getCanAccessInternet()){
                    Toast.makeText(UimsLoginActivity.this, "网络不通", Toast.LENGTH_SHORT).show();
                    return false;
                }else if(Config.getInCampus()){
                    url = new URL("http://uims.jlu.edu.cn/ntms/j_spring_security_check");
                }

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setUseCaches(false);
                conn.setInstanceFollowRedirects(false);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.connect();

                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.writeBytes(data);
                out.flush();
                out.close();

                InputStream is = conn.getInputStream();
                if (conn.getHeaderField("Location").contains("index")) {
                    UimsSession.setClassNo(Integer.parseInt(classNo));
                    UimsSession.setPassword(password);
                    UimsSession.setCookie(conn.getHeaderField("Set-Cookie"));
                    return true;
                }
                is.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return false;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                new UimsGetStuInfoTask(UimsSession.getCookie()).execute();
                finish();
            } else {
                passwordView.setError(getString(R.string.error_incorrect_password));
                passwordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}