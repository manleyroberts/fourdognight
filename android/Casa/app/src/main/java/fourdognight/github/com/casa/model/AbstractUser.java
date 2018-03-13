package fourdognight.github.com.casa.model;

import android.support.design.widget.BaseTransientBottomBar;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

/**
 * Created by manle on 2/19/2018.
 */

public abstract class AbstractUser implements Serializable {
    private String name;
    private String username;
    private String password;

    private transient final UserVerificationModel userVerificationModel = UserVerificationModel.getInstance();


    public AbstractUser(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
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

    boolean usernameMatches(String username) {
        return this.username.equals(username);
    }

    void pushUserChanges() {
        UserVerificationModel model = UserVerificationModel.getInstance();
        model.pushUserChanges(this);
    }
}
