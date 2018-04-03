package fourdognight.github.com.casa.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

import fourdognight.github.com.casa.R;
import fourdognight.github.com.casa.model.ModelFacade;

public class SplashActivity extends AppCompatActivity {

    private ModelFacade model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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

    private SplashActivity getInstance() {
        return this;
    }
}
