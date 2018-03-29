package fourdognight.github.com.casa;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import fourdognight.github.com.casa.model.AbstractUser;
import fourdognight.github.com.casa.model.Admin;
import fourdognight.github.com.casa.model.FirebaseInterfacer;
import fourdognight.github.com.casa.model.ModelFacade;
import fourdognight.github.com.casa.model.Shelter;
import fourdognight.github.com.casa.model.ShelterLocation;
import fourdognight.github.com.casa.model.ShelterManager;
import fourdognight.github.com.casa.model.User;

public class MainScreenActivity extends AppCompatActivity {

    private TextView mUsernameView;
    private ArrayAdapter adapter;
    private ModelFacade model;
    private AbstractUser user;
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
        String topText = user.getName();
        topText += " | " + user.getUsername();
        if (user instanceof Admin) {
            topText += " | Admin";
        } else {
            topText += " | User";
        }
        mUsernameView.setText(topText);
        // Reads the CSV data
        readHomelessShelterData();
        model.getShelterData(this);
    }

    public void reload(final List<Shelter> shelters) {
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainScreenActivity.this, fourdognight.github.com.casa.ListActivity.class);
                Shelter shelter = shelterList.get(i);
                intent.putExtra("Shelter", shelter);
                startActivity(intent);
            }
        });

        Button searchbar = findViewById(R.id.search);
        searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restrictionFilter = null;
                model.getShelterData(MainScreenActivity.this);
            }
        });
    }

    //splits the csv into commas and if there are quotation marks then the commas inside quotations
    // are not removed
    private void readHomelessShelterData() {
        InputStream is = getResources().openRawResource(R.raw.homelessshelterdatabase);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        List<String> rawList = new LinkedList<>();
        List<Shelter> list = new LinkedList<>();
        String line = "";
        try {
            reader.readLine();
            String read;
            while ((read = reader.readLine()) != null) {
                String[] row = read.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (!list.contains(row[0])) {
                    for (int i = 0; i < row.length; i++) {
                        if (row[i].indexOf('\"') > -1) {
                            row[i] = row[i].split("\"")[1];
                        }
                        row[i] = row[i].trim();
                        rawList.add(row[i]);
                    }
                }
            }
            for (int i = 0; i < rawList.size()/10; i++) {
                List<String> newList = new LinkedList<>();
                newList.add("dummy@dummy");
                Shelter shelter = new Shelter(Integer.parseInt(rawList.get(10 * i)), rawList.get(10 * i + 1), Integer.parseInt(rawList.get(10 * i + 2)), Integer.parseInt(rawList.get(10 * i + 3)), rawList.get(10 * i + 4),
                        new ShelterLocation(Double.parseDouble(rawList.get(10 * i + 5)), Double.parseDouble(rawList.get(10 * i + 6)), rawList.get(10 * i + 7)), rawList.get(10 * i + 8), rawList.get(10 * i + 9), newList);
                list.add(shelter);
            } //int uniqueKey, String shelterName, int capacity, int vacancy, String restriction, double longitude,
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = database.getReference("");
            HashMap<String, Object> map = new HashMap<>();
            map.put("shelterList", list);
            databaseReference.updateChildren(map);
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }

    private MainScreenActivity getInstance() {
        return this;
    }
}
