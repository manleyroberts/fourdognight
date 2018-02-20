package fourdognight.github.com.casa;
import java.util.HashMap;
/**
 * Created by manle on 2/12/2018.
 */

class UserVerificationModel {
    private static HashMap<String, AbstractUser> userList = new HashMap<>();

    static boolean attemptRegistration(String name, String username, String password,
                                       boolean isAdmin) {
        if (userList.containsKey(username)) {
            return false;
        } else if (isAdmin) {
            userList.put(username, new Admin(name, username, password));
        } else {
            userList.put(username, new User(name, username, password));
        }
        return true;
    }

    static AbstractUser attemptLogin(String username, String password) {
        if (!userList.containsKey(username) || !userList.get(username).authenticate(password)) {
            return null;
        } else {
            return userList.get(username);
        }
    }
}
