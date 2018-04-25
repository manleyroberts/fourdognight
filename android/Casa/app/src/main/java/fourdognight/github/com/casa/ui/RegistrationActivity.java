package fourdognight.github.com.casa.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import fourdognight.github.com.casa.R;
import fourdognight.github.com.casa.model.ModelFacade;

/**
 * registration page to create an account
 * @author Manley Roberts
 * @version 1.0
 */
public class RegistrationActivity extends AppCompatActivity {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MINIMUM_AT_CHAR_INDEX = 1;

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
            if ((id == EditorInfo.IME_ACTION_DONE) || (id == EditorInfo.IME_NULL)) {
                attemptRegistration();
                return true;
            }
            return false;
        });

        final Button button = findViewById(R.id.regisSubmitButton);
        button.setOnClickListener(v -> attemptRegistration());

    }

    private void attemptRegistration() {
        if (!taskActive) {

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
            editablePassword.clear();
            editablePassword2.clear();
            View focusView;

            //Check for password match
            if (!(password.equals(password2))) {
                mPasswordView.setError(getString(R.string.error_password_mismatch));
                focusView = mPasswordView;
            } else if (password.length() < MIN_PASSWORD_LENGTH) {
                mPasswordView.setError(getString(R.string.error_invalid_password_short));
                focusView = mPasswordView;
            } else if (TextUtils.isEmpty(email)) {
                mUsernameView.setError(getString(R.string.error_field_required));
                focusView = mUsernameView;
            } else if (email.indexOf('@') < MINIMUM_AT_CHAR_INDEX) {
                mUsernameView.setError(getString(R.string.error_invalid_email));
                focusView = mUsernameView;
            } else {
                taskActive = true;
                focusView = mUsernameView;

                //Send a registration attempt to the model.
                model.attemptRegistration(name, email, password, isAdmin, () -> {
                    Intent launchIntent = new Intent(getInstance(), LoginActivity.class);
                    launchIntent.putExtra("justRegistered", true);
                    startActivity(launchIntent);
                    finish();
                    taskActive = false;
                }, () -> {
                    mUsernameView.setError(getString(R.string.error_account_exists));
                    taskActive = false;
                });
            }
            focusView.requestFocus();
        }
    }

    private RegistrationActivity getInstance() {
        return this;
    }
}