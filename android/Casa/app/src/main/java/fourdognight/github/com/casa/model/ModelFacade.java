package fourdognight.github.com.casa.model;

import android.graphics.ColorSpace;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import fourdognight.github.com.casa.ListActivity;
import fourdognight.github.com.casa.LoginActivity;
import fourdognight.github.com.casa.MainScreenActivity;
import fourdognight.github.com.casa.MapsActivity;
import fourdognight.github.com.casa.RegistrationActivity;
import fourdognight.github.com.casa.SearchActivity;

/**
 * Created by manle on 3/9/2018.
 */

public class ModelFacade {
    private UserVerificationModel userVerificationModel;
    private ShelterManager shelterManager;
    private static ModelFacade model = new ModelFacade();
    private MainScreenActivity mainScreenActivity;
    private ListActivity listActivity;

    private ModelFacade() {    }

    public void init() {
        userVerificationModel = UserVerificationModel.getInstance();
        shelterManager = ShelterManager.getInstance();
        userVerificationModel.init();
        shelterManager.init();
    }

    public void getShelterData(MainScreenActivity instance) {
        this.mainScreenActivity = instance;
        shelterManager.getShelterData();
    }
    public void getShelterDataList(ListActivity instance, int uniqueKey) {
        this.listActivity = instance;
        shelterManager.getShelterData(uniqueKey);
    }
    public void attemptLogin(LoginActivity instance, String username, String password) {
        userVerificationModel.attemptLogin(instance, username, password);
    }
    public void attemptRegistration(RegistrationActivity instance, String name, String username, String password, boolean isAdmin) {
        userVerificationModel.attemptRegistration(instance, name, username, password, isAdmin);
    }
    public Shelter getShelter(int uniqueKey) {
        return shelterManager.getShelter(uniqueKey);
    }

    public static ModelFacade getInstance() {
        return model;
    }

    public boolean updateVacancy(Shelter shelter, User user, int bedsHeld) {
        return shelterManager.updateVacancy(shelter, user, bedsHeld);
    }

    void reload(List<Shelter> shelters) {
        if (mainScreenActivity != null) {
            mainScreenActivity.reload(shelters);
        }
    }

    void reloadList(Shelter shelter) {
        if (listActivity != null) {
            listActivity.reload(shelter, userVerificationModel.getCurrentUser());
        }
    }

    public void setCurrentUser (AbstractUser user) {
        userVerificationModel.setCurrentUser(user);
    }

    public AbstractUser getCurrentUser() {
        return userVerificationModel.getCurrentUser();
    }
}
