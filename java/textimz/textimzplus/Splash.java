package textimz.textimzplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

         // Create a Timer
        Timer RunSplash = new Timer();


        // Task to do when the timer ends
        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {
                startHome();
            }
        };

        // Start the timer
        // Set Duration of the Splash Screen(in mili-secs)
        RunSplash.schedule(ShowSplash, 1000);

        //setContentView(R.layout.activity_splash);
    }

    private void startHome() {
        startActivity(new Intent(Splash.this, Home.class));
        finish();
    }
}
