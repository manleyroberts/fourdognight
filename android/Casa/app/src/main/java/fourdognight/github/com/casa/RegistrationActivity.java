package fourdognight.github.com.casa;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

public class RegistrationActivity extends AppCompatActivity {

    private UserRegistrationTask mAuthTask = null;

    private static final int MIN_PASSWORD_LENGTH = 8;

    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mUserTextView;
    private View mPassTextView;
    private View mRegisView;
    private View mProgressView;
    private View mSuccessText;
    private EditText mPasswordView2;
    private View mPassTextView2;
    private EditText mNameView;
    private View mNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mPasswordView = findViewById(R.id.regPasswordField);
        mUsernameView = findViewById(R.id.regUsernameField);
        mRegisView = findViewById(R.id.regisLayout);
        mPassTextView = findViewById(R.id.regPassText);
        mUserTextView = findViewById(R.id.regUserText);
        mSuccessText = findViewById(R.id.successText);
        mPasswordView2 = findViewById(R.id.regPasswordField2);
        mPassTextView2 = findViewById(R.id.regPassText2);
        mNameView = findViewById(R.id.regNameField);
        mNameTextView = findViewById(R.id.regNameText);


        mSuccessText.setVisibility(View.GONE);
        mPasswordView2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        final Button button = findViewById(R.id.regisSubmitButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                attemptRegistration();
            }
        });

        mProgressView = findViewById(R.id.regis_progress);
    }

    private void attemptRegistration() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);
        mPasswordView2.setError(null);

        //Hide success text
        mSuccessText.setVisibility(View.GONE);

        // Store values at the time of the login attempt.
        String email = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String password2 = mPasswordView2.getText().toString();
        mPasswordView.getText().clear();
        mPasswordView2.getText().clear();

        boolean cancel = false;
        View focusView = null;

        //Check for password match
        if (!(password.equals(password2))) {
            mPasswordView.setError(getString(R.string.error_password_mismatch));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!cancel && password.length() < MIN_PASSWORD_LENGTH) {
            mPasswordView.setError(getString(R.string.error_invalid_password_short));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username
        if (!cancel && TextUtils.isEmpty(email)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        //Check for an at sign
        if (!cancel && email.indexOf('@') < 1 || email.indexOf('@') > email.length() - 2) {
            mUsernameView.setError(getString(R.string.error_invalid_email));
            focusView = mUsernameView;
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
            mAuthTask = new UserRegistrationTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
    public class UserRegistrationTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserRegistrationTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            if (UserVerificationModel.user_list.containsKey(mUsername)) {
                return false;
            }

            UserVerificationModel.user_list.put(mUsername, mPassword);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            mPasswordView.getText().clear();
            if (success) {
                Log.d("I", "Registered");
                mSuccessText.setVisibility(View.VISIBLE);
                LinearLayout myLayout = findViewById(R.id.regisLayout);
                myLayout.requestFocus();
            } else {
                mUsernameView.setError(getString(R.string.error_account_exists));
                mUsernameView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            mUsernameView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mPasswordView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
        // The ViewPropertyAnimator APIs are not available, so simply show
        // and hide the relevant UI components.
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mUsernameView.setVisibility(show ? View.GONE : View.VISIBLE);
        mPasswordView.setVisibility(show ? View.GONE : View.VISIBLE);
        mRegisView.setVisibility(show ? View.GONE : View.VISIBLE);
        mPassTextView.setVisibility(show ? View.GONE : View.VISIBLE);
        mUserTextView.setVisibility(show ? View.GONE : View.VISIBLE);
        mPassTextView2.setVisibility(show ? View.GONE : View.VISIBLE);
        mPasswordView2.setVisibility(show ? View.GONE : View.VISIBLE);
        mNameTextView.setVisibility(show ? View.GONE : View.VISIBLE);
        mNameView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private RegistrationActivity getInstance() {
        return this;
    }
}