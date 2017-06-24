package com.example.rachid.pintes.Views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rachid.pintes.Builders.Builder;
import com.example.rachid.pintes.Models.PintesModel;
import com.example.rachid.pintes.R;

public class NewHighScoreActivity extends AppCompatActivity {

    private PintesModel pintesModel;
    public static final String TEXT_COLOR_KEY = "TEXT_COLOR_KEY";
    public static final String BACKGROUND_COLOR_KEY = "BACKGROUND_COLOR_KEY";
    private int textColor = Color.parseColor("#56002c");
    private int backgroundColor = Color.WHITE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_high_score);
        this.pintesModel = ((Builder)getApplication()).getPintesModel();
        textColor = getIntent().getIntExtra(TEXT_COLOR_KEY,Color.parseColor("#56002c"));
        backgroundColor = getIntent().getIntExtra(BACKGROUND_COLOR_KEY, Color.WHITE);
        changerCouleurs();
        final Button promis = (Button) findViewById(R.id.promis);
        promis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        EditText promis = (EditText) findViewById(R.id.nbpintes);
                        int nbPintes = Integer.parseInt(promis.getText().toString());
                        pintesModel.setNbPintesMax(pintesModel.getScore()+nbPintes);
                        finish();
                        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                    }
                }, 1000);
            }
        });
    }
    private void changerCouleurs(){
        TextView textView = (TextView) findViewById(R.id.ths);
        textView.setTextColor(textColor);
        textView = (TextView) findViewById(R.id.nbpintes);
        textView.setTextColor(textColor);
        View view = findViewById(R.id.activity_new_high_score);
        view.setBackgroundColor(backgroundColor);
    }

}
