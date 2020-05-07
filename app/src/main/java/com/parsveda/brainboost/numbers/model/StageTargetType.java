package com.parsveda.brainboost.numbers.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kami on 12/19/2016.
 */
public enum StageTargetType {

    BE_ALIVE(1),
    REACHING_SCORE(2);

    private int value;
    private static Map map = new HashMap<>();

    StageTargetType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static StageTargetType fromInteger(int x) {
        switch (x) {
            case 1:
                return BE_ALIVE;
            case 2:
                return REACHING_SCORE;
        }
        return null;
    }


    static {
        for (StageTargetType pageType : StageTargetType.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static StageTargetType valueOf(int pageType) {
        return (StageTargetType) map.get(pageType);
    }


}
