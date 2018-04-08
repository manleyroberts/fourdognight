package fourdognight.github.com.casa.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

import fourdognight.github.com.casa.R;
import fourdognight.github.com.casa.model.ModelFacade;

<<<<<<< HEAD
/**
 * @author  Manley Roberts
 * @version 1.0
 *
 * creates the first page of the app, the opening page
 */
public class SplashActivity extends AppCompatActivity {

    private ModelFacade model;

=======
public class SplashActivity extends AppCompatActivity {

>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

<<<<<<< HEAD
        model = ModelFacade.getInstance();
        model.init();

        final Button logButton = findViewById(R.id.loginChoiceButton);
        logButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launchIntent = new Intent(getInstance(), LoginActivity.class);
                launchIntent.putExtra("justRegistered", false);
                startActivity(launchIntent);
            }
        });
        final Button regButton = findViewById(R.id.registerChoiceButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launchIntent = new Intent(getInstance(), RegistrationActivity.class);
                startActivity(launchIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

=======
        ModelFacade model = ModelFacade.getInstance();
        model.init();

        final Button logButton = findViewById(R.id.loginChoiceButton);
        logButton.setOnClickListener(v -> {
            Intent launchIntent = new Intent(getInstance(), LoginActivity.class);
            launchIntent.putExtra("justRegistered", false);
            startActivity(launchIntent);
        });
        final Button regButton = findViewById(R.id.registerChoiceButton);
        regButton.setOnClickListener(v -> {
            Intent launchIntent = new Intent(getInstance(), RegistrationActivity.class);
            startActivity(launchIntent);
        });
    }

>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
    private SplashActivity getInstance() {
        return this;
    }
}
