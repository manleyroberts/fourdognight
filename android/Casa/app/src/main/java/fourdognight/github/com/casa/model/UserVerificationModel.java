package fourdognight.github.com.casa.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by manle on 2/12/2018.
 */

public class UserVerificationModel {
    private FirebaseInterfacer firebaseInterfacer;
    private Map<String, User> users;

    private static UserVerificationModel instance = new UserVerificationModel();
    private static User currentUser;

    private UserVerificationModel() {
        users = new HashMap<>();
    }

    void init() {
        firebaseInterfacer = FirebaseInterfacer.getInstance();
        firebaseInterfacer.getUserData();
    }

    void setCurrentUser (User user) {
        currentUser = user;
    }

    User getCurrentUser() {
        return currentUser;
    };

    void attemptRegistration(String name, String username,
                             String password, boolean isAdmin, Runnable success,
                             Runnable failure) {
        firebaseInterfacer.attemptRegistration(name, username, password, isAdmin, success, failure);
    }

    void attemptLogin(String username, String password, final Runnable success,
                      Runnable failure) {
        firebaseInterfacer.attemptLogin(username, password, new Consumer<User>() {
            @Override
            public void accept(User user) {
                setCurrentUser(user);
                success.run();
            }
        }, failure);
    }

    void updateUserList(List<User> list) {
        for (User user : list) {
            updateUserList(user);
        }
    }

    void updateUserList(User user) {
        users.put(user.getUsername(), user);
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
            if (user.getCurrentShelterUniqueKey() == shelterKey) {
                list.add((User) user);
            }
        }
        return list;
    }

    static UserVerificationModel getInstance() {
        return instance;
    }
}
