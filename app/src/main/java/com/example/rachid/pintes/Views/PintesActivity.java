package com.example.rachid.pintes.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.rachid.pintes.Builders.Builder;
import com.example.rachid.pintes.Listeners.OnPintesModelChangeListener;
import com.example.rachid.pintes.Models.PintesModel;
import com.example.rachid.pintes.R;

public class PintesActivity extends AppCompatActivity implements OnPintesModelChangeListener{

    private TextSwitcher mSwitcher;
    private PintesModel pintesModel;
    private boolean newHighScoreActivityLancee;
    private boolean paniqueActivityLancee;
    private static final String NEW_HIGH_SCORE_ACTIVITY_LANCEE_KEY = "NEW_HIGH_SCORE_ACTIVITY_LANCEE_KEY";
    private static final String PANIQUE_ACTIVITY_LANCEE_KEY = "PANIQUE_ACTIVITY_LANCEE_KEY";
    public static final String TEXT_COLOR_KEY = "TEXT_COLOR_KEY";
    public static final String BACKGROUND_COLOR_KEY = "BACKGROUND_COLOR_KEY";
    private int textColor = Color.parseColor("#56002c");
    private int backgroundColor = Color.WHITE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pintes);
        this.pintesModel = ((Builder)this.getApplication()).getPintesModel();
        this.pintesModel.chargerBundle(savedInstanceState);
        this.pintesModel.ajouterOnPintesModelChangeListener(this);
        this.newHighScoreActivityLancee = false;
        this.paniqueActivityLancee = false;
        final Button plusplus = (Button) findViewById(R.id.plusplus);
        plusplus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pintesModel.incrementerScore();
            }
        });

        mSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
        mSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                TextView myText = new TextView(PintesActivity.this);
                myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                myText.setTextSize(24);
                myText.setTextColor(Color.parseColor("#56002c"));
                return myText;
            }

        });

        Animation in = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);
        mSwitcher.setInAnimation(in);
        mSwitcher.setOutAnimation(out);
        if(paniqueActivityLancee) {
            this.pintesModel.setLastScore(this.pintesModel.getScore());
            this.pintesModel.resetScore();
        }
        adapteScore();
        adapteTexte();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(NEW_HIGH_SCORE_ACTIVITY_LANCEE_KEY, this.newHighScoreActivityLancee);
        outState.putBoolean(PANIQUE_ACTIVITY_LANCEE_KEY, this.paniqueActivityLancee);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        this.newHighScoreActivityLancee = savedInstanceState.getBoolean(NEW_HIGH_SCORE_ACTIVITY_LANCEE_KEY);
        this.paniqueActivityLancee = savedInstanceState.getBoolean(PANIQUE_ACTIVITY_LANCEE_KEY);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        if(paniqueActivityLancee) {
            this.pintesModel.setLastScore(this.pintesModel.getScore());
            this.pintesModel.resetScore();
        }
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        if(paniqueActivityLancee) {
            this.pintesModel.setLastScore(this.pintesModel.getScore());
            this.pintesModel.resetScore();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextuel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("Menu",item.getTitle().toString());
        if(item.getItemId() == R.id.quittez){
            this.finish();
        }
        else if(item.getItemId() == R.id.resetHighScore){
            newHighScoreActivityLancee = false;
            this.pintesModel.resetHighScore();
        }
        else if(item.getItemId() == R.id.resetLastScore){
            this.pintesModel.resetLastScore();
        }
        else if(item.getItemId() == R.id.resetScore){
            this.pintesModel.resetScore();
            Log.i("resetScore",this.pintesModel.getScore()+"");
        }
        else if(item.getItemId() == R.id.noirEtBlanc){
            textColor = Color.WHITE;
            backgroundColor = Color.BLACK;
            changerCouleurs();
        }
        else if(item.getItemId() == R.id.blancEtNoir){
            textColor = Color.BLACK;
            backgroundColor = Color.WHITE;
            changerCouleurs();
        }
        else if(item.getItemId() == R.id.roseEtBrun){
            textColor = Color.parseColor("#8B4513");
            backgroundColor = Color.parseColor("#FF69B4");
            changerCouleurs();
        }
        return super.onOptionsItemSelected(item);
    }


    private void changerCouleurs(){
        TextView textView = (TextView) mSwitcher.getCurrentView();
        textView.setTextColor(textColor);
        textView = (TextView) findViewById(R.id.val);
        textView.setTextColor(textColor);
        textView = (TextView) findViewById(R.id.ths);
        textView.setTextColor(textColor);
        textView = (TextView) findViewById(R.id.highScore);
        textView.setTextColor(textColor);
        textView = (TextView) findViewById(R.id.tls);
        textView.setTextColor(textColor);
        textView = (TextView) findViewById(R.id.lastScore);
        textView.setTextColor(textColor);
        View view = findViewById(R.id.activity_pintes);
        view.setBackgroundColor(backgroundColor);
    }

    private void adapteScore(){
        Log.d("Compteur:",""+pintesModel.getScore());
        TextView val = (TextView) findViewById(R.id.val);
        val.setText(""+pintesModel.getScore());
        TextView hs = (TextView) findViewById(R.id.highScore);
        hs.setText(String.valueOf(this.pintesModel.getHighScore()));
        TextView ls = (TextView) findViewById(R.id.lastScore);
        ls.setText(String.valueOf(this.pintesModel.getLastScore()));
    }
    private void adapteTexte(){

        if( this.pintesModel.getScore() >= 50 )
            mSwitcher.setText(getResources().getString(R.string.t50));
        else if( this.pintesModel.getScore() >= 30 )
            mSwitcher.setText(getResources().getString(R.string.t30));
        else if( this.pintesModel.getScore() >= 24 )
            mSwitcher.setText(getResources().getString(R.string.t24));
        else if( this.pintesModel.getScore() >= 18 )
            mSwitcher.setText(getResources().getString(R.string.t18));
        else if( this.pintesModel.getScore() >= 15 )
            mSwitcher.setText(getResources().getString(R.string.t15));
        else if( this.pintesModel.getScore() >= 12 )
            mSwitcher.setText(getResources().getString(R.string.t12));
        else if( this.pintesModel.getScore() >= 10 )
            mSwitcher.setText(getResources().getString(R.string.t10));
        else if( this.pintesModel.getScore() >= 8 )
            mSwitcher.setText(getResources().getString(R.string.t8));
        else if( this.pintesModel.getScore() >= 5)
            mSwitcher.setText(getResources().getString(R.string.t5));
        else
            mSwitcher.setText(getResources().getString(R.string.t1));
    }


    public void onPintesModelChange(){
        if(this.pintesModel.isNouveauHighScore() && !newHighScoreActivityLancee){
            this.newHighScoreActivityLancee = true;
            TextView hs = (TextView) findViewById(R.id.ths);
            hs.setText(getResources().getString(R.string.notify_high_score));
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent highScoreIntent = new Intent(PintesActivity.this, NewHighScoreActivity.class);
                        highScoreIntent.putExtra(TEXT_COLOR_KEY,textColor);
                        highScoreIntent.putExtra(BACKGROUND_COLOR_KEY,backgroundColor);
                        startActivity(highScoreIntent);
                        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                    }
                }, 1000);
        }
        if(!this.paniqueActivityLancee && pintesModel.isDejaEnPanique() && pintesModel.getScore() >= pintesModel.getNbPintesMax() && pintesModel.getNbPintesMax() > 0){
           this.paniqueActivityLancee = true;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent paniqueIntent = new Intent(PintesActivity.this, PaniqueActivity.class);
                    paniqueIntent.putExtra(TEXT_COLOR_KEY,textColor);
                    paniqueIntent.putExtra(BACKGROUND_COLOR_KEY,backgroundColor);
                    startActivity(paniqueIntent);
                    overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                }
            }, 1000);
        }
        if(pintesModel.getValues().contains(pintesModel.getScore())){
            Log.d("AdapteCheck","True");
            adapteTexte();
        }
        adapteScore();
    }
}
