package fourdognight.github.com.casa;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

import fourdognight.github.com.casa.model.AbstractUser;
import fourdognight.github.com.casa.model.ModelFacade;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private boolean taskActive = false;

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mUserTextView;
    private View mPassTextView;
    private View mLoginView;
    private View mProgressView;
    private View mSuccessText;

    private ModelFacade model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        model = ModelFacade.getInstance();

        mPasswordView = findViewById(R.id.passwordField);
        mUsernameView = findViewById(R.id.usernameField);
        mSuccessText = findViewById(R.id.successText);
        mLoginView = findViewById(R.id.loginLayout);
        mPassTextView = findViewById(R.id.passText);
        mUserTextView = findViewById(R.id.userText);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSuccessText.setVisibility((Boolean) (getIntent().getExtras().get("justRegistered"))
                ? View.VISIBLE : View.GONE);

        final Button button = findViewById(R.id.loginSubmitButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                attemptLogin();
            }
        });

        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (taskActive) {
            return;
        }
        taskActive = true;
        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password_short));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username
        if (TextUtils.isEmpty(email)) {
            mUsernameView.setError(getString(R.string.error_field_required));
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
            Log.d("Login", "Task fired");
            model.attemptLogin(this, email, password);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
        }
    }

    public void completeLogin(AbstractUser user) {
        if (user != null) {
            Log.d("I", "Logged in");
            Intent launchIntent = new Intent(getInstance(), MainScreenActivity.class);
            launchIntent.putExtra("currentUser", user);
            startActivity(launchIntent);
            finish();
        } else {
            Log.d("Login", "Tried");
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            mPasswordView.requestFocus();
        }
        taskActive = false;
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
            mLoginView.setVisibility(show ? View.GONE : View.VISIBLE);
            mPassTextView.setVisibility(show ? View.GONE : View.VISIBLE);
            mUserTextView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
    }

//    /**
//     * Represents an asynchronous login task used to authenticate
//     * the user.
//     */
//    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//
//        private final String mUsername;
//        private final String mPassword;
//        private AbstractUser loggedInUser = null;
//        private final ModelFacade model = new ModelFacade();
//
//        UserLoginTask(String username, String password) {
//            mUsername = username;
//            mPassword = password;
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
//            model.attemptLogin(getInstance(), mUsername, mPassword);
//            return loggedInUser != null;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            showProgress(false);
//
//            if (success) {
//                Log.d("I", "Logged in");
//                Intent launchIntent = new Intent(getInstance(), MainScreenActivity.class);
//                launchIntent.putExtra("currentUser", loggedInUser);
//                startActivity(launchIntent);
//                finish();
//            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
//        }
//    }

    private LoginActivity getInstance() {
        return this;
    }
}

