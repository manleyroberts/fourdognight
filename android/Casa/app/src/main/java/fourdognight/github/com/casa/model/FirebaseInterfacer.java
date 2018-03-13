package fourdognight.github.com.casa.model;

import android.support.annotation.NonNull;
import android.text.TextWatcher;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import junit.framework.TestResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by manle on 3/9/2018.
 */

public class FirebaseInterfacer {
    List<Shelter> results;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    static FirebaseInterfacer instance = new FirebaseInterfacer();

    public FirebaseInterfacer() {
        results = new LinkedList<Shelter>();
    }

    void getShelterData(final ShelterManager instance) {
        DatabaseReference myRef = database.getReference("shelterList");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                results.clear();
                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                    HashMap<String, Object> map = (HashMap<String, Object>) itemSnapshot.getValue();
                    List<String> currentPatrons;
                    if (map.get("currentPatrons") != null) {
                        currentPatrons = (List<String>) map.get("currentPatrons");
                    } else {
                        currentPatrons = new ArrayList<>();
                    }

                    Shelter next = new Shelter(((Long) map.get("uniqueKey")).intValue(),
                            (String) map.get("shelterName"), ((Long) map.get("capacity")).intValue(),
                            ((Long) map.get("vacancy")).intValue(), (String) map.get("restriction"),
                            (double) map.get("longitude"), (double) map.get("latitude"),
                            (String) map.get("address"), (String) map.get("special"), (String) map.get("phone"),
                            currentPatrons);
                    results.add(next);
                }
                instance.reload(results);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading shelter list.");
            }
        });
    }

    void getUserData(final UserVerificationModel instance) {
        DatabaseReference myRef = database.getReference("userList");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<AbstractUser> list = new ArrayList<>();
                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                    HashMap<String, Object> map = (HashMap<String, Object>) itemSnapshot.getValue();
                    Log.d("Vacancy", (map.get("isAdmin") == null) ? "NULL" : "NOTNULL");
                    AbstractUser loadedUser = ((Boolean) map.get("isAdmin")) ? new Admin((String) map.get("name"), (String) map.get("username"), (String) map.get("password"))
                            : new User((String) map.get("name"), (String) map.get("username"),
                            (String) map.get("password"), ((Long) map.get("currentShelterUniqueKey")).intValue(),
                            ((Long) map.get("heldBeds")).intValue());
                    list.add(loadedUser);
                }
                instance.updateUserList(list);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading user list.");
            }
        });
    }

    void attemptLogin(final UserVerificationModel instance, final String username) {
        DatabaseReference myRef = database.getReference("userList/" + username);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    instance.loadUserLogin(null);
                } else {
                    HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                    Log.d("Vacancy", (map.get("isAdmin") == null) ? "NULL" : "NOTNULL");
                    AbstractUser loadedUser = ((Boolean) map.get("isAdmin")) ? new Admin((String) map.get("name"), (String) map.get("username"), (String) map.get("password"))
                            : new User((String) map.get("name"), (String) map.get("username"),
                            (String) map.get("password"), ((Long) map.get("currentShelterUniqueKey")).intValue(),
                            ((Long) map.get("heldBeds")).intValue());
                    instance.loadUserLogin(loadedUser);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading user.");
            }
        });
    }

    void attemptRegistration(final UserVerificationModel instance, final String username) {
        DatabaseReference myRef = database.getReference("userList/" + username);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    instance.createNewUser();
                } else {
                    instance.userExists();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading user.");
            }
        });
    }

    void updateUser(AbstractUser user) {
        DatabaseReference myRef = database.getReference("userList");
        Map<String, Object> updatedUsers = new HashMap<>();
        updatedUsers.put(user.getUsername(), user);
        myRef.updateChildren(updatedUsers);

        myRef = database.getReference("userList/" + user.getUsername());
        updatedUsers = new HashMap<>();
        updatedUsers.put("isAdmin", user instanceof Admin);
        if (user instanceof User) {
            User myUser = (User) user;
            updatedUsers.put("currentShelterUniqueKey", myUser.getCurrentShelterUniqueKey());
        }
        myRef.updateChildren(updatedUsers);
    }

//    void updateVacancy(Shelter shelter, User user, int bedsHeld) {
//        DatabaseReference myRef = database.getReference("userList/" + user.getUsername());
//        Map<String, Object> updatedEntries = new HashMap<>();
//
//        updatedEntries.put("heldBeds", bedsHeld);
//        updatedEntries.put("currentShelterUniqueKey", shelter.getUniqueKey());
//        myRef.updateChildren(updatedEntries);
//
//        myRef = database.getReference("shelterList/" + shelter.getUniqueKey());
//        updatedEntries.clear();
//        List<String> shelterPatrons = shelter.getCurrentPatrons();
//        shelterPatrons.add(user.getUsername());
//        updatedEntries.put("currentPatrons", shelterPatrons);
//        updatedEntries.put("vacancy", shelter.getVacancy() - bedsHeld);
//        myRef.updateChildren(updatedEntries);
//    }

    void refactorVacancy(final Shelter shelter, final List<String> users, final int newVacancy) {
        final DatabaseReference myRef = database.getReference("shelterList/" + shelter.getUniqueKey());
//        myRef.addListenerForSingleValueEvent((new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot subSnapshot : dataSnapshot.getChildren()) {
//                    Task task = subSnapshot.getRef().removeValue();
//                    task.addOnCompleteListener(new OnCompleteListener() {
//                        @Override
//                        public void onComplete(@NonNull Task task) {
                            Map<String, Object> updatedEntries = new HashMap<>();
//                            for (String user : users) {
//
//                                updatedEntries.put("currentPatrons", user);
//                            }
                            updatedEntries.put("vacancy", newVacancy);
                            myRef.updateChildren(updatedEntries);
//                            Log.d("Vacancy", shelter.getCurrentPatrons().toString());
//                        }
//                    });
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("Firebase", "Error refactoring vacancy.");
//            }
//        }));
    }

    static FirebaseInterfacer getInstance() {
        return instance;
    }
}
