package com.eemf.sirgoingfar.tic_tac_toe.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eemf.sirgoingfar.tic_tac_toe.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //create a splash effect
        splashActivity();
    }

    private void splashActivity() {
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);
                    //create and start an intent for the next Activity to display
                    Intent goToPlayerMode = new Intent(getApplicationContext(),PlayerModeActivity.class);
                    startActivity(goToPlayerMode);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
