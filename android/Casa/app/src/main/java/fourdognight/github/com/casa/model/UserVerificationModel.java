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

    void setCurrentUser (AbstractUser user) {
        currentUser = user;
    }

    AbstractUser getCurrentUser() {
        return currentUser;
    };

    void attemptRegistration(String name, String username,
                             String password, boolean isAdmin, Runnable success,
                             Runnable failure) {
        firebaseInterfacer.attemptRegistration(username, password, name, isAdmin, success, failure);
    }

    void attemptLogin(String username, String password, final Runnable success,
                      Runnable failure) {
        firebaseInterfacer.attemptLogin(username, password, new Consumer<AbstractUser>() {
            @Override
            public void accept(AbstractUser abstractUser) {
                setCurrentUser(abstractUser);
                success.run();
            }
        }, failure);
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
