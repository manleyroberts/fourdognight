package fourdognight.github.com.casa.ui;

import android.annotation.TargetApi;
import android.content.Intent;
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

import fourdognight.github.com.casa.R;
import fourdognight.github.com.casa.model.ModelFacade;

public class RegistrationActivity extends AppCompatActivity {

    private static final int MIN_PASSWORD_LENGTH = 8;

    private boolean taskActive = false;

    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mUserTextView;
    private View mPassTextView;
    private View mRegistrationView;
    private View mProgressView;
    private EditText mPasswordView2;
    private View mPassTextView2;
    private EditText mNameView;
    private View mNameTextView;
    private Switch mAdminSwitchView;

    private ModelFacade model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        model = ModelFacade.getInstance();

        mPasswordView = findViewById(R.id.regPasswordField);
        mUsernameView = findViewById(R.id.regUsernameField);
        mRegistrationView = findViewById(R.id.regisLayout);
        mPassTextView = findViewById(R.id.regPassText);
        mUserTextView = findViewById(R.id.regUserText);
        mPasswordView2 = findViewById(R.id.regPasswordField2);
        mPassTextView2 = findViewById(R.id.regPassText2);
        mNameView = findViewById(R.id.regNameField);
        mNameTextView = findViewById(R.id.regNameText);
        mAdminSwitchView = findViewById(R.id.adminSwitch);

        mPasswordView2.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptRegistration();
                return true;
            }
            return false;
        });

        final Button button = findViewById(R.id.regisSubmitButton);
        button.setOnClickListener(v -> attemptRegistration());

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

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            taskActive = true;
            Log.d("Attempt", "RegistrationAttempt");
            model.attemptRegistration(name, email, password, isAdmin, () -> {
                Log.d("I", "Registered");
                Intent launchIntent = new Intent(getInstance(), LoginActivity.class);
                launchIntent.putExtra("justRegistered", true);
                startActivity(launchIntent);
                finish();
                taskActive = false;
            },
                    () -> {
                        mUsernameView.setError(getString(R.string.error_account_exists));
                        mUsernameView.requestFocus();
                        taskActive = false;
                    });
        }
    }

    private RegistrationActivity getInstance() {
        return this;
    }
}