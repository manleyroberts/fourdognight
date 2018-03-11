package fourdognight.github.com.casa.model;

import fourdognight.github.com.casa.LoginActivity;
import fourdognight.github.com.casa.MainScreenActivity;
import fourdognight.github.com.casa.RegistrationActivity;

/**
 * Created by manle on 3/9/2018.
 */

public class ModelFacade {
    private UserVerificationModel userVerificationModel;
    private ShelterManager shelterManager;

    public ModelFacade() {
        userVerificationModel = new UserVerificationModel();
        shelterManager = new ShelterManager();
    }
    public void getShelterData(MainScreenActivity instance) {
        shelterManager.getShelterData(instance);
    }
    public void attemptLogin(LoginActivity instance, String username, String password) {
        userVerificationModel.attemptLogin(instance, username, password);
    }
    public void attemptRegistration(RegistrationActivity instance, String name, String username, String password, boolean isAdmin) {
        userVerificationModel.attemptRegistration(instance, name, username, password, isAdmin);
    }
    public Shelter getShelter(String shelterName) {
        return shelterManager.getShelter(shelterName);
    }
}
