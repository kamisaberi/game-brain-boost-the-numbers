package com.parsveda.brainboost.numbers.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kami on 12/19/2016.
 */
public enum StageType {

    SEQUENCED_NUMBER(1),
    COLOR(2),
    MEMORY_COLOR(3);

    private int value;
    private static Map map = new HashMap<>();

    StageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static StageType fromInteger(int x) {
        switch (x) {
            case 1:
                return SEQUENCED_NUMBER;
            case 2:
                return COLOR;
            case 3:
                return MEMORY_COLOR;
        }
        return null;
    }


    static {
        for (StageType pageType : StageType.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static StageType valueOf(int pageType) {
        return (StageType) map.get(pageType);
    }


}
