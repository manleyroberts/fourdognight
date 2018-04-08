package fourdognight.github.com.casa.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Locale;

import fourdognight.github.com.casa.R;
import fourdognight.github.com.casa.model.ModelFacade;
import fourdognight.github.com.casa.model.Shelter;
import fourdognight.github.com.casa.model.ShelterLocation;
import fourdognight.github.com.casa.model.User;

public class ListActivity extends AppCompatActivity {


    private TextView shelterName;
    private TextView capacity;
    private TextView key;
    private TextView restriction;
    private TextView longitude;
    private TextView latitude;
    private TextView address;
    private TextView special;
    private TextView phone;
    private TextView vacancy;
    private EditText selfReport;

    private ModelFacade model;
    //Creates all the "text boxes" for the shelter information on page when shelter is clicked
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        model = ModelFacade.getInstance();

        shelterName = findViewById(R.id.sheltertext);
        key = findViewById(R.id.uniqueKey);
        capacity = findViewById(R.id.capacity);
        vacancy = findViewById(R.id.vacancy);
        longitude = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);
        address = findViewById(R.id.address);
        special = findViewById(R.id.special);
        phone = findViewById(R.id.phone);
        restriction = findViewById(R.id.restrict);
        selfReport = findViewById(R.id.selfReport);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            final Shelter shelter = (Shelter) bundle.get("Shelter");
            final User user = model.getCurrentUser();
            reload(shelter);

            final Button button = findViewById(R.id.updateVacancyButton);
            button.setOnClickListener(v -> {
            model.init();
            if (!user.isAdmin()) {
                selfReport.setError(null);
                Editable text = selfReport.getText();
                int intendedBeds;
                String textNotEditable = text.toString();
                if (textNotEditable.isEmpty()) {
                    intendedBeds = 0;
                } else {
                    intendedBeds = Integer.parseInt(text.toString());
                }

                if (!user.canStayAt(shelter)) {
                    selfReport.setError(getString(R.string.error_held_beds_elsewhere));
                    selfReport.requestFocus();
                } else if (TextUtils.isEmpty(text) || intendedBeds < 0 ||
                        !model.updateVacancy(shelter, user,
                                Integer.parseInt(text.toString()))) {
                    selfReport.setError(getString(R.string.error_wrong_bed_number));
                    selfReport.requestFocus();
                } else {
                    int uniqueKey = shelter.getUniqueKey();
                    model.getShelterDataUnique(uniqueKey, this::reload);
                }
            }
            });
        }
    }

    private void reload(Shelter shelter) {
        User user = model.getCurrentUser();

        shelterName.setText(shelter.getShelterName());
        capacity.setText(String.format(Locale.getDefault(), "%d", shelter.getCapacity()));
        key.setText(String.format(Locale.getDefault(), "%d", shelter.getUniqueKey()));
        vacancy.setText(String.format(Locale.getDefault(), "%d", shelter.getVacancy()));
        restriction.setText(shelter.getRestriction());
        ShelterLocation loc = shelter.getLocation();
        longitude.setText(String.format(Locale.getDefault(), "%f", loc.getLongitude()));
        latitude.setText(String.format(Locale.getDefault(), "%f", loc.getLatitude()));
        address.setText(loc.getAddress());
        special.setText(shelter.getSpecial());
        phone.setText(shelter.getPhone());
        int beds = user.getHeldBeds();

        if (user.getCurrentShelterUniqueKey() == shelter.getUniqueKey()) {
            selfReport.setText(beds);
        }
    }

}


