package fourdognight.github.com.casa;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import fourdognight.github.com.casa.model.AbstractUser;
import fourdognight.github.com.casa.model.ModelFacade;
import fourdognight.github.com.casa.model.UserVerificationModel;

public class RegistrationActivity extends AppCompatActivity {

//    private UserRegistrationTask mAuthTask = null;

    private static final int MIN_PASSWORD_LENGTH = 8;

    private boolean taskActive = false;

    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mUserTextView;
    private View mPassTextView;
    private View mRegisView;
    private View mProgressView;
    private EditText mPasswordView2;
    private View mPassTextView2;
    private EditText mNameView;
    private View mNameTextView;
    private Switch mAdminSwitchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mPasswordView = findViewById(R.id.regPasswordField);
        mUsernameView = findViewById(R.id.regUsernameField);
        mRegisView = findViewById(R.id.regisLayout);
        mPassTextView = findViewById(R.id.regPassText);
        mUserTextView = findViewById(R.id.regUserText);
        mPasswordView2 = findViewById(R.id.regPasswordField2);
        mPassTextView2 = findViewById(R.id.regPassText2);
        mNameView = findViewById(R.id.regNameField);
        mNameTextView = findViewById(R.id.regNameText);
        mAdminSwitchView = (Switch) findViewById(R.id.adminSwitch);

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
        if (taskActive) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);
        mPasswordView2.setError(null);

        // Store values at the time of the login attempt.
        String email = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String password2 = mPasswordView2.getText().toString();
        String name = mNameView.getText().toString();
        boolean isAdmin = mAdminSwitchView.isChecked();
        Log.e("isAdmin ", isAdmin + " (RegistrationActivity.java: 91)");
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

        ModelFacade model = new ModelFacade();
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            taskActive = true;
            showProgress(true);
            model.attemptRegistration(getInstance(), name, email, password, isAdmin);
//            mAuthTask = new UserRegistrationTask(name, email, password, isAdmin);
//            mAuthTask.execute((Void) null);
        }
    }

    public void completeRegistrationSuccess() {
        Log.d("I", "Registered");
        Intent launchIntent = new Intent(getInstance(), LoginActivity.class);
        launchIntent.putExtra("justRegistered", true);
        startActivity(launchIntent);
        finish();
        taskActive = false;
    }

    public void completeRegistrationFailed() {
        mUsernameView.setError(getString(R.string.error_account_exists));
        mUsernameView.requestFocus();
        taskActive = false;
    }


//    /**
//     * Represents an asynchronous login task used to authenticate
//     * the user.
//     */
//    public class UserRegistrationTask extends AsyncTask<Void, Void, Boolean> {
//
//        private final String mUsername;
//        private final String mPassword;
//        private final String mName;
//        private final boolean mIsAdmin;
//
//        UserRegistrationTask(String name, String username, String password, boolean isAdmin) {
//            mUsername = username;
//            mPassword = password;
//            mName = name;
//            mIsAdmin = isAdmin;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            // TODO: attempt authentication against a network service.
//
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return false;
//            }
//
//            return model.attemptRegistration(mName, mUsername, mPassword, mIsAdmin);
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            showProgress(false);
//            Log.d("I", "attempt");
//            mPasswordView.getText().clear();
//            if (success) {
//                Log.d("I", "Registered");
//                Intent launchIntent = new Intent(getInstance(), LoginActivity.class);
//                launchIntent.putExtra("justRegistered", true);
//                startActivity(launchIntent);
//                finish();
//            } else {
//                mUsernameView.setError(getString(R.string.error_account_exists));
//                mUsernameView.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
//        }
//    }

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
        mAdminSwitchView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private RegistrationActivity getInstance() {
        return this;
    }
}