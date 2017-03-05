package com.teoneff.musicplayer2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;

/**
 * https://www.tutorialspoint.com/android/android_mediaplayer.htm
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton b1,b2,b3,b4;
    private ImageView iv;
    private MediaPlayer mediaPlayer;

    private long startTime = 0;
    private long finalTime = 0;

    private Handler myHandler = new Handler();;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView tx1,tx2,tx3;

    public static int oneTimeOnly = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (ImageButton) findViewById(R.id.button1);
        b2 = (ImageButton) findViewById(R.id.button2);
        b3 = (ImageButton) findViewById(R.id.button3);
        b4 = (ImageButton) findViewById(R.id.button4);
        iv = (ImageView)findViewById(R.id.imageView);

        tx1 = (TextView)findViewById(R.id.textView2);
        tx2 = (TextView)findViewById(R.id.textView3);
        tx3 = (TextView)findViewById(R.id.textView4);
        tx3.setText("meditacion_1.mp3");

        mediaPlayer = MediaPlayer.create(this, R.raw.meditacion_1);
        seekbar = (SeekBar)findViewById(R.id.seekBar);
        seekbar.setClickable(false);
        b2.setEnabled(false);

    }

    private void boton1() {
        int temp = (int)startTime;

        if((temp + forwardTime) <= finalTime){
            startTime = startTime + forwardTime;
            mediaPlayer.seekTo((int) startTime);
            Toast.makeText(getApplicationContext(),"You have Jumped forward 5 seconds",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Cannot jump forward 5 seconds",Toast.LENGTH_SHORT).show();
        }
    }

    private void boton2() {
        Toast.makeText(getApplicationContext(), "Pausing sound",Toast.LENGTH_SHORT).show();
        mediaPlayer.pause();
        b2.setEnabled(false);
        b3.setEnabled(true);
    }

    private void boton3() {
        Toast.makeText(getApplicationContext(), "Playing sound",Toast.LENGTH_SHORT).show();
        mediaPlayer.start();

        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();

        if (oneTimeOnly == 0) {
            seekbar.setMax((int) finalTime);
            oneTimeOnly = 1;
        }

        tx2.setText(Utilities.milliSecondsToTimer(finalTime));
        tx1.setText(Utilities.milliSecondsToTimer(startTime));

        seekbar.setProgress((int)startTime);
        myHandler.postDelayed(UpdateSongTime,100);
        b2.setEnabled(true);
        b3.setEnabled(false);
    }

    private void boton4() {
        int temp = (int)startTime;

        if((temp - backwardTime) > 0){
            startTime = startTime - backwardTime;
            mediaPlayer.seekTo((int) startTime);
            Toast.makeText(getApplicationContext(),"You have Jumped backward 5 seconds",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Cannot jump backward 5 seconds",Toast.LENGTH_SHORT).show();
        }
    }



    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();

            tx1.setText(Utilities.milliSecondsToTimer(startTime));
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                boton1();
                break;
            case R.id.button2:
                boton2();
                break;
            case R.id.button3:
                boton3();
                break;
            case R.id.button4:
                boton4();
                break;
        }
    }

}
