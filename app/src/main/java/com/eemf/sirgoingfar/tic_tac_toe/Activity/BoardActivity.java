package com.eemf.sirgoingfar.tic_tac_toe.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eemf.sirgoingfar.tic_tac_toe.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BoardActivity extends AppCompatActivity {

    //all buttons
    TextView btn_1;
    TextView btn_2;
    TextView btn_3;
    TextView btn_4;
    TextView btn_5;
    TextView btn_6;
    TextView btn_7;
    TextView btn_8;
    TextView btn_9;
    TextView btn_10;
    TextView btn_11;
    TextView btn_12;
    TextView btn_13;
    TextView btn_14;
    TextView btn_15;
    TextView btn_16;
    TextView btn_17;
    TextView btn_18;
    TextView btn_19;
    TextView btn_20;
    TextView btn_21;
    TextView btn_22;
    TextView btn_23;
    TextView btn_24;
    TextView btn_25;

    //3 by 3 Button Arrays
    TextView[] row1_3;
    TextView[] row2_3;
    TextView[] row3_3;
    TextView[] col1_3;
    TextView[] col2_3;
    TextView[] col3_3;
    TextView[] diagonal1_3;
    TextView[] diagonal2_3;

    //4 by 4 Button Arrays
    TextView[] row1_4;
    TextView[] row2_4;
    TextView[] row3_4;
    TextView[] row4_4;
    TextView[] col1_4;
    TextView[] col2_4;
    TextView[] col3_4;
    TextView[] col4_4;
    TextView[] diagonal1_4;
    TextView[] diagonal2_4;

    //5 by 5 Button Arrays
    TextView[] row1_5;
    TextView[] row2_5;
    TextView[] row3_5;
    TextView[] row4_5;
    TextView[] row5_5;
    TextView[] col1_5;
    TextView[] col2_5;
    TextView[] col3_5;
    TextView[] col4_5;
    TextView[] col5_5;
    TextView[] diagonal1_5;
    TextView[] diagonal2_5;

    Button boardReset;
    Button homeButton;
    Button restartGame;
    LinearLayout level4;
    LinearLayout level5;

    int boardType = PlayerSetupActivity.mBoardTypeBundle.getInt("boardType");
    int mPlayerType = PlayerModeActivity.selectedPlayingMode.getInt("selectedPlayerMode");


    //Computer (Neema) Play Processing Variables

    private boolean isGameOver = false;
    private boolean isThereTie = false;

    int currentPlayerColor;
    int initialFor = 0;
    int finalFor = 0;
    int initialAgainst = 0;
    int finalAgainst = 0;
    int forCount = 0;
    int againstCount = 0;
    int trackBoardWinMatch = 0;
    private final int BOARD3BY3 = 9;
    private final int BOARD4BY4 = 16;
    private final int BOARD5BY5 = 25;
    private int tieTracker = 0;
    private int playerMode = PlayerModeActivity.selectedPlayingMode.getInt("selectedPlayerMode");
    private int playerTwoScore = 0;
    private int playerOneScore = 0;

    private Bundle playerNames = PlayerSetupActivity.mPlayerName;

    String player;
    String nextPlayer;
    String textContent; //holds the TEXT inside the TextViews inside the allBoardButtonList when looping through the list
    private String mCurrentPlayer = "X";
    private String playerOneName = playerNames.getString("PlayerTwoName");
    private String playerTwoName = playerNames.getString("PlayerOneName");
    private String winnerName;
    private String winnerNameAlt;

    Integer value;
    Integer mapValue;

    TextView choice; //final button selected by the computer
    TextView mapKey;
    TextView playerOneNameTextView;
    TextView playerTwoNameTextView;
    TextView playerOneScoreTextView;
    TextView playerTwoScoreTextView;
    TextView currentTextView; //holds the TEXTVIEW inside the all boardButtonList when looping through the list
    TextView individualTextViewOnCurrentLine;
    TextView playingToTextView;

    HashMap<TextView, ArrayList<TextView[]>> rowsColsDiagonalsOfPossibleMoves; //gets the row, col and/or diagonal (if the possible move falls on the diagonal)
    HashMap<TextView, ArrayList<ArrayList<Integer>>> entireMoveScores;// scores of the entire possibleMoves
    HashMap<TextView, ArrayList<TextView[]>> rowsColsDiagonalsOfPossibleMovesHashMap = new HashMap<>();
    HashMap<TextView, ArrayList<ArrayList<Integer>>> entirePossibleMovesScore = new HashMap<>();
    HashMap<TextView, Integer> tenCount = new HashMap<>();
    HashMap<TextView, Integer> minusTenCount = new HashMap<>();

    ArrayList<TextView> allBoardButtonsList = new ArrayList<>();
    ArrayList<TextView[]> rowsColsDiagonals = new ArrayList<>();
    ArrayList<TextView> possibleMoves = new ArrayList<>(); //possible moves ArrayList
    ArrayList<TextView> currentLine = new ArrayList<>();
    ArrayList<TextView[]> allRowColDiagonalAPossibleMoveFalls;
    ArrayList<TextView> eitherRowColDiagonalAPossibleMoveFalls;


    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            isGameOver = false;
            isThereTie = false;
            playingToTextView = (TextView) view;
            if (checkIfEmpty(playingToTextView)) {
                if (checkWin()) {
                    isGameOver = true;
                    declareWinner();
                }

                //check for tie
                checkForTie();

                if (mPlayerType == 1 && !isGameOver && !isThereTie) {
                    computerPlayer();
                }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        //reference all TextView
        referenceAllTextViews();

        //get Board Type from Bundle BoardType
        setUpBoardView(boardType);

        //attach onClickListener to the board buttons
        attachOnClickListenerToBoardButtons();

        //populate the arraylist for all buttons
        createArrayListForButtonsBasedOnBoardType();

        //setup dashboard
        playerOneNameTextView = findViewById(R.id.playerOneName);
        playerTwoNameTextView = findViewById(R.id.playerTwoName);
        playerOneScoreTextView = findViewById(R.id.playerOneScore);
        playerTwoScoreTextView = findViewById(R.id.playerTwoScore);

        if (playerMode == 1) {
            playerTwoNameTextView.setText(playerNames.getCharSequence("PlayerOneName"));
            playerOneName = "Neema";
            playerOneNameTextView.setText(playerOneName);
        } else if (playerMode == 2) {
            playerTwoNameTextView.setText(playerNames.getCharSequence("PlayerTwoName"));
            playerOneNameTextView.setText(playerNames.getCharSequence("PlayerOneName"));
        }

    }

    private void referenceAllTextViews() {
        this.btn_1 = findViewById(R.id.btn_1);
        this.btn_2 = findViewById(R.id.btn_2);
        this.btn_3 = findViewById(R.id.btn_3);
        this.btn_4 = findViewById(R.id.btn_4);
        this.btn_5 = findViewById(R.id.btn_5);
        this.btn_6 = findViewById(R.id.btn_6);
        this.btn_7 = findViewById(R.id.btn_7);
        this.btn_8 = findViewById(R.id.btn_8);
        this.btn_9 = findViewById(R.id.btn_9);
        this.btn_10 = findViewById(R.id.btn_10);
        this.btn_11 = findViewById(R.id.btn_11);
        this.btn_12 = findViewById(R.id.btn_12);
        this.btn_13 = findViewById(R.id.btn_13);
        this.btn_14 = findViewById(R.id.btn_14);
        this.btn_15 = findViewById(R.id.btn_15);
        this.btn_16 = findViewById(R.id.btn_16);
        this.btn_17 = findViewById(R.id.btn_17);
        this.btn_18 = findViewById(R.id.btn_18);
        this.btn_19 = findViewById(R.id.btn_19);
        this.btn_20 = findViewById(R.id.btn_20);
        this.btn_21 = findViewById(R.id.btn_21);
        this.btn_22 = findViewById(R.id.btn_22);
        this.btn_23 = findViewById(R.id.btn_23);
        this.btn_24 = findViewById(R.id.btn_24);
        this.btn_25 = findViewById(R.id.btn_25);


        this.boardReset = findViewById(R.id.reset_btn);
        this.homeButton = findViewById(R.id.home_btn);
        this.restartGame = findViewById(R.id.restart_btn);

        this.level4 = findViewById(R.id.btn_layer_4);
        this.level5 = findViewById(R.id.btn_layer_5);
    }

    public String getmCurrentPlayer() {
        return mCurrentPlayer;
    }

    public void setmCurrentPlayer() {
        this.mCurrentPlayer = (mCurrentPlayer.equals("X") ? "O" : "X");
    }

    private void setUpBoardView(int boardType) {
        if (boardType == 1) {
            TextView[] buttonsToHide = {btn_4, btn_5, btn_9, btn_10, btn_14, btn_15, btn_16, btn_17,
                    btn_18, btn_19, btn_20, btn_21, btn_22, btn_23, btn_24, btn_25};

            for (TextView currentButton : buttonsToHide)
                currentButton.setVisibility(View.GONE);

            level4.setVisibility(View.GONE);
            level5.setVisibility(View.GONE);
        } else if (boardType == 2) {
            TextView[] buttonsToHide = {btn_5, btn_10, btn_15, btn_20, btn_21, btn_22, btn_23, btn_24, btn_25};

            for (TextView currentButton : buttonsToHide)
                currentButton.setVisibility(View.GONE);

            level5.setVisibility(View.GONE);
        }
    }

    private void attachOnClickListenerToBoardButtons() {
        if (boardType == 1) {
            btn_1.setOnClickListener(onClickListener);
            btn_2.setOnClickListener(onClickListener);
            btn_3.setOnClickListener(onClickListener);
            btn_6.setOnClickListener(onClickListener);
            btn_7.setOnClickListener(onClickListener);
            btn_8.setOnClickListener(onClickListener);
            btn_11.setOnClickListener(onClickListener);
            btn_12.setOnClickListener(onClickListener);
            btn_13.setOnClickListener(onClickListener);
        }

        else if (boardType == 2) {
            btn_1.setOnClickListener(onClickListener);
            btn_2.setOnClickListener(onClickListener);
            btn_3.setOnClickListener(onClickListener);
            btn_4.setOnClickListener(onClickListener);
            btn_6.setOnClickListener(onClickListener);
            btn_7.setOnClickListener(onClickListener);
            btn_8.setOnClickListener(onClickListener);
            btn_9.setOnClickListener(onClickListener);
            btn_11.setOnClickListener(onClickListener);
            btn_12.setOnClickListener(onClickListener);
            btn_13.setOnClickListener(onClickListener);
            btn_14.setOnClickListener(onClickListener);
            btn_16.setOnClickListener(onClickListener);
            btn_17.setOnClickListener(onClickListener);
            btn_18.setOnClickListener(onClickListener);
            btn_19.setOnClickListener(onClickListener);
        }

        else if (boardType == 3) {
            btn_1.setOnClickListener(onClickListener);
            btn_2.setOnClickListener(onClickListener);
            btn_3.setOnClickListener(onClickListener);
            btn_4.setOnClickListener(onClickListener);
            btn_5.setOnClickListener(onClickListener);
            btn_6.setOnClickListener(onClickListener);
            btn_7.setOnClickListener(onClickListener);
            btn_8.setOnClickListener(onClickListener);
            btn_9.setOnClickListener(onClickListener);
            btn_10.setOnClickListener(onClickListener);
            btn_11.setOnClickListener(onClickListener);
            btn_12.setOnClickListener(onClickListener);
            btn_13.setOnClickListener(onClickListener);
            btn_14.setOnClickListener(onClickListener);
            btn_15.setOnClickListener(onClickListener);
            btn_16.setOnClickListener(onClickListener);
            btn_17.setOnClickListener(onClickListener);
            btn_18.setOnClickListener(onClickListener);
            btn_19.setOnClickListener(onClickListener);
            btn_20.setOnClickListener(onClickListener);
            btn_21.setOnClickListener(onClickListener);
            btn_22.setOnClickListener(onClickListener);
            btn_23.setOnClickListener(onClickListener);
            btn_24.setOnClickListener(onClickListener);
            btn_25.setOnClickListener(onClickListener);
        }

        boardReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetBoard();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHome = new Intent(getApplicationContext(), PlayerModeActivity.class);
                finish();
                startActivity(goHome);
            }
        });

        restartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerOneScoreTextView.setText("0");
                playerTwoScoreTextView.setText("0");
                playerOneScore = 0;
                playerTwoScore = 0;
                resetBoard();
            }
        });

        //initialize ArrayList
        initializeArrayList();
    }

    public void createArrayListForButtonsBasedOnBoardType() {
        //add board buttons to an ArrayList
        if (boardType == 1) {
            allBoardButtonsList.add(btn_1);
            allBoardButtonsList.add(btn_2);
            allBoardButtonsList.add(btn_3);
            allBoardButtonsList.add(btn_6);
            allBoardButtonsList.add(btn_7);
            allBoardButtonsList.add(btn_8);
            allBoardButtonsList.add(btn_11);
            allBoardButtonsList.add(btn_12);
            allBoardButtonsList.add(btn_13);
        } else if (boardType == 2) {
            allBoardButtonsList.add(btn_1);
            allBoardButtonsList.add(btn_2);
            allBoardButtonsList.add(btn_3);
            allBoardButtonsList.add(btn_4);
            allBoardButtonsList.add(btn_6);
            allBoardButtonsList.add(btn_7);
            allBoardButtonsList.add(btn_8);
            allBoardButtonsList.add(btn_9);
            allBoardButtonsList.add(btn_11);
            allBoardButtonsList.add(btn_12);
            allBoardButtonsList.add(btn_13);
            allBoardButtonsList.add(btn_14);
            allBoardButtonsList.add(btn_16);
            allBoardButtonsList.add(btn_17);
            allBoardButtonsList.add(btn_18);
            allBoardButtonsList.add(btn_19);
        } else if (boardType == 3) {
            allBoardButtonsList.add(btn_1);
            allBoardButtonsList.add(btn_2);
            allBoardButtonsList.add(btn_3);
            allBoardButtonsList.add(btn_4);
            allBoardButtonsList.add(btn_5);
            allBoardButtonsList.add(btn_6);
            allBoardButtonsList.add(btn_7);
            allBoardButtonsList.add(btn_8);
            allBoardButtonsList.add(btn_9);
            allBoardButtonsList.add(btn_10);
            allBoardButtonsList.add(btn_11);
            allBoardButtonsList.add(btn_12);
            allBoardButtonsList.add(btn_13);
            allBoardButtonsList.add(btn_14);
            allBoardButtonsList.add(btn_15);
            allBoardButtonsList.add(btn_16);
            allBoardButtonsList.add(btn_17);
            allBoardButtonsList.add(btn_18);
            allBoardButtonsList.add(btn_19);
            allBoardButtonsList.add(btn_20);
            allBoardButtonsList.add(btn_21);
            allBoardButtonsList.add(btn_22);
            allBoardButtonsList.add(btn_23);
            allBoardButtonsList.add(btn_24);
            allBoardButtonsList.add(btn_25);
        }
    }

    private boolean checkIfEmpty(TextView clickedView) {

        if (clickedView.getText().toString().equals(" ")) {
            //switch color
            currentPlayerColor = (getmCurrentPlayer().equals("X") ? Color.BLACK : Color.RED);
            clickedView.setTextColor(currentPlayerColor);

            //change text
            clickedView.setText(getmCurrentPlayer());

            tieTracker++;

            return true;
        }

        return false;
    }

    public void initializeArrayList() {
        //3 by 3
        this.row1_3 = new TextView[]{btn_1, btn_2, btn_3};
        this.row2_3 = new TextView[]{btn_6, btn_7, btn_8};
        this.row3_3 = new TextView[]{btn_11, btn_12, btn_13};
        this.col1_3 = new TextView[]{btn_1, btn_6, btn_11};
        this.col2_3 = new TextView[]{btn_2, btn_7, btn_12};
        this.col3_3 = new TextView[]{btn_3, btn_8, btn_13};
        this.diagonal1_3 = new TextView[]{btn_1, btn_7, btn_13};
        this.diagonal2_3 = new TextView[]{btn_3, btn_7, btn_11};

        //4 by 4
        this.row1_4 = new TextView[]{btn_1, btn_2, btn_3, btn_4};
        this.row2_4 = new TextView[]{btn_6, btn_7, btn_8, btn_9};
        this.row3_4 = new TextView[]{btn_11, btn_12, btn_13, btn_14};
        this.row4_4 = new TextView[]{btn_16, btn_17, btn_18, btn_19};
        this.col1_4 = new TextView[]{btn_1, btn_6, btn_11, btn_16};
        this.col2_4 = new TextView[]{btn_2, btn_7, btn_12, btn_17};
        this.col3_4 = new TextView[]{btn_3, btn_8, btn_13, btn_18};
        this.col4_4 = new TextView[]{btn_4, btn_9, btn_14, btn_19};
        this.diagonal1_4 = new TextView[]{btn_1, btn_7, btn_13, btn_19};
        this.diagonal2_4 = new TextView[]{btn_4, btn_8, btn_12, btn_16};

        //5 by 5
        this.row1_5 = new TextView[]{btn_1, btn_2, btn_3, btn_4, btn_5};
        this.row2_5 = new TextView[]{btn_6, btn_7, btn_8, btn_9, btn_10};
        this.row3_5 = new TextView[]{btn_11, btn_12, btn_13, btn_14, btn_15};
        this.row4_5 = new TextView[]{btn_16, btn_17, btn_18, btn_19, btn_20};
        this.row5_5 = new TextView[]{btn_21, btn_22, btn_23, btn_24, btn_25};
        this.col1_5 = new TextView[]{btn_1, btn_6, btn_11, btn_16, btn_21};
        this.col2_5 = new TextView[]{btn_2, btn_7, btn_12, btn_17, btn_22};
        this.col3_5 = new TextView[]{btn_3, btn_8, btn_13, btn_18, btn_23};
        this.col4_5 = new TextView[]{btn_4, btn_9, btn_14, btn_19, btn_24};
        this.col5_5 = new TextView[]{btn_5, btn_10, btn_15, btn_20, btn_25};
        this.diagonal1_5 = new TextView[]{btn_1, btn_7, btn_13, btn_19, btn_25};
        this.diagonal2_5 = new TextView[]{btn_5, btn_9, btn_13, btn_17, btn_21};


        //3 by 3
        if (boardType == 1) {
            rowsColsDiagonals.add(this.row1_3);
            rowsColsDiagonals.add(this.row2_3);
            rowsColsDiagonals.add(this.row3_3);
            rowsColsDiagonals.add(this.col1_3);
            rowsColsDiagonals.add(this.col2_3);
            rowsColsDiagonals.add(this.col3_3);
            rowsColsDiagonals.add(this.diagonal1_3);
            rowsColsDiagonals.add(this.diagonal2_3);
        }

        //4 by 4
        else if (boardType == 2) {
            rowsColsDiagonals.add(this.row1_4);
            rowsColsDiagonals.add(this.row2_4);
            rowsColsDiagonals.add(this.row3_4);
            rowsColsDiagonals.add(this.row4_4);
            rowsColsDiagonals.add(this.col1_4);
            rowsColsDiagonals.add(this.col2_4);
            rowsColsDiagonals.add(this.col3_4);
            rowsColsDiagonals.add(this.col4_4);
            rowsColsDiagonals.add(this.diagonal1_4);
            rowsColsDiagonals.add(this.diagonal2_4);
        }

        //5 by 5
        else if (boardType == 3) {
            rowsColsDiagonals.add(this.row1_5);
            rowsColsDiagonals.add(this.row2_5);
            rowsColsDiagonals.add(this.row3_5);
            rowsColsDiagonals.add(this.row4_5);
            rowsColsDiagonals.add(this.row5_5);
            rowsColsDiagonals.add(this.col1_5);
            rowsColsDiagonals.add(this.col2_5);
            rowsColsDiagonals.add(this.col3_5);
            rowsColsDiagonals.add(this.col4_5);
            rowsColsDiagonals.add(this.col5_5);
            rowsColsDiagonals.add(this.diagonal1_5);
            rowsColsDiagonals.add(this.diagonal2_5);
        }
    }

    public void resetBoard() {
        if (boardType == 1) {
            btn_1.setText(" ");
            btn_2.setText(" ");
            btn_3.setText(" ");
            btn_6.setText(" ");
            btn_7.setText(" ");
            btn_8.setText(" ");
            btn_11.setText(" ");
            btn_12.setText(" ");
            btn_13.setText(" ");

            if (playerMode == 1) {
                possibleMoves.clear();

                if (entireMoveScores != null)
                    entireMoveScores.clear();

                tenCount.clear();
                minusTenCount.clear();
                finalAgainst = 0;
                finalFor = 0;
            }

            mCurrentPlayer = "X";
            tieTracker = 0;

        } else if (boardType == 2) {
            btn_1.setText(" ");
            btn_2.setText(" ");
            btn_3.setText(" ");
            btn_4.setText(" ");
            btn_6.setText(" ");
            btn_7.setText(" ");
            btn_8.setText(" ");
            btn_9.setText(" ");
            btn_11.setText(" ");
            btn_12.setText(" ");
            btn_13.setText(" ");
            btn_14.setText(" ");
            btn_16.setText(" ");
            btn_17.setText(" ");
            btn_18.setText(" ");
            btn_19.setText(" ");

            if (playerMode == 1) {
                possibleMoves.clear();

                if (entireMoveScores != null)
                    entireMoveScores.clear();

                tenCount.clear();
                minusTenCount.clear();
                finalAgainst = 0;
                finalFor = 0;
            }

            mCurrentPlayer = "X";
            tieTracker = 0;
        } else if (boardType == 3) {
            btn_1.setText(" ");
            btn_2.setText(" ");
            btn_3.setText(" ");
            btn_4.setText(" ");
            btn_5.setText(" ");
            btn_6.setText(" ");
            btn_7.setText(" ");
            btn_8.setText(" ");
            btn_9.setText(" ");
            btn_10.setText(" ");
            btn_11.setText(" ");
            btn_12.setText(" ");
            btn_13.setText(" ");
            btn_14.setText(" ");
            btn_15.setText(" ");
            btn_16.setText(" ");
            btn_17.setText(" ");
            btn_18.setText(" ");
            btn_19.setText(" ");
            btn_20.setText(" ");
            btn_21.setText(" ");
            btn_22.setText(" ");
            btn_23.setText(" ");
            btn_24.setText(" ");
            btn_25.setText(" ");

            if (playerMode == 1) {
                possibleMoves.clear();

                if (entireMoveScores != null)
                    entireMoveScores.clear();

                tenCount.clear();
                minusTenCount.clear();
                finalAgainst = 0;
                finalFor = 0;
            }

            mCurrentPlayer = "X";
            tieTracker = 0;
        }
    }

    public void computerPlayer() {

        //get a list of possible moves
        for (int count = 0; count < allBoardButtonsList.size(); count++) {
            currentTextView = allBoardButtonsList.get(count);
            textContent = currentTextView.getText().toString();
            if (textContent.equals(" "))
                possibleMoves.add(currentTextView);
        }

        rowsColsDiagonalsOfPossibleMoves = checkDiagonalsOrRowsThatThePossibleMovesBelongTo(possibleMoves);
        entireMoveScores = checkWinLoseNoWin(possibleMoves, rowsColsDiagonalsOfPossibleMoves);
        computeScoreFrequency(possibleMoves, entireMoveScores);

        choice = chooseTheRightMove(possibleMoves, entireMoveScores);

        playComputerTurn(choice);

    }

    private void computeScoreFrequency(ArrayList<TextView> possibleMoves, HashMap<TextView, ArrayList<ArrayList<Integer>>> entireMoveScores) {
        for (TextView possibleMove : possibleMoves) {
            for (ArrayList<Integer> currentMove : entireMoveScores.get(possibleMove)) {
                for (Integer a : currentMove) {
                    if (a.intValue() == 10)
                        forCount++;
                    else if (a.intValue() == -10)
                        againstCount++;
                }
            }
            tenCount.put(possibleMove, forCount);
            minusTenCount.put(possibleMove, againstCount);
            forCount = 0;
            againstCount = 0;
        }
    }

    private void playComputerTurn(TextView choice) {
        if (checkIfEmpty(choice)) {
            possibleMoves.clear();
            entireMoveScores.clear();
            tenCount.clear();
            minusTenCount.clear();
            finalAgainst = 0;
            finalFor = 0;
            if (checkWin()) {
                declareWinner();
            }

            checkForTie();

        } else {
            possibleMoves.clear();
            entireMoveScores.clear();
            tenCount.clear();
            minusTenCount.clear();
            finalAgainst = 0;
            finalFor = 0;
            computerPlayer();
        }
    }

    private TextView chooseTheRightMove(ArrayList<TextView> possibleMoves,
                                        HashMap<TextView, ArrayList<ArrayList<Integer>>> entireMoveScores) {
        choice = null;

        for (TextView possibleMove : possibleMoves) {

            initialFor = tenCount.get(possibleMove);
            if (finalFor < initialFor)
                finalFor = initialFor;

            initialAgainst = minusTenCount.get(possibleMove);
            if (finalAgainst < initialAgainst)
                finalAgainst = initialAgainst;
        }

        if (boardType == 1)
            trackBoardWinMatch = 3;
        else if (boardType == 2)
            trackBoardWinMatch = 4;
        else if (boardType == 2)
            trackBoardWinMatch = 5;

        //check if the opponent will win if I make a stupid move
        if (finalAgainst >= (trackBoardWinMatch - 1)) {
            return decisionBox(-10);
        }

        //check if the computer can win with the next move
        if (finalFor >= (trackBoardWinMatch - 1)) {
            return decisionBox(10);
        }


        //if the opponent and the computer is not winning, proceed
        if (finalFor > finalAgainst) {
            for (Map.Entry<TextView, Integer> entry : tenCount.entrySet()) {
                mapKey = entry.getKey();
                mapValue = entry.getValue();

                if (mapValue == finalFor) {
                    choice = mapKey;
                    return choice;
                }
                return decisionBox(10);
            }
        } else {
            //quickly gain the central pivotal move (the one that's always on the two diagonals)
            if (boardType == 1) {
                if (btn_7.getText().toString().equals(" ")) {
                    choice = btn_7;
                    return choice;
                }
            } else if (boardType == 3) {
                if (btn_13.getText().toString().equals(" ")) {
                    choice = btn_13;
                    return choice;
                }
            }

            return decisionBox(10);
        }
        return null;
    }

    private HashMap<TextView, ArrayList<ArrayList<Integer>>> checkWinLoseNoWin
            (ArrayList<TextView> possibleMoves,
             HashMap<TextView, ArrayList<TextView[]>> rowsColsDiagonalsOfPossibleMoves) {

        nextPlayer = getmCurrentPlayer() == "X" ? "O" : "X";

        for (TextView possibleMove : possibleMoves) {

            ArrayList<ArrayList<Integer>> scoreCombined = new ArrayList<>();

            allRowColDiagonalAPossibleMoveFalls = rowsColsDiagonalsOfPossibleMoves.get(possibleMove);

            for (int i = 0; i < allRowColDiagonalAPossibleMoveFalls.size(); i++) {
                eitherRowColDiagonalAPossibleMoveFalls = new ArrayList<>(Arrays.asList(allRowColDiagonalAPossibleMoveFalls.get(i)));

                ArrayList<Integer> individualScore = new ArrayList<>();

                for (TextView currentNeighbouringRowColDiagonalTextView : eitherRowColDiagonalAPossibleMoveFalls) {


                    if (getmCurrentPlayer().equals(currentNeighbouringRowColDiagonalTextView.getText().toString())) {
                        value = Integer.valueOf(10);
                        individualScore.add(value);
                    } else if (nextPlayer.equals(currentNeighbouringRowColDiagonalTextView.getText().toString())) {
                        value = Integer.valueOf(-10);
                        individualScore.add(value);
                    } else {
                        value = Integer.valueOf(0);
                        individualScore.add(value);
                    }
                }

                scoreCombined.add(individualScore);
            }
            entirePossibleMovesScore.put(possibleMove, scoreCombined);
        }
        return entirePossibleMovesScore;
    }

    private HashMap<TextView, ArrayList<TextView[]>> checkDiagonalsOrRowsThatThePossibleMovesBelongTo(ArrayList<TextView> possibleMoves) {
        for (TextView currentMovePosition : possibleMoves) {
            ArrayList<TextView[]> rowsColsDiagonalsOfPossibleMovesHolder = new ArrayList<>();

            for (int count = 0; count < rowsColsDiagonals.size(); count++) {

                currentLine.addAll(Arrays.asList(rowsColsDiagonals.get(count)));

                for (int i = 0; i < currentLine.size(); i++) {
                    individualTextViewOnCurrentLine = currentLine.get(i);

                    if (individualTextViewOnCurrentLine.equals(currentMovePosition) && !(rowsColsDiagonalsOfPossibleMovesHolder.contains(rowsColsDiagonals.get(count)))) {
                        rowsColsDiagonalsOfPossibleMovesHolder.add(rowsColsDiagonals.get(count));
                    }
                }
                currentLine.clear();
            }
            rowsColsDiagonalsOfPossibleMovesHashMap.put(currentMovePosition, rowsColsDiagonalsOfPossibleMovesHolder);
        }
        return rowsColsDiagonalsOfPossibleMovesHashMap;
    }

    private boolean checkWin() {
        player = getmCurrentPlayer();
        switch (boardType) {
            //3 by 3 board
            case 1: {
                if (
                    //horizontal check
                        (btn_1.getText().toString().equals(player) &&
                                btn_2.getText().toString().equals(player) &&
                                btn_3.getText().toString().equals(player))

                                ||

                                (btn_6.getText().toString().equals(player) &&
                                        btn_7.getText().toString().equals(player) &&
                                        btn_8.getText().toString().equals(player))

                                ||

                                (btn_11.getText().toString().equals(player) &&
                                        btn_12.getText().toString().equals(player) &&
                                        btn_13.getText().toString().equals(player))

                                ||

                                //vertical check
                                (btn_1.getText().toString().equals(player) &&
                                        btn_6.getText().toString().equals(player) &&
                                        btn_11.getText().toString().equals(player))

                                ||

                                (btn_2.getText().toString().equals(player) &&
                                        btn_7.getText().toString().equals(player) &&
                                        btn_12.getText().toString().equals(player))


                                ||

                                (btn_3.getText().toString().equals(player) &&
                                        btn_8.getText().toString().equals(player) &&
                                        btn_13.getText().toString().equals(player))


                                ||

                                //diagonal check
                                (btn_1.getText().toString().equals(player) &&
                                        btn_7.getText().toString().equals(player) &&
                                        btn_13.getText().toString().equals(player))


                                ||

                                (btn_3.getText().toString().equals(player) &&
                                        btn_7.getText().toString().equals(player) &&
                                        btn_11.getText().toString().equals(player))
                        )
                    return true;
            }

            //4 by 4 board
            case 2: {
                if (
                    //horizontal check
                        (btn_1.getText().toString().equals(player) &&
                                btn_2.getText().toString().equals(player) &&
                                btn_3.getText().toString().equals(player) &&
                                btn_4.getText().toString().equals(player))

                                ||

                                (btn_6.getText().toString().equals(player) &&
                                        btn_7.getText().toString().equals(player) &&
                                        btn_8.getText().toString().equals(player) &&
                                        btn_9.getText().toString().equals(player))

                                ||

                                (btn_11.getText().toString().equals(player) &&
                                        btn_12.getText().toString().equals(player) &&
                                        btn_13.getText().toString().equals(player) &&
                                        btn_14.getText().toString().equals(player))

                                ||

                                (btn_16.getText().toString().equals(player) &&
                                        btn_17.getText().toString().equals(player) &&
                                        btn_18.getText().toString().equals(player) &&
                                        btn_19.getText().toString().equals(player))

                                ||

                                //vertical check
                                (btn_1.getText().toString().equals(player) &&
                                        btn_6.getText().toString().equals(player) &&
                                        btn_11.getText().toString().equals(player) &&
                                        btn_16.getText().toString().equals(player))

                                ||

                                (btn_2.getText().toString().equals(player) &&
                                        btn_7.getText().toString().equals(player) &&
                                        btn_12.getText().toString().equals(player) &&
                                        btn_17.getText().toString().equals(player))

                                ||

                                (btn_3.getText().toString().equals(player) &&
                                        btn_8.getText().toString().equals(player) &&
                                        btn_13.getText().toString().equals(player) &&
                                        btn_18.getText().toString().equals(player))

                                ||

                                (btn_4.getText().toString().equals(player) &&
                                        btn_9.getText().toString().equals(player) &&
                                        btn_14.getText().toString().equals(player) &&
                                        btn_19.getText().toString().equals(player))

                                ||

                                (btn_5.getText().toString().equals(player) &&
                                        btn_10.getText().toString().equals(player) &&
                                        btn_15.getText().toString().equals(player) &&
                                        btn_20.getText().toString().equals(player))

                                ||

                                //diagonal check
                                (btn_1.getText().toString().equals(player) &&
                                        btn_7.getText().toString().equals(player) &&
                                        btn_13.getText().toString().equals(player) &&
                                        btn_19.getText().toString().equals(player))

                                ||

                                (btn_4.getText().toString().equals(player) &&
                                        btn_8.getText().toString().equals(player) &&
                                        btn_12.getText().toString().equals(player) &&
                                        btn_16.getText().toString().equals(player))

                        )
                    return true;
            }

            //5 by 5 board
            case 3: {
                if (
                    //horizontal check
                        (btn_1.getText().toString().equals(player) &&
                                btn_2.getText().toString().equals(player) &&
                                btn_3.getText().toString().equals(player) &&
                                btn_4.getText().toString().equals(player) &&
                                btn_5.getText().toString().equals(player))

                                ||

                                (btn_6.getText().toString().equals(player) &&
                                        btn_7.getText().toString().equals(player) &&
                                        btn_8.getText().toString().equals(player) &&
                                        btn_9.getText().toString().equals(player) &&
                                        btn_10.getText().toString().equals(player))

                                ||

                                (btn_11.getText().toString().equals(player) &&
                                        btn_12.getText().toString().equals(player) &&
                                        btn_13.getText().toString().equals(player) &&
                                        btn_14.getText().toString().equals(player) &&
                                        btn_15.getText().toString().equals(player))

                                ||

                                (btn_16.getText().toString().equals(player) &&
                                        btn_17.getText().toString().equals(player) &&
                                        btn_18.getText().toString().equals(player) &&
                                        btn_19.getText().toString().equals(player) &&
                                        btn_20.getText().toString().equals(player))

                                ||

                                (btn_21.getText().toString().equals(player) &&
                                        btn_22.getText().toString().equals(player) &&
                                        btn_23.getText().toString().equals(player) &&
                                        btn_24.getText().toString().equals(player) &&
                                        btn_25.getText().toString().equals(player))

                                ||

                                //vertical check
                                (btn_1.getText().toString().equals(player) &&
                                        btn_6.getText().toString().equals(player) &&
                                        btn_11.getText().toString().equals(player) &&
                                        btn_16.getText().toString().equals(player) &&
                                        btn_21.getText().toString().equals(player))

                                ||

                                (btn_2.getText().toString().equals(player) &&
                                        btn_7.getText().toString().equals(player) &&
                                        btn_12.getText().toString().equals(player) &&
                                        btn_17.getText().toString().equals(player) &&
                                        btn_22.getText().toString().equals(player))


                                ||

                                (btn_3.getText().toString().equals(player) &&
                                        btn_8.getText().toString().equals(player) &&
                                        btn_13.getText().toString().equals(player) &&
                                        btn_18.getText().toString().equals(player) &&
                                        btn_23.getText().toString().equals(player))

                                ||

                                (btn_4.getText().toString().equals(player) &&
                                        btn_9.getText().toString().equals(player) &&
                                        btn_14.getText().toString().equals(player) &&
                                        btn_19.getText().toString().equals(player) &&
                                        btn_24.getText().toString().equals(player))

                                ||

                                (btn_5.getText().toString().equals(player) &&
                                        btn_10.getText().toString().equals(player) &&
                                        btn_15.getText().toString().equals(player) &&
                                        btn_20.getText().toString().equals(player) &&
                                        btn_25.getText().toString().equals(player))

                                ||

                                //diagonal check
                                (btn_1.getText().toString().equals(player) &&
                                        btn_7.getText().toString().equals(player) &&
                                        btn_13.getText().toString().equals(player) &&
                                        btn_19.getText().toString().equals(player) &&
                                        btn_25.getText().toString().equals(player))


                                ||

                                (btn_5.getText().toString().equals(player) &&
                                        btn_9.getText().toString().equals(player) &&
                                        btn_13.getText().toString().equals(player) &&
                                        btn_17.getText().toString().equals(player) &&
                                        btn_21.getText().toString().equals(player))
                        )
                    return true;
            }
            default: {
            }
        }

        //change Player
        setmCurrentPlayer();

        return false;
    }

    private void declareWinner() {
        winnerName = (getmCurrentPlayer() == "O" ? playerOneName : playerTwoName);
        winnerNameAlt = (getmCurrentPlayer() == "X" ? playerOneName : playerTwoName);
        if (playerMode == 1)
            Toast.makeText(this, winnerName + " won!", Toast.LENGTH_SHORT).show();
        else if (playerMode == 2)
            Toast.makeText(this, winnerNameAlt + " won!", Toast.LENGTH_SHORT).show();

        //increment the winner's score
        if (playerMode == 1) {
            if (winnerName.equals(playerOneName)) {
                playerOneScore++;
            } else if (winnerName.equals(playerTwoName)) {
                playerTwoScore++;
            }
        } else if (playerMode == 2) {
            if (winnerName.equals(playerOneName)) {
                playerTwoScore++;
            } else if (winnerName.equals(playerTwoName)) {
                playerOneScore++;
            }
        }

        //set the UI
        if (playerMode == 1) {
            if (winnerName.equals(playerOneName))
                playerOneScoreTextView.setText(String.valueOf(playerOneScore));
            else if (winnerName.equals(playerTwoName))
                playerTwoScoreTextView.setText(String.valueOf(playerTwoScore));
        } else if (playerMode == 2) {
            if (winnerName.equals(playerOneName))
                playerOneScoreTextView.setText(String.valueOf(playerTwoScore));
            else if (winnerName.equals(playerTwoName))
                playerTwoScoreTextView.setText(String.valueOf(playerOneScore));
        }

        resetBoard();
    }

    private TextView decisionBox(int scoreToFind) {

//        if (finalFor == finalAgainst) {
//            for (Map.Entry<TextView, Integer> entry : tenCount.entrySet()) {
//                mapKey = entry.getKey();
//                mapValue = entry.getValue();
//
//                if (mapValue >= finalFor) {
//                    choice = mapKey;
//                    return choice;
//                }
//            }
//        }

        //#1
        for (TextView possibleMove : possibleMoves) {
            for (ArrayList<Integer> a : entireMoveScores.get(possibleMove)) {
                int count = 0;
                for (Integer b : a) {
                    if (b.intValue() == scoreToFind) {
                        count++;
                    }
                }
                if (count >= (trackBoardWinMatch - 1) && " ".equals(possibleMove.getText().toString())) {
                    choice = possibleMove;
                    return choice;
                }
            }
        }

        //#2
        for (Map.Entry<TextView, Integer> entry : tenCount.entrySet()) {
            mapKey = entry.getKey();
            mapValue = entry.getValue();

            if (mapValue >= finalFor) {
                choice = mapKey;
                return choice;
            }
        }

        //#3
        for (TextView possibleMove : possibleMoves) {
            if (" ".equals(possibleMove.getText().toString())) {
                choice = possibleMove;
                return choice;
            }
        }

        return null;
    }

    private boolean checkForTie() {
        switch (boardType) {
            //3 by 3 board
            case 1: {
                if (tieTracker == BOARD3BY3) {
                    Toast.makeText(this, "You both had a Tie", Toast.LENGTH_SHORT).show();   //show pop-up here
                    isThereTie = true;
                    resetBoard();
                    return false;
                }
            }
            //4 by 4
            case 2: {
                if (tieTracker == BOARD4BY4) {
                    Toast.makeText(this, "You both had a Tie", Toast.LENGTH_SHORT).show();   //show pop-up here
                    isThereTie = true;
                    resetBoard();
                    return false;
                }
            }
            //5 by 5
            case 3: {
                if (tieTracker == BOARD5BY5) {
                    Toast.makeText(this, "You both had a Tie", Toast.LENGTH_SHORT).show();   //show pop-up here
                    isThereTie = true;
                    resetBoard();
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * on @value KEYCODE_BACK is pressed, @link onKeyDown(keyCode, event) is called
     *
     * @param keyCode is the key code for the key pressed
     * @param event   is the key event
     * @return true or @return the event
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                resetBoard();
                finish();
                Intent goBack = new Intent(this, PlayerSetupActivity.class);
                startActivity(goBack);
                return true;

        }
        return super.onKeyDown(keyCode, event);
    }
}
