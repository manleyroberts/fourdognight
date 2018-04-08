package fourdognight.github.com.casa.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

import fourdognight.github.com.casa.R;
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

    private ModelFacade model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        model = ModelFacade.getInstance();

        mPasswordView = findViewById(R.id.passwordField);
        mUsernameView = findViewById(R.id.usernameField);
        View mSuccessText = findViewById(R.id.successText);
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        mSuccessText.setVisibility((Boolean) (getIntent().getExtras().get("justRegistered"))
                ? View.VISIBLE : View.GONE);

        final Button button = findViewById(R.id.loginSubmitButton);
        button.setOnClickListener(v -> attemptLogin());

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
            Log.d("Login", "Task fired");
            model.attemptLogin(email, password, () -> {
                Intent launchIntent = new Intent(getInstance(), MainScreenActivity.class);
                startActivity(launchIntent);
                finish();
            }, () -> {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
                taskActive = false;
            });
        }
    }

    private LoginActivity getInstance() {
        return this;
    }
}

