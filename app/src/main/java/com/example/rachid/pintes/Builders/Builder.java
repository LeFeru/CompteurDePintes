package com.example.rachid.pintes.Builders;

import android.app.Application;

import com.example.rachid.pintes.Models.PintesModel;
import com.example.rachid.pintes.Persistors.AndroidScoresPersistor;
import com.example.rachid.pintes.Persistors.ScoresPersistorImpl;

/**
 * Created by rachid on 18/06/17.
 */
public class Builder extends Application {

    private AndroidScoresPersistor androidScoresPersistor;
    private PintesModel pintesModel;
    @Override
    public void onCreate() {
        super.onCreate();
        this.androidScoresPersistor = new ScoresPersistorImpl(this.getSharedPreferences(ScoresPersistorImpl.PREFS,MODE_PRIVATE));
        this.pintesModel = new PintesModel(this.androidScoresPersistor);
    }

    public AndroidScoresPersistor getAndroidScoresPersistor() {
        return androidScoresPersistor;
    }

    public PintesModel getPintesModel() {
        return pintesModel;
    }
}
