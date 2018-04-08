package fourdognight.github.com.casa.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
=======
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fourdognight.github.com.casa.R;
import fourdognight.github.com.casa.model.User;
import fourdognight.github.com.casa.model.ModelFacade;
import fourdognight.github.com.casa.model.Shelter;
import fourdognight.github.com.casa.model.Consumer;

<<<<<<< HEAD
/**
 * @author Evan Mi, Manley Roberts, Jared Duncan
 * @version 1.0
 *
 * establishes the activities/functions on the main screen of the app
 */
public class MainScreenActivity extends AppCompatActivity {

    private TextView mUsernameView;
    private ArrayAdapter adapter;
    private ModelFacade model;
    private User user;
=======
public class MainScreenActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private ModelFacade model;
>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
    private List<Shelter> shelterList;
    private String restrictionFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = ModelFacade.getInstance();
        model.init();

        if (getIntent().hasExtra("restrictionFilter")) {
            restrictionFilter = (String) getIntent().getExtras().get("restrictionFilter");
        }

        setContentView(R.layout.activity_main_screen);
        final Button button = findViewById(R.id.logOutButton);
<<<<<<< HEAD
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        final Button mapViewButton = findViewById(R.id.mapViewButton);
        mapViewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (adapter == null) {
                    return;
                }
                Intent intent = new Intent(MainScreenActivity.this, MapsActivity.class);
                intent.putExtra("Shelters", (Serializable)shelterList);
                startActivity(intent);
            }
        });

        mUsernameView = findViewById(R.id.mainScreenUsernameField);
        user = model.getCurrentUser();
=======
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreenActivity.this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        final Button mapViewButton = findViewById(R.id.mapViewButton);
        mapViewButton.setOnClickListener(v -> {
            if (adapter == null) {
                return;
            }
            Intent intent = new Intent(MainScreenActivity.this, MapsActivity.class);
            intent.putExtra("Shelters", (Serializable)shelterList);
            startActivity(intent);
        });

        TextView mUsernameView = findViewById(R.id.mainScreenUsernameField);
        User user = model.getCurrentUser();
>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
        String topText = user.getName();
        topText += " | " + user.getUsername();
        if (user.isAdmin()) {
            topText += " | Admin";
        } else {
            topText += " | User";
        }
        mUsernameView.setText(topText);
        // Reads the CSV data
//        readHomelessShelterData();
<<<<<<< HEAD
        model.getShelterData(new Consumer<List<Shelter>>() {
            @Override
            public void accept(List<Shelter> list) {
                reload(list);
            }
        });
    }

    /**
     * creates a visible list of shelters that can be clicked
     * to view an info page full of shelter data and offers a search function
     * @param shelters the list of shelters used to create a visible list
     */
    public void reload(final List<Shelter> shelters) {
=======
        model.getShelterData(list -> reload(list));
    }

    private void reload(final List<Shelter> shelters) {
>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
        List<String> sheltersDisplay = new ArrayList<>(shelters.size());
        this.shelterList = new ArrayList<>();
        for (Shelter shelter : shelters) {
            if (restrictionFilter == null
                    || shelter.getRestriction().toLowerCase().contains(restrictionFilter)
                    || shelter.getShelterName().toLowerCase().contains(restrictionFilter)) {
                sheltersDisplay.add(shelter.getShelterName());
                this.shelterList.add(shelter);
            }
        }
        adapter  = new ArrayAdapter<>(this, R.layout.shelterlist, sheltersDisplay);

        //Creates the info page
        final ListView listView = findViewById(R.id.shelterList);
        listView.setAdapter(adapter);
        // retrieves information for the info page
<<<<<<< HEAD
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainScreenActivity.this, fourdognight.github.com.casa.ui.ListActivity.class);
                Shelter shelter = shelterList.get(i);
                intent.putExtra("Shelter", shelter);
                startActivity(intent);
            }
        });

        Button searchbar = findViewById(R.id.search);
        searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this,
                        SearchActivity.class);
                startActivity(intent);
            }
        });

        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restrictionFilter = null;
                model.getShelterData(new Consumer<List<Shelter>>() {
                    @Override
                    public void accept(List<Shelter> list) {
                        reload(list);
                    }
                });
            }
=======
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MainScreenActivity.this, ListActivity.class);
            Shelter shelter = shelterList.get(i);
            intent.putExtra("Shelter", shelter);
            startActivity(intent);
        });

        Button searchbar = findViewById(R.id.search);
        searchbar.setOnClickListener(view -> {
            Intent intent = new Intent(MainScreenActivity.this,
                    SearchActivity.class);
            startActivity(intent);
        });

        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(view -> {
            restrictionFilter = null;
            model.getShelterData(list -> reload(list));
>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
        });
    }

    //splits the csv into commas and if there are quotation marks then the commas inside quotations
    // are not removed
//    private void readHomelessShelterData() {
//        InputStream is = getResources().openRawResource(R.raw.homelessshelterdatabase);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//
//        List<String> rawList = new LinkedList<>();
//        List<Shelter> list = new LinkedList<>();
//        String line = "";
//        try {
//            reader.readLine();
//            String read;
//            while ((read = reader.readLine()) != null) {
//                String[] row = read.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
//                if (!list.contains(row[0])) {
//                    for (int i = 0; i < row.length; i++) {
//                        if (row[i].indexOf('\"') > -1) {
//                            row[i] = row[i].split("\"")[1];
//                        }
//                        row[i] = row[i].trim();
//                        rawList.add(row[i]);
//                    }
//                }
//            }
//            for (int i = 0; i < rawList.size()/10; i++) {
//                List<String> newList = new LinkedList<>();
//                newList.add("dummy@dummy");
//                Shelter shelter = new Shelter(Integer.parseInt(rawList.get(10 * i)), rawList.get(10 * i + 1), Integer.parseInt(rawList.get(10 * i + 2)), Integer.parseInt(rawList.get(10 * i + 3)), rawList.get(10 * i + 4),
//                        new ShelterLocation(Double.parseDouble(rawList.get(10 * i + 5)), Double.parseDouble(rawList.get(10 * i + 6)), rawList.get(10 * i + 7)), rawList.get(10 * i + 8), rawList.get(10 * i + 9), newList);
//                list.add(shelter);
//            } //int uniqueKey, String shelterName, int capacity, int vacancy, String restriction, double longitude,
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference databaseReference = database.getReference("");
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("shelterList", list);
//            databaseReference.updateChildren(map);
//        } catch (IOException e) {
//            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
//            e.printStackTrace();
//        }
//    }

<<<<<<< HEAD
    private MainScreenActivity getInstance() {
        return this;
    }
=======
>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
}
