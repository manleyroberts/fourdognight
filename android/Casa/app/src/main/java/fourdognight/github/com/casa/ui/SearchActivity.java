package fourdognight.github.com.casa.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import fourdognight.github.com.casa.R;
import fourdognight.github.com.casa.model.ModelFacade;


public class SearchActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Button nameButton = findViewById(R.id.nameButton);
        nameButton.setOnClickListener(view -> {
            EditText nameField = findViewById(R.id.nameField);
            Editable nameFieldText = nameField.getText();
            String name = nameFieldText.toString();
            if (!name.isEmpty()) {
                restrictionCallback(name);
            }
        });

        final Spinner ageSpinner = findViewById(R.id.ageSpinner);
        String[] ages = new String[] {
                "Families", "Children", "Young Adults", "Anyone"
        };
        SpinnerAdapter ageAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ages);
        ageSpinner.setAdapter(ageAdapter);

        final Button ageButton = findViewById(R.id.ageButton);
        Object selectedAge = ageSpinner.getSelectedItem();
        ageButton.setOnClickListener(view -> restrictionCallback(selectedAge.toString()));

        final Spinner genderSpinner = findViewById(R.id.genderSpinner);
        String[] genders = new String[] {
                "Men", "Women"
        };
        SpinnerAdapter genderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, genders);
        genderSpinner.setAdapter(genderAdapter);

        final Button genderButton = findViewById(R.id.genderButton);
        Object selectedGender = genderSpinner.getSelectedItem();
        genderButton.setOnClickListener(view -> restrictionCallback(selectedGender.toString()));
    }

    private void restrictionCallback(String filter) {
        Intent intent = new Intent(SearchActivity.this, MainScreenActivity.class);
        intent.putExtra("restrictionFilter", filter.toLowerCase());
        startActivity(intent);
    }
}
