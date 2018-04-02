package fourdognight.github.com.casa.model;

import java.util.List;

import fourdognight.github.com.casa.ui.ListActivity;
import fourdognight.github.com.casa.ui.MainScreenActivity;
import fourdognight.github.com.casa.ui.MapsActivity;
import fourdognight.github.com.casa.ui.SearchActivity;

/**
 * Created by manle on 3/9/2018.
 */

public class ModelFacade {
    private UserVerificationModel userVerificationModel;
    private ShelterManager shelterManager;
    private static ModelFacade model = new ModelFacade();

    private ModelFacade() {    }

    public void init() {
        userVerificationModel = UserVerificationModel.getInstance();
        shelterManager = ShelterManager.getInstance();
        userVerificationModel.init();
        shelterManager.init();
    }

    public void getShelterData(Consumer<List<Shelter>> success) {
        shelterManager.getShelterData(success);
    }

//    public void getShelterData(MainScreenActivity instance) {
//        this.mainScreenActivity = instance;
//        shelterManager.getShelterData();
//    }
//    public void getShelterData(SearchActivity instance) {
//        this.searchActivity = instance;
//        shelterManager.getShelterData();
//    }
//    public void getShelterData(MapsActivity instance) {
//        this.mapsActivity = instance;
//        shelterManager.getShelterData();
//    }
//    public void getShelterDataList(ListActivity instance, int uniqueKey) {
//        this.listActivity = instance;
//        shelterManager.getShelterData(uniqueKey);
//    }
    public void attemptLogin(String username, String password, Runnable success, Runnable failure) {
        userVerificationModel.attemptLogin(username, password, success, failure);
    }
    public void attemptRegistration(String name, String username,
                                    String password, boolean isAdmin, Runnable success,
                                    Runnable failure) {
        userVerificationModel.attemptRegistration(name, username, password, isAdmin, success,
                failure);
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
//
//    void reload(List<String> sheltersDisplay, List<Shelter> shelters) {
//        if (mainScreenActivity != null) {
//            mainScreenActivity.reload(sheltersDisplay);
//        }
//        if (searchActivity != null) {
//            searchActivity.reload(shelters);
//        }
//        if (mapsActivity != null) {
//            mapsActivity.reload(shelters);
//        }
//    }

//    void reloadList(Shelter shelter) {
//        if (listActivity != null) {
//            listActivity.reload(shelter);
//        }
//    }

    public void getShelterDataUnique(int uniqueKey, Consumer<Shelter> success) {
        shelterManager.getShelterDataUnique(uniqueKey, success);
    }

    public User getCurrentUser() {
        return userVerificationModel.getCurrentUser();
    }
}
