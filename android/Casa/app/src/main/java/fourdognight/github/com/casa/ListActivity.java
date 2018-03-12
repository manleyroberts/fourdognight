package fourdognight.github.com.casa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fourdognight.github.com.casa.model.AbstractUser;
import fourdognight.github.com.casa.model.ModelFacade;
import fourdognight.github.com.casa.model.Shelter;
import fourdognight.github.com.casa.model.User;

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
    //Creates all the "text boxes" for the shelter information on page when shelter is clicked
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

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
            final AbstractUser user = (AbstractUser) bundle.get("User");
            shelterName.setText(shelter.getShelterName());
            capacity.setText(String.format("%d", shelter.getCapacity()));
            key.setText(String.format("%d", shelter.getUniqueKey()));
            vacancy.setText(String.format("%d", shelter.getVacancy()));
            restriction.setText(shelter.getRestriction());
            longitude.setText(String.format("%f", shelter.getLongitude()));
            latitude.setText(String.format("%f", shelter.getLatitude()));
            address.setText(shelter.getAddress());
            special.setText(shelter.getSpecial());
            phone.setText(shelter.getPhone());
            if ((user instanceof User) && ((User) user).getCurrentShelter() != null &&
                    ((User) user).getCurrentShelter().getUniqueKey() == shelter.getUniqueKey()) {
                selfReport.setText(((Integer) ((User) user).getHeldBeds()).toString());
            }

            final Button button = findViewById(R.id.updateVacancyButton);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (user instanceof User) {
                        selfReport.setError(null);
                        int intendedBeds = Integer.parseInt(selfReport.getText().toString());
                        if (((User) user).getCurrentShelter() != null
                                && ((User) user).getCurrentShelter().getUniqueKey()
                                != shelter.getUniqueKey() && ((User) user).getHeldBeds() > 0) {
                            selfReport.setError(getString(R.string.error_held_beds_elsewhere));
                            selfReport.requestFocus();
                        } else if (TextUtils.isEmpty(selfReport.getText()) || intendedBeds < 0 ||
                                !ModelFacade.getInstance().updateVacancy(shelter, (User) user,
                                        Integer.parseInt(selfReport.getText().toString()))) {
                            selfReport.setError(getString(R.string.error_wrong_bed_number));
                            selfReport.requestFocus();
                        } else {
                            Intent intent = new Intent(ListActivity.this, fourdognight.github.com.casa.ListActivity.class);
                            intent.putExtra("Shelter", shelter);
                            intent.putExtra("User", user);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            });
        }
    }
}


