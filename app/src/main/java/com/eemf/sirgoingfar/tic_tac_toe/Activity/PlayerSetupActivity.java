package com.eemf.sirgoingfar.tic_tac_toe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.eemf.sirgoingfar.tic_tac_toe.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerSetupActivity extends AppCompatActivity {

    int mPlayerType;
    int mBoardType;
    String mPlayerOneNameValue;
    String mPlayerTwoNameValue;
    static Bundle mBoardTypeBundle, mPlayerName, gameMode;

    EditText mPlayerOneName;
    EditText mPlayerTwoName;
    @BindView(R.id.spinner) Spinner mBoardTypeSpinner;
    Button mStartGame;

    ArrayAdapter<CharSequence> mBoardTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);
        ButterKnife.bind(this);

        mPlayerType = PlayerModeActivity.selectedPlayingMode.getInt("selectedPlayerMode");
        adjustView();

        //set up spinner and its adapter
        mBoardTypeAdapter = ArrayAdapter.createFromResource(this, R.array.board_type, R.layout.spinner_list);
        mBoardTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBoardTypeSpinner.setAdapter(mBoardTypeAdapter);
        mBoardTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mBoardTypeBundle = new Bundle();
                mBoardTypeBundle.putInt("boardType", position);
                mBoardType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mStartGame = findViewById(R.id.start_game_button);
        mStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBoardType == 0) {
                    Toast.makeText(getApplicationContext(), "Select a board type", Toast.LENGTH_LONG).show();
                } else {
                    processBoard();
                    toNextActivity();
                }
            }
        });
    }

    protected void adjustView() {
        mPlayerOneName = findViewById(R.id.player_1_name);
        mPlayerTwoName = findViewById(R.id.player_2_name);

        if (mPlayerType == 1) {
            mPlayerTwoName.setVisibility(View.GONE);
            mPlayerOneName.setHint("Enter your name");
        }
    }

    protected void processBoard() {
        gameMode = new Bundle();
        int numberOfPlayers = PlayerModeActivity.selectedPlayingMode.getInt("selectedPlayerMode");

        if (mPlayerOneName.getText().toString().isEmpty()) {
            mPlayerOneNameValue = "Player 1";
            if(numberOfPlayers == 1)
                mPlayerOneNameValue = "Other Player";
        } else {
            mPlayerOneNameValue = mPlayerOneName.getText().toString();
        }

        if (mPlayerTwoName.getText().toString().isEmpty()) {
            mPlayerTwoNameValue = "Player 2";
        } else {
            mPlayerTwoNameValue = mPlayerTwoName.getText().toString();
        }

        //Bundle the player names
        mPlayerName = new Bundle();
        mPlayerName.putString("PlayerOneName", mPlayerOneNameValue);

        //don't put the second player name if it's in a single player mode
        if (numberOfPlayers != 1)
            mPlayerName.putString("PlayerTwoName", mPlayerTwoNameValue);
    }

    private void toNextActivity() {
        //switch scene to the next activity
        Intent boardActivityIntent = new Intent(getApplicationContext(), BoardActivity.class);
        startActivity(boardActivityIntent);
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
                if(mBoardTypeBundle != null)
                mBoardTypeBundle.clear();

                if(mPlayerName != null)
                mPlayerName.clear();

                if(gameMode != null)
                gameMode.clear();

                Intent goBack = new Intent(this,PlayerModeActivity.class);
                finish();
                startActivity(goBack);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
