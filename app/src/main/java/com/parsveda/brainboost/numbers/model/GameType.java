package com.parsveda.brainboost.numbers.model;

/**
 * Created by kami on 12/22/2016.
 */
public enum GameType {
    SUDDEN_DEATH(1), SURVIVAL(2);

    private int value;

    GameType(int value) {
        this.value = value;
    }
}
