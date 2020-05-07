package com.parsveda.brainboost.numbers.application;

import android.app.Application;
import android.graphics.Typeface;
import android.os.Environment;

import com.parsveda.brainboost.numbers.base.Globals;

/**
 * Created by kami on 12/20/2016.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Globals.context = getBaseContext();
        Globals.loadPresets();
        Globals.loadStages();
        Globals.loadLevels();

        Globals.ReckonerFace = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/Reckoner.ttf");
        Globals.CanSaveData = checkExternalStorage();


    }

    public boolean checkExternalStorage() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            return false;
        } else {
            return false;
        }
    }

}
