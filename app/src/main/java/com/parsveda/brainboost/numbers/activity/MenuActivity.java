package com.parsveda.brainboost.numbers.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parsveda.brainboost.numbers.R;
import com.parsveda.brainboost.numbers.base.Globals;
import com.parsveda.brainboost.numbers.model.GameType;
import com.parsveda.brainboost.numbers.model.StageTouchOrderType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_menu);

        TextView txtScore = (TextView) findViewById(R.id.txtScore);
        txtScore.setTypeface(Globals.ReckonerFace, Typeface.BOLD);


        Button btnPlayNormal = (Button) findViewById(R.id.btnPlayNormal);
        btnPlayNormal.setTypeface(Globals.ReckonerFace, Typeface.BOLD);


        btnPlayNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Globals.currentStage.setType(GameType.SUDDEN_DEATH);
                Globals.currentStage.setTouchOrderType(StageTouchOrderType.NORMAL);
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Button btnPlayReverse = (Button) findViewById(R.id.btnPlayReverse);
        btnPlayReverse.setTypeface(Globals.ReckonerFace, Typeface.BOLD);

        btnPlayReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Globals.currentStage.setType(GameType.SUDDEN_DEATH);
                Globals.currentStage.setTouchOrderType(StageTouchOrderType.REVERSE);
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Button btnPlayComplex = (Button) findViewById(R.id.btnPlayComplex);
        btnPlayComplex.setTypeface(Globals.ReckonerFace, Typeface.BOLD);

        btnPlayComplex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(MenuActivity.this, "NOT TADAY", Toast.LENGTH_LONG).show();
                Globals.currentStage.setType(GameType.SUDDEN_DEATH);
                Globals.currentStage.setTouchOrderType(StageTouchOrderType.COMPLEX);
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //Globals.context = getBaseContext();

        File extDir = getExternalFilesDir(null);
        //String path = extDir.getAbsolutePath();
        Globals.fileSaveData = new File(extDir, Globals.SAVE_FILE_NAME);


        try {

            if (Globals.fileSaveData.exists() == false) {
                createFile();
            }

            getBestScore();
            txtScore.setText(Globals.currentStage.getNormalModeBestScore() + Globals.currentStage.getReverseModeBestScore() + Globals.currentStage.getComplexModeBestScore() + "");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        try {
//            createFile();
//            readFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public boolean checkExternalStorage() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {

//            TextView txt1 = (TextView) findViewById(R.id.textView1);
//            txt1.setText("External Storage Read Only");

        } else {
//            TextView txt1 = (TextView) findViewById(R.id.textView1);
//            txt1.setText("External Storage Unavailable");

        }
        return false;
    }


    public void createFile() throws IOException, JSONException {

        if (!checkExternalStorage()) {
            return;
        }


        JSONArray data = new JSONArray();
        JSONObject tour;


        tour = new JSONObject();

        tour.put("best_score_normal", 0);
        tour.put("best_score_reverse", 0);
        tour.put("best_score_complex", 0);
//        tour.put("price", 900);
        data.put(tour);

//        tour = new JSONObject();
//        tour.put("tour", "Pars Gulf");
//        tour.put("price", 1200);
//        data.put(tour);
//
//        tour = new JSONObject();
//        tour.put("tour", "Omman See");
//        tour.put("price", 600);
//        data.put(tour);


        String text = data.toString();

        FileOutputStream fos = new FileOutputStream(Globals.fileSaveData);
        fos.write(text.getBytes());
        fos.close();


//        TextView txt1 = (TextView) findViewById(R.id.textView1);
//        txt1.setText("File written To Disk:\n" + data.toString());


    }


    public void getBestScore() throws IOException, JSONException {


        FileInputStream fis = new FileInputStream(Globals.fileSaveData);
        BufferedInputStream bis = new BufferedInputStream(fis);
        StringBuffer b = new StringBuffer();

        while (bis.available() != 0) {
            char c = (char) bis.read();
            b.append(c);

        }

        Log.d(Globals.LOG_TAG, b.toString());

        JSONArray data = new JSONArray(b.toString());

        StringBuffer toursBuffer = new StringBuffer();
        for (int i = 0; i < data.length(); i++) {
            //Log.d(Globals.LOG_TAG, "HAHA ");
            //String s = data.getJSONObject(i).getString("best_score_normal");
            //Log.d(Globals.LOG_TAG, s);
            Globals.currentStage.setNormalModeBestScore(data.getJSONObject(i).getInt("best_score_normal"));
            Globals.currentStage.setReverseModeBestScore(data.getJSONObject(i).getInt("best_score_reverse"));
            Globals.currentStage.setComplexModeBestScore(data.getJSONObject(i).getInt("best_score_complex"));
            Log.d(Globals.LOG_TAG, "NORMAL : " + Globals.currentStage.getNormalModeBestScore() + "  REVERSE : " + Globals.currentStage.getReverseModeBestScore() + "  REVERSE : " + Globals.currentStage.getComplexModeBestScore());
        }

        bis.close();


        //return bestScoreNormal + bestScoreReverse;

    }

}
