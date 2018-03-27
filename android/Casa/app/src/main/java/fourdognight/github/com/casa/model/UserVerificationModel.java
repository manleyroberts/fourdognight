package fourdognight.github.com.casa.model;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fourdognight.github.com.casa.LoginActivity;
import fourdognight.github.com.casa.RegistrationActivity;

/**
 * Created by manle on 2/12/2018.
 */

public class UserVerificationModel {
    private FirebaseInterfacer firebaseInterfacer;
    private String heldUsername;
    private String heldPassword;
    private LoginActivity heldLogin;
    private RegistrationActivity heldRegistration;
    private AbstractUser heldUser;
    private Map<String, AbstractUser> users;

    private static UserVerificationModel instance = new UserVerificationModel();
    private static AbstractUser currentUser;

    private UserVerificationModel() {
        users = new HashMap<>();
    }

    void init() {
        firebaseInterfacer = FirebaseInterfacer.getInstance();
        firebaseInterfacer.getUserData();
    }

    void loadUserLogin(AbstractUser loadedUser) {
        if (loadedUser == null) {
            heldLogin.completeLogin(null);
        } else if (loadedUser.usernameMatches(heldUsername) && loadedUser.authenticate(heldPassword)) {
            heldLogin.completeLogin(loadedUser);
        } else {
            heldLogin.completeLogin(null);
        }
    }

    void setCurrentUser (AbstractUser user) {
        currentUser = user;
    }

    AbstractUser getCurrentUser() {
        return currentUser;
    }

    void createNewUser() {
        firebaseInterfacer.updateUser(heldUser);
        heldRegistration.completeRegistrationSuccess();
    }

    void userExists() {
        heldRegistration.completeRegistrationFailed();
    }

    void attemptRegistration(RegistrationActivity instance, String name, String username, String password,
                                       boolean isAdmin) {
        heldRegistration = instance;
        if (isAdmin) {
            heldUser = new Admin(name, username, password);
        } else {
            heldUser = new User(name, username, password, -1, 0);
        }
        firebaseInterfacer.attemptRegistration(username);
    }

    void attemptLogin(LoginActivity instance, String username, String password) {
        heldLogin = instance;
        heldUsername = username;
        heldPassword = password;
        firebaseInterfacer.attemptLogin(username);
    }

    AbstractUser findUserByUsername(String username) {
        return users.get(username);
    }

    void updateUserList(List<AbstractUser> list) {
        for (AbstractUser user : list) {
            updateUserList(user);
        }
    }

    void updateUserList(AbstractUser user) {
        users.put(user.getUsername(), user);
    }

    void pushUserChanges() {
        for (AbstractUser user : users.values()) {
            firebaseInterfacer.updateUser(user);
        }
    }

    List<User> usersAtShelter(Shelter shelter) {
        List<User> list = new LinkedList<>();
        int shelterKey = shelter.getUniqueKey();
        for (AbstractUser user : users.values()) {
            if (user instanceof User && ((User) user).getCurrentShelterUniqueKey() == shelterKey) {
                list.add((User) user);
            }
        }
        return list;
    }

    static UserVerificationModel getInstance() {
        return instance;
    }
}
