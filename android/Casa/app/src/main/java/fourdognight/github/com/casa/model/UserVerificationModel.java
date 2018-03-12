package fourdognight.github.com.casa.model;

import android.util.Log;

import java.util.HashMap;
import fourdognight.github.com.casa.LoginActivity;
import fourdognight.github.com.casa.RegistrationActivity;

/**
 * Created by manle on 2/12/2018.
 */

public class UserVerificationModel {
    private static HashMap<String, AbstractUser> userList = new HashMap<>();
    private FirebaseInterfacer firebaseInterfacer;
    private String heldUsername;
    private String heldPassword;
    private LoginActivity heldLogin;
    private RegistrationActivity heldRegistration;
    private AbstractUser heldUser;

    public UserVerificationModel() {
        firebaseInterfacer = new FirebaseInterfacer();
    }

    void loadUserLogin(AbstractUser loadedUser) {
        if (loadedUser == null) {
            heldLogin.completeLogin(null);
        } else if (loadedUser.getUsername().equals(heldUsername) && loadedUser.getPassword().equals(heldPassword)) {
            heldLogin.completeLogin(loadedUser);
        } else {
            heldLogin.completeLogin(null);
        }
    }

    void createNewUser() {
        firebaseInterfacer.addNewUser(heldUser);
        heldRegistration.completeRegistrationSuccess();
    }

    void userExists() {
        heldRegistration.completeRegistrationFailed();
    }

    public void attemptRegistration(RegistrationActivity instance, String name, String username, String password,
                                       boolean isAdmin) {
        heldRegistration = instance;
        if (isAdmin) {
            heldUser = new Admin(name, username, password);
        } else {
            heldUser = new User(name, username, password, -1, -1);
        }
        firebaseInterfacer.attemptRegistration(this, username);
    }

    public void attemptLogin(LoginActivity instance, String username, String password) {
        heldLogin = instance;
        heldUsername = username;
        heldPassword = password;
        firebaseInterfacer.attemptLogin(this, username);
    }
}
