package fourdognight.github.com.casa.ui;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fourdognight.github.com.casa.R;
import fourdognight.github.com.casa.model.User;
import fourdognight.github.com.casa.model.ModelFacade;
import fourdognight.github.com.casa.model.Shelter;

/**
 * creates the mainscreen activity with all the shelters as a list
 * @author Evan Mi, Manley Roberts, Jared Duncan
 * @version 1.0
 */
public class MainScreenActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private ModelFacade model;
    private List<Shelter> shelterList;
    @Nullable
    private String restrictionFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = ModelFacade.getInstance();
        model.init();

        Intent restrictionIntent = getIntent();
        if (restrictionIntent.hasExtra("restrictionFilter")) {
            restrictionFilter = (String) getIntent().getExtras().get("restrictionFilter");
        }

        setContentView(R.layout.activity_main_screen);
        final Button button = findViewById(R.id.logOutButton);
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
        model.getShelterData(this::reload);
    }

    private void reload(final Collection<Shelter> shelters) {
        List<String> sheltersDisplay = new ArrayList<>(shelters.size());
        this.shelterList = new ArrayList<>();
        for (Shelter shelter : shelters) {
            String restriction = shelter.getRestriction();
            String shelterName = shelter.getShelterName();
            restriction = restriction.toLowerCase();
            shelterName = shelterName.toLowerCase();
            if (restrictionFilter == null
                    || restriction.contains(restrictionFilter)
                    || shelterName.contains(restrictionFilter)) {
                sheltersDisplay.add(shelter.getShelterName());
                this.shelterList.add(shelter);
            }
        }
        adapter  = new ArrayAdapter<>(this, R.layout.shelterlist, sheltersDisplay);

        //Creates the info page
        final ListView listView = findViewById(R.id.shelterList);
        listView.setAdapter(adapter);
        // retrieves information for the info page
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
            model.getShelterData(this::reload);
        });
    }

}
