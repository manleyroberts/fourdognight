package fourdognight.github.com.casa.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import fourdognight.github.com.casa.R;
import fourdognight.github.com.casa.model.ModelFacade;


public class SearchActivity extends AppCompatActivity{
    private ModelFacade model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        model = ModelFacade.getInstance();
//        model.getShelterData(new Consumer<List<Shelter>>() {
//            @Override
//            public void accept(List<Shelter> list) {
//                reload(list);
//            }
//        });

        final Button nameButton = findViewById(R.id.nameButton);
        nameButton.setOnClickListener(view -> {
            EditText nameField = findViewById(R.id.nameField);
            String name = nameField.getText().toString();
            if (!name.isEmpty()) {
                restrictionCallback(name);
            }
        });

        final Spinner ageSpinner = findViewById(R.id.ageSpinner);
        String[] ages = new String[] {
                "Families", "Children", "Young Adults", "Anyone"
        };
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ages);
        ageSpinner.setAdapter(ageAdapter);

        final Button ageButton = findViewById(R.id.ageButton);
        ageButton.setOnClickListener(view -> restrictionCallback(ageSpinner.getSelectedItem().toString()));

        final Spinner genderSpinner = findViewById(R.id.genderSpinner);
        String[] genders = new String[] {
                "Men", "Women"
        };
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, genders);
        genderSpinner.setAdapter(genderAdapter);

        final Button genderButton = findViewById(R.id.genderButton);
        genderButton.setOnClickListener(view -> restrictionCallback(genderSpinner.getSelectedItem().toString()));
    }

    private void restrictionCallback(String filter) {
        Intent intent = new Intent(SearchActivity.this, MainScreenActivity.class);
        intent.putExtra("restrictionFilter", filter.toLowerCase());
        startActivity(intent);
    }
}
