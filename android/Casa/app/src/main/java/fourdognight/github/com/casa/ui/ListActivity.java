package fourdognight.github.com.casa.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import fourdognight.github.com.casa.R;
import fourdognight.github.com.casa.model.Consumer;
import fourdognight.github.com.casa.model.ModelFacade;
import fourdognight.github.com.casa.model.Shelter;
import fourdognight.github.com.casa.model.ShelterLocation;
import fourdognight.github.com.casa.model.User;
import fourdognight.github.com.casa.model.UserVerificationModel;

public class ListActivity extends AppCompatActivity {


    TextView shelterName;
    TextView capacity;
    TextView key;
    TextView restriction;
    TextView longitude;
    TextView latitude;
    TextView address;
    TextView special;
    TextView phone;
    TextView vacancy;
    EditText selfReport;

    ModelFacade model;
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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            final Shelter shelter = (Shelter) bundle.get("Shelter");
            final User user = model.getCurrentUser();
            reload(shelter);

            final Button button = findViewById(R.id.updateVacancyButton);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                model.init();
                if (!user.isAdmin()) {
                    selfReport.setError(null);
                    Editable text = selfReport.getText();
                    int intendedBeds;
                    if (text.toString().isEmpty()) {
                        intendedBeds = 0;
                    } else {
                        intendedBeds = Integer.parseInt(text.toString());
                    }

                    if (user.canStayAt(shelter)) {
                        selfReport.setError(getString(R.string.error_held_beds_elsewhere));
                        selfReport.requestFocus();
                    } else if (TextUtils.isEmpty(text) || intendedBeds < 0 ||
                            !model.updateVacancy(shelter, (User) user,
                                    Integer.parseInt(text.toString()))) {
                        selfReport.setError(getString(R.string.error_wrong_bed_number));
                        selfReport.requestFocus();
                    } else {
                        int uniqueKey = shelter.getUniqueKey();
                        model.getShelterDataUnique(uniqueKey, new Consumer<Shelter>() {
                            @Override
                            public void accept(Shelter shelter) {
                                reload(shelter);
                            }
                        });
                    }
                }
                }
            });
        }
    }

    public void reload(Shelter shelter) {
        User user = model.getCurrentUser();

        shelterName.setText(shelter.getShelterName());
        capacity.setText(String.format("%d", shelter.getCapacity()));
        key.setText(String.format("%d", shelter.getUniqueKey()));
        vacancy.setText(String.format("%d", shelter.getVacancy()));
        restriction.setText(shelter.getRestriction());
        ShelterLocation loc = shelter.getLocation();
        longitude.setText(String.format("%f", loc.getLongitude()));
        latitude.setText(String.format("%f", loc.getLatitude()));
        address.setText(loc.getAddress());
        special.setText(shelter.getSpecial());
        phone.setText(shelter.getPhone());
        Integer beds = user.getHeldBeds();

        if (user.getCurrentShelterUniqueKey() == shelter.getUniqueKey()) {
            selfReport.setText(beds.toString());
        }
    }

    private ListActivity getInstance() {
        return this;
    }
}


