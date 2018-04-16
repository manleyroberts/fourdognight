package fourdognight.github.com.casa.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.junit.Assert.*;

public class IsaacUnitTest {

    User u, us;
    ShelterManager sm;
    UserVerificationModel um;

    @Before
    public void setUp() {
        sm = ShelterManager.getInstance();
        sm.init();
        sm.getShelterData((l) -> {});
        um = UserVerificationModel.getInstance();
        um.init();
        u = new User();
        us = new User("", "", "", 9, false);
    }

    @Test
    public void testNotAtShelter() {
        assertEquals(-1, u.getCurrentShelterUniqueKey());
        u.setCurrentStatus(3, 1);
        assertEquals(3, u.getCurrentShelterUniqueKey());
        assertEquals(1, u.getHeldBeds());
        assertTrue(um.usersAtShelter(new Shelter(3, "Fuqua Hall", 92, 92, "Men", new ShelterLocation(-84.392273, 33.76515, "144 Mills Street, Atlanta, Georgia 30313"), "Transitional housing", "(404) 367-2492", Arrays.asList("dummy@dummy"))).contains(u));
    }

    @Test
    public void testAtShelter() {
        assertEquals(9, us.getCurrentShelterUniqueKey());
        assertTrue(um.usersAtShelter(new Shelter(9, "Hope Atlanta", 22, 22, "Anyone", new ShelterLocation(-84.390429, 33.753594, "34 Peachtree Street NW, Suite 700, Atlanta, GA 30303"), "Emergency shelter", "(404)-817-7070", Arrays.asList("dummy@dummy", ""))).contains(us));
        us.setCurrentStatus(0, 4);
        assertEquals(0, u.getCurrentShelterUniqueKey());
        assertEquals(4, u.getHeldBeds());
        assertFalse(um.usersAtShelter(new Shelter(9, "Hope Atlanta", 22, 22, "Anyone", new ShelterLocation(-84.390429, 33.753594, "34 Peachtree Street NW, Suite 700, Atlanta, GA 30303"), "Emergency shelter", "(404)-817-7070", Arrays.asList("dummy@dummy"))).contains(us));
        assertTrue(um.usersAtShelter(new Shelter(0, "My Sister's House", 264, 263, "Women/Children", new ShelterLocation(-84.410142, 33.780174, "921 Howell Mill Road, Atlanta, Georgia 30318"), "Temporary, Emergency, Residential Recovery", "(404) 367-2465", Arrays.asList("dummy@dummy", ""))).contains(us));
    }
}