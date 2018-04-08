package fourdognight.github.com.casa.model;

import java.util.List;

/**
 * Facade for the model (singleton class)
 * @author Manley Roberts, Jared Duncan
 * @version 1.0
 */
public class ModelFacade {
    private UserVerificationModel userVerificationModel;
    private ShelterManager shelterManager;
    private static final ModelFacade model = new ModelFacade();

    private ModelFacade() {    }

    /**
     * Initialize the singleton instance
     */
    public void init() {
        userVerificationModel = UserVerificationModel.getInstance();
        shelterManager = ShelterManager.getInstance();
        userVerificationModel.init();
        shelterManager.init();
    }

    /**
     * Gets shelter data
     * @param success consumer of shelter list, which is updated upon success of getting
     *                the shelter data
     */
    public void getShelterData(Consumer<List<Shelter>> success) {
        shelterManager.getShelterData(success);
    }

    /**
     * Attempts to log in a user
     * @param username the username that the user input
     * @param password the password that the user input
     * @param success callback to be run on successful login
     * @param failure callback to be run on unsuccessful login
     */
    public void attemptLogin(String username, String password, Runnable success, Runnable failure) {
        userVerificationModel.attemptLogin(username, password, success, failure);
    }

    /**
     * Attempts to register a user
     * @param name the name of the user to register
     * @param username the username of the user to register
     * @param password the password of the user to register
     * @param isAdmin boolean flag for whether the user to register is an admin
     * @param success callback to be run on success
     * @param failure callback to be run on failure
     */
    public void attemptRegistration(String name, String username,
                                    String password, boolean isAdmin, Runnable success,
                                    Runnable failure) {
        userVerificationModel.attemptRegistration(name, username, password, isAdmin, success,
                failure);
    }

    /**
     * Returns the singleton instance of this class
     * @return the singleton instance of this class
     */
    public static ModelFacade getInstance() {
        return model;
    }

    /**
     * Updates vacancy information with beds requested by user
     * @param shelter the shelter for which to update vacancy information
     * @param user the user requesting beds from shelter
     * @param bedsHeld the number of beds the user is requesting
     * @return true on success of vacancy update, false on failure
     */
    public boolean updateVacancy(Shelter shelter, User user, int bedsHeld) {
        return shelterManager.updateVacancy(shelter, user, bedsHeld);
    }

    /**
     * Gets shelter data for the shelter with unique key uniqueKey
     * @param uniqueKey the unique key for the shelter to get
     * @param success a consumer to be filled with the Shelter with unique key uniqueKey upon
     *                successful retrieval
     */
    public void getShelterDataUnique(int uniqueKey, Consumer<Shelter> success) {
        shelterManager.getShelterDataUnique(uniqueKey, success);
    }

    /**
     * Gets the current logged in user
     * @return the current logged in user
     */
    public User getCurrentUser() {
        return userVerificationModel.getCurrentUser();
    }

    List<User> usersAtShelter(Shelter shelter) {
        return userVerificationModel.usersAtShelter(shelter);
    }
}
