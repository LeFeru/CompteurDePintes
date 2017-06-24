package com.example.rachid.pintes.Persistors;

import android.content.SharedPreferences;

/**
 * Created by rachid on 18/06/17.
 */
public class ScoresPersistorImpl implements AndroidScoresPersistor {

    private SharedPreferences sharedPreferences;
    public static final String PREFS = "mesPreferences";
    private static final String STATE_HIGHSCORE = "highScore";
    private static final String STATE_LASTSCORE = "lastScore";

    public ScoresPersistorImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void saveHighScore(int highScore) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt(STATE_HIGHSCORE,highScore);
        editor.commit();
    }

    @Override
    public void saveLastScore(int lastScore) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt(STATE_LASTSCORE,lastScore);
        editor.commit();
    }

    @Override
    public int readHighScore() {
        return this.sharedPreferences.getInt(STATE_HIGHSCORE,0);
    }

    @Override
    public int readLastScore() {
        return this.sharedPreferences.getInt(STATE_LASTSCORE,0);
    }
}
