package fourdognight.github.com.casa.model;

import fourdognight.github.com.casa.LoginActivity;
import fourdognight.github.com.casa.MainScreenActivity;
import fourdognight.github.com.casa.RegistrationActivity;

/**
 * Created by manle on 3/9/2018.
 */

public class ModelFacade {
    private FirebaseInterfacer firebaseInterfacer;
    private UserVerificationModel userVerificationModel;

    public ModelFacade() {
        firebaseInterfacer = new FirebaseInterfacer();
        userVerificationModel = new UserVerificationModel();
    }
    public void getShelterData(MainScreenActivity instance) {
        firebaseInterfacer.getShelterData(instance);
    }
    public void attemptLogin(LoginActivity instance, String username, String password) {
        userVerificationModel.attemptLogin(instance, username, password);
    }
    public void attemptRegistration(RegistrationActivity instance, String name, String username, String password, boolean isAdmin) {
        userVerificationModel.attemptRegistration(instance, name, username, password, isAdmin);
    }
}
