package fourdognight.github.com.casa.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

import fourdognight.github.com.casa.R;
import fourdognight.github.com.casa.model.ModelFacade;

/**
 * creates the first page of the app, opening page
 * @author Manley Roberts
 * @version 1.0
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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

    private SplashActivity getInstance() {
        return this;
    }
}
