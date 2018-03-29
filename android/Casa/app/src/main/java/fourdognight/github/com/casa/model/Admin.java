package fourdognight.github.com.casa.model;

/**
 * Created by manle on 2/19/2018.
 */

public class Admin extends AbstractUser{
    Admin(String name, String username, String password) {
        super (name, username, password);
        pushUserChanges();
    }
}
