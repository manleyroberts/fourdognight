package fourdognight.github.com.casa.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import fourdognight.github.com.casa.R;
import fourdognight.github.com.casa.model.ModelFacade;

public class RegistrationActivity extends AppCompatActivity {

    private static final int MIN_PASSWORD_LENGTH = 8;

    private boolean taskActive = false;

    private EditText mUsernameView;
    private EditText mPasswordView;
    private EditText mPasswordView2;
    private EditText mNameView;
    private Switch mAdminSwitchView;

    private ModelFacade model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        model = ModelFacade.getInstance();

        mPasswordView = findViewById(R.id.regPasswordField);
        mUsernameView = findViewById(R.id.regUsernameField);
        mPasswordView2 = findViewById(R.id.regPasswordField2);
        mNameView = findViewById(R.id.regNameField);
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
        Editable editableEmail = mUsernameView.getText();
        String email = editableEmail.toString();
        Editable editablePassword = mPasswordView.getText();
        String password = editablePassword.toString();
        Editable editablePassword2 = mPasswordView2.getText();
        String password2 = editablePassword2.toString();
        Editable editableName = mNameView.getText();
        String name = editableName.toString();
        boolean isAdmin = mAdminSwitchView.isChecked();
        Log.e("isAdmin ", isAdmin + " (RegistrationActivity.java: 91)");
        editablePassword.clear();
        editablePassword2.clear();

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