package fourdognight.github.com.casa.model;

import android.graphics.ColorSpace;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import fourdognight.github.com.casa.LoginActivity;
import fourdognight.github.com.casa.MainScreenActivity;
import fourdognight.github.com.casa.RegistrationActivity;
import fourdognight.github.com.casa.SearchActivity;

/**
 * Created by manle on 3/9/2018.
 */

public class ModelFacade {
    private UserVerificationModel userVerificationModel;
    private ShelterManager shelterManager;
    static ModelFacade model = new ModelFacade();
    private MainScreenActivity mainScreenActivity;
    private SearchActivity searchActivity;

    public ModelFacade() {
        userVerificationModel = UserVerificationModel.getInstance();
        shelterManager = ShelterManager.getInstance();
    }
    public void getShelterData(MainScreenActivity instance) {
        mainScreenActivity = instance;
        shelterManager.getShelterData(this);
    }
    public void getShelterData(SearchActivity instance) {
        searchActivity = instance;
        shelterManager.getShelterData(this);
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

    void reloadMainScreen(List<String> sheltersDisplay) {
        if (mainScreenActivity != null) {
            mainScreenActivity.reload(sheltersDisplay);
        }
    }
}
