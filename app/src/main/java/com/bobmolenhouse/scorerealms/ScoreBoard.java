package com.bobmolenhouse.scorerealms;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

public class ScoreBoard extends AppCompatActivity {
    private TextView player1ScoreView;
    private TextView player2ScoreView;
    private int player1Score;
    private int player2Score;

    private TextView player1ScoreChange;
    private TextView player2ScoreChange;

    private Button player1Plus;
    private Button player1Minus;
    private Button player2Plus;
    private Button player2Minus;

    private CountDownTimer timer;
    private int scoreChange1 = 0;
    private int scoreChange2 = 0;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        //initialize the banner ads
        MobileAds.initialize(this, "ca-app-pub-6935583766445792~6470879865");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        //initialize values and what not
        player1ScoreView = (TextView) findViewById(R.id.player1Score);
        player2ScoreView = (TextView) findViewById(R.id.player2Score);
        player1ScoreChange = (TextView) findViewById(R.id.player1_score_change);
        player2ScoreChange = (TextView) findViewById(R.id.player2_score_change);
        player1Score = 50;
        player2Score = 50;
        player1Plus = (Button) findViewById(R.id.player1Plus);
        player1Plus.setOnClickListener(clickListener);
        player1Minus = (Button) findViewById(R.id.player1Minus);
        player1Minus.setOnClickListener(clickListener);
        player2Plus = (Button) findViewById(R.id.player2Plus);
        player2Plus.setOnClickListener(clickListener);
        player2Minus = (Button) findViewById(R.id.player2Minus);
        player2Minus.setOnClickListener(clickListener);

        timer = new CountDownTimer(1500, 100) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                player2ScoreChange.setText("");
                player1ScoreChange.setText("");
                scoreChange1 = 0;
                scoreChange2 = 0;
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.new_game:
                player1Score = 50;
                player2Score = 50;
                player1ScoreView.setText("" + player1Score);
                player2ScoreView.setText("" + player2Score);
                return true;

            case R.id.who_goes_first:
                Random r = new Random();
                timer.start();
                if(r.nextInt(10) > 4){
                    player1ScoreChange.setText("I go first!");
                }else{
                    player2ScoreChange.setText("I go first!");
                }
                return true;

            case R.id.master_control:
                LinearLayout l = (LinearLayout) findViewById(R.id.player1);
                l.setRotation(0);
                return true;

            case R.id.individual_control:
                LinearLayout i = (LinearLayout) findViewById(R.id.player1);
                i.setRotation(180);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            timer.cancel();
            switch(v.getId()){
                case R.id.player1Plus:
                    player1Score += 1;
                    scoreChange1 += 1;
                    if(scoreChange1 >= 0) {
                        player1ScoreChange.setText("+ " + scoreChange1);
                    }else{
                        player1ScoreChange.setText("" + scoreChange1);
                    }
                    timer.start();
                    player1ScoreView.setText("" + player1Score);
                    break;

                case R.id.player1Minus:
                    if(player1Score > 0) {
                        player1Score -= 1;
                        scoreChange1 -= 1;
                    }else{}

                    if(scoreChange1 >= 0) {
                        player1ScoreChange.setText("+ " + scoreChange1);
                    }else if(player1Score == 0){
                        player1ScoreChange.setText("You Dead");
                    }else{
                        player1ScoreChange.setText("" + scoreChange1);
                    }
                    timer.start();
                    player1ScoreView.setText("" + player1Score);
                    break;

                case R.id.player2Plus:
                    player2Score += 1;
                    scoreChange2 += 1;
                    if(scoreChange2 >= 0) {
                        player2ScoreChange.setText("+ " + scoreChange2);

                    }else{
                        player2ScoreChange.setText("" + scoreChange2);
                    }
                    timer.start();
                    player2ScoreView.setText("" + player2Score);
                    break;

                case R.id.player2Minus:
                    if(player2Score > 0) {
                        player2Score -= 1;
                        scoreChange2 -= 1;
                    }else{}

                    if(scoreChange2 >= 0) {
                        player2ScoreChange.setText("+ " + scoreChange2);
                    }else if(player2Score == 0){
                        player2ScoreChange.setText("You Dead");
                    }else{
                        player2ScoreChange.setText("" + scoreChange2);
                    }
                    timer.start();
                    player2ScoreView.setText("" + player2Score);
                    break;
            }
        }
    };



}
