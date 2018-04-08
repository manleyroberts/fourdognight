package fourdognight.github.com.casa.model;

import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * singleton class which maintains the users\
 * @author Manley Roberts
 * @version 1.0
 */

class UserVerificationModel {
    private FirebaseInterfacer firebaseInterfacer;
    private final Map<String, User> users;

    private static final UserVerificationModel instance = new UserVerificationModel();
    private static User currentUser;

    private UserVerificationModel() {
        users = new HashMap<>();
    }

    void init() {
        firebaseInterfacer = FirebaseInterfacer.getInstance();
        firebaseInterfacer.getUserData(list -> {
            for (User user: list) {
                updateUserList(user);
            }
        });
    }

    private void setCurrentUser(User user) {
        currentUser = user;
    }

    User getCurrentUser() {
        return currentUser;
    }

    void attemptRegistration(String name, String username,
                             String password, boolean isAdmin, Runnable success,
                             Runnable failure) {
        firebaseInterfacer.attemptRegistration(name, username, password, isAdmin, success, failure);
    }

    void attemptLogin(String username, String password, final Runnable success,
                      Runnable failure) {
        firebaseInterfacer.attemptLogin(username, password, user -> {
            setCurrentUser(user);
            success.run();
        }, failure);
    }

    void updateUserList(User user) {
        Log.d("LEG", "bef" + users.keySet());
        String username = user.getUsername();
        users.put(username, user);
        Log.d("LEG", "aft" + users.keySet());
    }

    void pushUserChanges() {
        for (User user : users.values()) {
            firebaseInterfacer.updateUser(user);
        }
    }

    List<User> usersAtShelter(Shelter shelter) {
        List<User> list = new LinkedList<>();
        int shelterKey = shelter.getUniqueKey();
        for (User user : users.values()) {
            if (!list.contains(user) && user.getCurrentShelterUniqueKey() == shelterKey) {
                list.add(user);
                Log.d("LEG", user.getUsername());
            }
        }
        return list;
    }

    static UserVerificationModel getInstance() {
        return instance;
    }
}
