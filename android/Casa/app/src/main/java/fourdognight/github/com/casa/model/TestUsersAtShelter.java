package fourdognight.github.com.casa.model;

import android.view.Display;
import android.view.MotionEvent;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test Suite to test usersAtShelter in UserVerificationModel class with the goal of attaining
 * branch coverage.
 *
 * @author Manley Roberts
 * @version 1.0
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestUsersAtShelter {
    Shelter nullShelter = null;
    Shelter shelterA;
    Shelter shelterB;
    Shelter shelterC;
    UserVerificationModel userVerificationModel;
    LinkedList<User> otherList = new LinkedList<>();
    User bob;
    User sandy;
    User jeffrey;
    User mac;

    @Before
    public void setUp() {
        shelterA = new Shelter(0, "A", 10, 8, "restrictions",
                new ShelterLocation(100.0, 100.0, "123 Cherry Lane"),
                "This shelter is really cool.", "404-867-5309", new LinkedList<>());
        shelterB = new Shelter(1, "B", 10, 9, "restrictions",
                new ShelterLocation(100.0, 100.0, "456 Cherry Lane"),
                "This shelter is really cool.", "404-868-5309", new LinkedList<>());
        shelterC = new Shelter(2, "C", 10, 10, "restrictions",
                new ShelterLocation(100.0, 100.0, "789 Cherry Lane"),
                "This shelter is really cool.", "404-869-5309", new LinkedList<>());

        bob = new User("Bob", "bob@gatech.edu",
                "silkydog", 0, false);

        sandy = new User("Sandy", "sandy@gatech.edu",
                "wordfather", 0, false);

        jeffrey = new User("Jeffrey", "jeffrey@gatech.edu",
                "splitmail", 1, false);

        mac = new User("Mac", "mac@gatech.edu",
                "firebender", -1, false);

        userVerificationModel = UserVerificationModel.getInstance();

        userVerificationModel.updateUserList(bob);

        userVerificationModel.updateUserList(sandy);

        userVerificationModel.updateUserList(jeffrey);

        userVerificationModel.updateUserList(mac);
        //Users now contains bob, sandy, jeffrey, and mac
    }

    @Test
    public void testNullShelter() {
        //Should return an empty list
        assertEquals(new LinkedList<>(), userVerificationModel.usersAtShelter(nullShelter));
    }

    @Test
    public void testShelterNoUsers() {
        //Should return an empty list
        assertEquals(new LinkedList<>(), userVerificationModel.usersAtShelter(shelterC));
    }

    @Test
    public void testShelterWithOneUserAtShelter() {
        //Adding jeffrey to the other list
        otherList.add(jeffrey);

        //Checking if jeffrey shows up for his proper shelter
        assertEquals(otherList, userVerificationModel.usersAtShelter(shelterB));
    }

    @Test
    public void testShelterWithMultipleUsersAtShelter() {
        otherList.add(bob);
        otherList.add(sandy);
        //Should return a list containing bob and sandy, in any order
        List<User> users = userVerificationModel.usersAtShelter(shelterA);
        assertTrue(users.contains(bob));
        assertTrue(users.contains(sandy));
        assertEquals(2, users.size());
    }
}