package com.eemf.sirgoingfar.tic_tac_toe.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eemf.sirgoingfar.tic_tac_toe.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerModeActivity extends AppCompatActivity {
    @BindView(R.id.single_player_btn) Button singlePlayerButton;
    @BindView(R.id.double_player_btn) Button doublePlayerButton;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View selectedPlayerMode) {
            processPlayerMode(selectedPlayerMode);
        }
    };

    private int selectedPlayingOption;
    public static Bundle selectedPlayingMode = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_mode);
        ButterKnife.bind(this);

        //attach OnClickListener
        attachOnClickListenerToPlayerModeButtons();
    }

    private void attachOnClickListenerToPlayerModeButtons() {
        //attach Listener
        singlePlayerButton.setOnClickListener(onClickListener);
        doublePlayerButton.setOnClickListener(onClickListener);
    }


    private void processPlayerMode(View selectedPlayerMode) {
        switch (selectedPlayerMode.getId()){
            case R.id.single_player_btn: {
                this.selectedPlayingOption = 1;
                //Bundle the selected option in order
                selectedPlayingMode.putInt("selectedPlayerMode",selectedPlayingOption);
                toNextActivity();
                break;
            }
            case R.id.double_player_btn: {
                this.selectedPlayingOption = 2;
                //Bundle the selected option in order
                selectedPlayingMode.putInt("selectedPlayerMode",selectedPlayingOption);
                toNextActivity();
                break;
            }
        }
    }

    private void toNextActivity(){
        //switch scene to the next activity
        Intent playerSetUpActivityIntent = new Intent(getApplicationContext(),PlayerSetupActivity.class);
        startActivity(playerSetUpActivityIntent);

    }

    /**
     * on @value KEYCODE_BACK is pressed, @link onKeyDown(keyCode, event) is called
     * @param keyCode is the key code for the key pressed
     * @param event is the key event
     * @return true or @return the event
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                Intent minimize = new Intent(Intent.ACTION_MAIN);
                minimize.addCategory(Intent.CATEGORY_HOME);
                minimize.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(minimize);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
