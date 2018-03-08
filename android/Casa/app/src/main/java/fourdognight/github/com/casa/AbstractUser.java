package fourdognight.github.com.casa;

import java.io.Serializable;

/**
 * Created by manle on 2/19/2018.
 */

abstract class AbstractUser implements Serializable {
    private String name;
    private String username;
    private String password;

    AbstractUser(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    boolean authenticate(String password) {
        return password.equals(this.password);
    }
}
