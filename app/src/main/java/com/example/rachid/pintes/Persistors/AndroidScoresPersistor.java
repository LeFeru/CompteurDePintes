package com.example.rachid.pintes.Persistors;

/**
 * Created by rachid on 18/06/17.
 */
public interface AndroidScoresPersistor {

    public void saveHighScore(int highScore);

    public void saveLastScore(int lastScore);

    public int readHighScore();

    public int readLastScore();

}
