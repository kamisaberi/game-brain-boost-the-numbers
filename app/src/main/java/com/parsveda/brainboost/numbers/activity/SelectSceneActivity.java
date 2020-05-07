package com.parsveda.brainboost.numbers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.parsveda.brainboost.numbers.R;
import com.parsveda.brainboost.numbers.adapter.GridViewAdapter;
import com.parsveda.brainboost.numbers.base.Globals;
import com.parsveda.brainboost.numbers.model.StageItem;

import java.util.ArrayList;

public class SelectSceneActivity extends AppCompatActivity {


    private GridView gridView;
    private GridViewAdapter gridAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_select_scene);

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                StageItem item = (StageItem) parent.getItemAtPosition(position);


                //Log.d(Globals.LOG_TAG, "CLCLCLCLCLCLCLCLCL");
                //Create intent
                Intent intent = new Intent(SelectSceneActivity.this, MainActivity.class);
                //intent.putExtra("title", item.getTitle());
                //intent.putExtra("image", item.getImage());

                //Start details activity
                startActivity(intent);
            }
        });
    }

    /**
     * Prepare some dummy data for gridview
     */
    private ArrayList<StageItem> getData() {
        final ArrayList<StageItem> stageItems = new ArrayList<>();
//        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
//        for (int i = 0; i < imgs.length(); i++) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
//            //stageItems.add(new StageItem(bitmap, "Image#" + i));
//            stageItems.add(new StageItem("Image#" + i));
//        }

        for (int i = 0; i < Globals.stages.size(); i++) {
            //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            //stageItems.add(new StageItem(bitmap, "Image#" + i));
            stageItems.add(Globals.stages.get(i));
        }


        return stageItems;
    }


}
