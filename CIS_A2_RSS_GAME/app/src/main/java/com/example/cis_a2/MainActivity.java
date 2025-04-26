
package com.example.cis_a2;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
// This app shows you what button you click by shading it under and creating a click sound each time you click
// Once the game reaches a final score the buttons will be disabled except the replay button
// The game ends when the total score of 5 is reached.
public class MainActivity extends Activity {
    //private variables for the game rules enforcement
    private int playerScore = 0;
    private int opponentScore = 0;

    //onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //the textview I will use
        final TextView resultTextView = findViewById(R.id.result_text_view);
        final TextView scoreTextView = findViewById(R.id.score_text_view);

        //create each button object
        Button rockButton = findViewById(R.id.rock_button);
        Button paperButton = findViewById(R.id.paper_button);
        Button spearButton = findViewById(R.id.spear_button);
        Button replayGame = findViewById(R.id.replay_game);
        //rock button
        rockButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                playRound("ROCK", resultTextView, scoreTextView, rockButton, paperButton, spearButton);
            }
        });

        //paper button
        paperButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                playRound("PAPER", resultTextView, scoreTextView, rockButton, paperButton, spearButton);
            }
        });

        //spear button
        spearButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                playRound("SPEAR", resultTextView, scoreTextView, rockButton, paperButton, spearButton);
            }
        });

        //reset all variables when replayed
        replayGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            //reset all values
            public void onClick(View v) {
                playerScore = 0;
                opponentScore = 0;
                resultTextView.setText("");
                scoreTextView.setText("");
                //Enable the buttons back
                rockButton.setEnabled(true);
                paperButton.setEnabled(true);
                spearButton.setEnabled(true);
            }
        });
    }
    // ******************* BUTTONS ABOVE ***************************** //
    private void playRound(String playerMove, TextView resultTextView, TextView scoreTextView, Button rockButton, Button paperButton, Button spearButton)
    {
        // If the total score is 5 or more, don't proceed with the round
        if (playerScore + opponentScore >= 5)
        {
            return;
        }

        String[] moves = {"ROCK", "PAPER", "SPEAR"};//array of string objects
        String opponentMove = moves[new Random().nextInt(moves.length)];

        //round tie
        if (playerMove.equals(opponentMove))
        {
            resultTextView.setText(String.format("It's a draw! Both players chose %s.", playerMove));
        }
        //win the round
        else if ((playerMove.equals("ROCK") && opponentMove.equals("SPEAR")) ||
                (playerMove.equals("SPEAR") && opponentMove.equals("PAPER")) ||
                (playerMove.equals("PAPER") && opponentMove.equals("ROCK")))
        {
            playerScore++;
            resultTextView.setText(String.format("You win the round! Your move: %s beats Opponent's Move: %s.", playerMove, opponentMove));
        }
        //lose the round
        else
        {
            opponentScore++;
            resultTextView.setText(String.format("You lose the round! Opponent move: %s beats your move: %s.", opponentMove, playerMove));
        }
        scoreTextView.setText("Score: You " + playerScore + " - " + opponentScore + " Opponent");

        // Check if the total score is 5 or more after playing the round
        if (playerScore + opponentScore >= 5) {
            // Disable all the buttons except replay after game completion
            rockButton.setEnabled(false);
            paperButton.setEnabled(false);
            spearButton.setEnabled(false);

            // Display the final result
            if (playerScore > opponentScore)
            {
                //I used + normally but the android studio kept disturbing my eye with the yellow underline
                //so I changed to this String.format
                resultTextView.setText(String.format("Congratulations! You won the game!\nYour move: %s beats Opponent's move: %s", playerMove, opponentMove));
            }
            else
            {
                resultTextView.setText(String.format("Sorry, you lost the game.\nYour move: %s is beaten by your opponent's move: %s", playerMove, opponentMove));
            }
        }
    }
}
