package fourdognight.github.com.casa.model;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by jared on 4/16/18.
 */
public class JaredUnitTest {
    private Shelter shelter;
    private User user;
    private ShelterManager manager;

    @Before
    public void setUp() {
        manager = ShelterManager.getInstance();
        manager.init();
        ShelterLocation location = new ShelterLocation(85.5, -27.0, "3428 Howell Mill");
        shelter = new Shelter(1, "test", 10, 10, "Men", location, "none", "555-555-5555", new LinkedList<>());
        user = new User("Test", "email@email", "password", -1, false);
    }

    @Test
    public void testUpdateVacancy() {
        assertTrue(shelter.updateVacancy(user, 5));
        manager.refactorVacancy(shelter);
        assertEquals(user.getHeldBeds(), 5);
        assertEquals(shelter.getVacancy(), 5);
    }

    @Test
    public void testUpdateVacancyFailure_TooManyBeds() {
        assertFalse(shelter.updateVacancy(user, 15));
        manager.refactorVacancy(shelter);
        assertEquals(user.getHeldBeds(), 0);
        assertEquals(shelter.getVacancy(), 10);
    }

    @Test
    public void testUpdateVacancyFailure_LessThanZeroBeds() {
        assertFalse(shelter.updateVacancy(user, -1));
        manager.refactorVacancy(shelter);
        assertEquals(user.getHeldBeds(), 0);
        assertEquals(shelter.getVacancy(), 10);
    }

}