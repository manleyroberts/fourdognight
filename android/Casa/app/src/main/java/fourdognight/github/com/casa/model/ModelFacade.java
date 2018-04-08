package fourdognight.github.com.casa.model;

import java.util.List;

/**
 * Created by manle on 3/9/2018.
 */

public class ModelFacade {
    private UserVerificationModel userVerificationModel;
    private ShelterManager shelterManager;
    private static final ModelFacade model = new ModelFacade();

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

    public void attemptLogin(String username, String password, Runnable success, Runnable failure) {
        userVerificationModel.attemptLogin(username, password, success, failure);
    }

    public void attemptRegistration(String name, String username,
                                    String password, boolean isAdmin, Runnable success,
                                    Runnable failure) {
        userVerificationModel.attemptRegistration(name, username, password, isAdmin, success,
                failure);
    }

    public static ModelFacade getInstance() {
        return model;
    }

    public boolean updateVacancy(Shelter shelter, User user, int bedsHeld) {
        return shelterManager.updateVacancy(shelter, user, bedsHeld);
    }

    public void getShelterDataUnique(int uniqueKey, Consumer<Shelter> success) {
        shelterManager.getShelterDataUnique(uniqueKey, success);
    }

    public User getCurrentUser() {
        return userVerificationModel.getCurrentUser();
    }

    List<User> usersAtShelter(Shelter shelter) {
        return userVerificationModel.usersAtShelter(shelter);
    }
}
