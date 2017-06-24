package com.example.rachid.pintes.Models;

import android.os.Bundle;
import android.widget.TextSwitcher;

import com.example.rachid.pintes.Listeners.OnPintesModelChangeListener;
import com.example.rachid.pintes.Persistors.AndroidScoresPersistor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rachid on 18/06/17.
 */
public class PintesModel {

    private static final String STATE_COMPTEUR = "score";
    private static final String STATE_HIGHSCORE = "highScore";
    private static final String STATE_LASTSCORE = "lastScore";
    private static final String STATE_NBPINTESMAX = "nbPintesMax";
    private static final String STATE_DEJAENPANIQUE = "dejaEnPanique";

    private int score;
    private int highScore;
    private int lastScore;
    private int nbPintesMax;
    private boolean dejaEnPanique;
    private boolean nouveauHighScore;
    private Set<Integer> values;

    private AndroidScoresPersistor androidScoresPersistor;

    private Set<OnPintesModelChangeListener> listeners;

    public PintesModel(AndroidScoresPersistor androidScoresPersistor) {
        this.androidScoresPersistor = androidScoresPersistor;
        this.score = 0;
        this.highScore = this.androidScoresPersistor.readHighScore();
        this.lastScore = this.androidScoresPersistor.readLastScore();
        this.nbPintesMax = 0;
        this.dejaEnPanique = false;
        this.nouveauHighScore = false;
        this.values = new HashSet<Integer>(Arrays.asList(new Integer[] {5,8,10,12,15,18,24,30,50}));
        this.listeners = new HashSet<OnPintesModelChangeListener>();
    }

    public void incrementerScore(){
        setScore(this.score+1);
        notifyPintesModelChangeListeners();
    }

    public boolean isNouveauHighScore() {
        return nouveauHighScore;
    }

    public void setNouveauHighScore(boolean nouveauHighScore) {
        if(isNouveauHighScore()) return;
        this.nouveauHighScore = nouveauHighScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        if(this.score >= score) return;
        this.score = score;
        setHighScore(this.score);
        notifyPintesModelChangeListeners();
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        if(this.highScore >= highScore && !isDejaEnPanique()) return;
        setNouveauHighScore(true);
        this.highScore = highScore;
        this.androidScoresPersistor.saveHighScore(highScore);
        setDejaEnPanique(true);
        notifyPintesModelChangeListeners();
    }

    public int getLastScore() {
        return lastScore;
    }

    public void setLastScore(int lastScore) {
        if(lastScore <= 0) return;
        this.lastScore = lastScore;
        this.androidScoresPersistor.saveLastScore(lastScore);
        notifyPintesModelChangeListeners();
    }

    public int getNbPintesMax() {
        return nbPintesMax;
    }

    public void setNbPintesMax(int nbPintesMax) {
        if(this.nbPintesMax > 0) return;
        this.nbPintesMax = nbPintesMax;
        notifyPintesModelChangeListeners();
    }

    public boolean isDejaEnPanique() {
        return dejaEnPanique;
    }

    public void setDejaEnPanique(boolean dejaEnPanique) {
        if(isDejaEnPanique()) return;
        this.dejaEnPanique = dejaEnPanique;
        notifyPintesModelChangeListeners();
    }

    public Set<Integer> getValues() {
        return Collections.unmodifiableSet(values);
    }

    public void setValues(Set<Integer> values) {
        this.values = values;
        notifyPintesModelChangeListeners();
    }

    public void chargerBundle(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            setScore(savedInstanceState.getInt(STATE_COMPTEUR,0));
            setHighScore(savedInstanceState.getInt(STATE_HIGHSCORE,0));
            setLastScore(savedInstanceState.getInt(STATE_LASTSCORE,0));
            setNbPintesMax(savedInstanceState.getInt(STATE_NBPINTESMAX,0));
            setDejaEnPanique(savedInstanceState.getBoolean(STATE_DEJAENPANIQUE,false));
            notifyPintesModelChangeListeners();
        }
    }

    public void ajouterOnPintesModelChangeListener(OnPintesModelChangeListener listener){
        this.listeners.add(listener);
    }

    private void notifyPintesModelChangeListeners(){
        for(OnPintesModelChangeListener listener: listeners){
            listener.onPintesModelChange();
        }
    }

    public void resetScore(){
        score = 0;
        nbPintesMax = 0;
        dejaEnPanique = false;
        nouveauHighScore = false;
        notifyPintesModelChangeListeners();
    }
    public void resetLastScore(){
        lastScore = 0;
        this.androidScoresPersistor.saveLastScore(lastScore);
        notifyPintesModelChangeListeners();
    }
    public void resetHighScore(){
        highScore = 0;
        nbPintesMax = 0;
        dejaEnPanique = false;
        nouveauHighScore = false;
        this.androidScoresPersistor.saveHighScore(highScore);
        notifyPintesModelChangeListeners();
    }
}
