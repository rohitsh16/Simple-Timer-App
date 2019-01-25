package com.example.hp.clock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    TextView textView;
    private SoundPool soundPool;
    private int sound1,sound2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes=new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            soundPool=new SoundPool.Builder().setMaxStreams(6).setAudioAttributes(audioAttributes).build();
        }else{
            soundPool=new SoundPool(6,AudioManager.STREAM_MUSIC,0);
        }
        sound1=soundPool.load(this,R.raw.sound1,1);
        sound2=soundPool.load(this,R.raw.sound1,1);
        editText=(EditText)findViewById(R.id.editText);
        button=(Button)findViewById(R.id.button);
        textView=(TextView)findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Button button=(Button)v;
                button.setEnabled(false);
                String s=editText.getText().toString();
                if(!s.equalsIgnoreCase("")) {

                    final int sec = Integer.valueOf(s);
                   //soundPool.play(sound2,1,1,0,-1,1);

                    CountDownTimer countDownTimer = new CountDownTimer(sec * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            textView.setText("Seconds : " + (int) (millisUntilFinished / 1000));
                           // textView.playSoundEffect(SoundEffectConstants.CLICK);
                            
                        }
                           /* @Override
                            protected void onResume(){
                            MainActivity.super.onResume();
                            Button button=(Button)findViewById(R.id.button);
                            button.setEnabled(true);
                            }*/

                        @Override
                        public void onFinish() {
                            textView.setText("DONE !!");
                            //soundPool.stop(sound2);
                            soundPool.play(sound1,1,1,0,0,1);
                            button.setEnabled(true);
                        }
                    }.start();

                }
            }
        });
    }
    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(("Are You Sure Want to Quit?"));
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {soundPool.stop(sound1);//soundPool.stop(sound2);
                finish();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
}
