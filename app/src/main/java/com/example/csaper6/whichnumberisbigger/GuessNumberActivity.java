package com.example.csaper6.whichnumberisbigger;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class GuessNumberActivity extends AppCompatActivity {

    private Button leftButton, rightButton;
    private TextView scoreText;
    private int score;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //basically our constructor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_number);

        //wire up widgets
        leftButton = (Button) findViewById(R.id.button_left);
        rightButton = (Button) findViewById(R.id.button_right);
        scoreText = (TextView) findViewById(R.id.text_score);

        //set up initial values
        scoreText.setText("Score: 0");
        score = 0;
        rollTwoNumbers();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //generates two new numbers
    private void rollTwoNumbers() {
        leftButton.setText("" + ((int) (Math.random() * 100) + 1));
        rightButton.setText("" + ((int) (Math.random() * 100) + 1));
        if (leftButton.getText().equals(rightButton.getText())) {
            rollTwoNumbers();
        }


    }

    private void updateScore() {
        scoreText.setText("Score: " + score);
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("score", score);
        editor.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //load prefs, set score, update score
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        score = prefs.getInt("score",0);
        updateScore();
    }

    /*
                When we click on a button, determine whether user chose the right one
                if they did, add a point
                if not, subrtact a point
                make two more numbers
         */
    public void onLeftClick(View view) {
        //read text on button
        //Toast.makeText(GuessNumberActivity.this, "Left", Toast.LENGTH_SHORT).show();
        int thisNumber = Integer.parseInt(leftButton.getText().toString());
        int otherNumber = Integer.parseInt(rightButton.getText().toString());
        if (thisNumber > otherNumber) {
            score++;
        } else {
            score--;
        }
        updateScore();
        rollTwoNumbers();
    }

    public void onRightClick(View view) {
        //Toast.makeText(GuessNumberActivity.this, "Right", Toast.LENGTH_SHORT).show();
        int thisNumber = Integer.parseInt(rightButton.getText().toString());
        int otherNumber = Integer.parseInt(leftButton.getText().toString());
        if (thisNumber > otherNumber) {
            score++;
        } else {
            score--;
        }
        updateScore();
        rollTwoNumbers();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "GuessNumber Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.csaper6.whichnumberisbigger/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "GuessNumber Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.csaper6.whichnumberisbigger/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
