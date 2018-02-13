package fourdognight.github.com.casa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Button button = findViewById(R.id.loginChoiceButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launchIntent = new Intent(getInstance(), LoginActivity.class);
                startActivity(launchIntent);
            }
        });
    }

    private SplashActivity getInstance() {
        return this;
    }
}
