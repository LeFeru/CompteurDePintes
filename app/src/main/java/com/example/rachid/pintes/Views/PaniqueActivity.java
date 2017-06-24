package com.example.rachid.pintes.Views;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rachid.pintes.Builders.Builder;
import com.example.rachid.pintes.Models.PintesModel;
import com.example.rachid.pintes.R;

public class PaniqueActivity extends AppCompatActivity {

    private final static String PHONE_NUMBER = "01010";
    public static final String TEXT_COLOR_KEY = "TEXT_COLOR_KEY";
    public static final String BACKGROUND_COLOR_KEY = "BACKGROUND_COLOR_KEY";
    private int textColor = Color.parseColor("#56002c");
    private int backgroundColor = Color.WHITE;
    private PintesModel pintesModel;
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;

    private ProgressBar progressBar;
    Integer count =1;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panique);
        Log.i("PaniqueActivity","OK");
        this.pintesModel = ((Builder)getApplication()).getPintesModel();
        textColor = getIntent().getIntExtra(TEXT_COLOR_KEY, Color.parseColor("#56002c"));
        backgroundColor = getIntent().getIntExtra(BACKGROUND_COLOR_KEY, Color.WHITE);
        changerCouleurs();
        final Button call = (Button) findViewById(R.id.Appeler_100);
        call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + PHONE_NUMBER));
                        try{
                            startActivity(callIntent);
                        }
                        catch(SecurityException securityException){
                            Log.i("SecurityException","Doesn't have permission");
                        }
                        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                    }
                }, 1000);
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setMax(this.pintesModel.getScore());
        count = progressBar.getMax();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(progressBar.getMax());
        new MyTask().execute();

        /*
        mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
        mProgressBar.setProgress(i);
        mProgressBar.setMax(this.pintesModel.getScore());
        mProgressBar.setRotation(180);
        mCountDownTimer=new CountDownTimer(this.pintesModel.getScore()*1000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                i++;
                mProgressBar.setProgress(i);

            }

            @Override
            public void onFinish() {
                i++;
                mProgressBar.setProgress(i);
                //a vérifier
                onTick(1000);
                PaniqueActivity.this.finish();
            }
        };
        mCountDownTimer.start();*/
    }

    @Override
    public void onBackPressed() {
    }

    private void changerCouleurs(){
        TextView textView = (TextView) findViewById(R.id.msg_stop);
        textView.setTextColor(textColor);
        View view = findViewById(R.id.activity_panique);
        view.setBackgroundColor(backgroundColor);
    }

    class MyTask extends AsyncTask<Void, Integer, String> {
        @Override
        protected String doInBackground(Void... params) {
            for (; count > 0; count--) {
                try {
                    Thread.sleep(1000);
                    publishProgress(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Task Completed.";
        }
        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            Log.i("onPostExecute",result);
            PaniqueActivity.this.finish();
        }
        @Override
        protected void onPreExecute() {
            Log.i("onPreExcute","Task Starting...");
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i("onProgressUpdate","Running..."+ values[0]);
            progressBar.setProgress(values[0]);
        }
    }
}
