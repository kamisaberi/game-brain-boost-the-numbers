package com.parsveda.brainboost.numbers.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parsveda.brainboost.numbers.R;
import com.parsveda.brainboost.numbers.base.Globals;
import com.parsveda.brainboost.numbers.base.Preset;
import com.parsveda.brainboost.numbers.base.Utils;
import com.parsveda.brainboost.numbers.base.ViewModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    private static final boolean AUTO_HIDE = true;
    //private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    //private boolean mVisible;
//    private final Runnable mHideRunnable = new Runnable() {
//        @Override
//        public void run() {
//           // hide();
//        }
//    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

    RelativeLayout mainPanel;
    Random rand;
    public CountDownTimer timer;
    private boolean ret = false;
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                // delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        //mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mControlsView.setVisibility(View.GONE);
        //mVisible = false;
        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);


//MMMMMMMMMMMMMMMMMMMMEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE


        try {
            InputStream is = getAssets().open("Presets.xml");
            //Log.d(Globals.LOG_TAG, "csdasadasd");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();
            NodeList nList = doc.getElementsByTagName("Preset");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Preset preset = new Preset();
                    //Element element2 = (Element) node;
                    Log.d(Globals.LOG_TAG, "------------------------------------------");
                    preset.setName(((Element) node).getAttribute("name") + "");
                    preset.setId(Integer.parseInt(((Element) node).getAttribute("id") + ""));
                    Log.d(Globals.LOG_TAG, ((Element) node).getAttribute("id") + "");
                    Log.d(Globals.LOG_TAG, ((Element) node).getAttribute("name") + "");

                    NodeList nList2 = ((Element) node).getElementsByTagName("View");


                    for (int j = 0; j < nList2.getLength(); j++) {
                        Node node2 = nList2.item(j);
                        if (node2.getNodeType() == Node.ELEMENT_NODE) {

                            //Element element3 = (Element) node2;
                            ViewModel mdl = new ViewModel();
                            Log.d(Globals.LOG_TAG, ((Element) node2).getAttribute("attrib") + "");
                            Log.d(Globals.LOG_TAG, ((Element) node2).getAttribute("id") + "");
                            mdl.setAttribs(((Element) node2).getAttribute("attrib") + "");
                            mdl.setId(Integer.parseInt(((Element) node2).getAttribute("id") + ""));

                            preset.getModels().add(mdl);
                        }
                    }
                    Globals.presets.add(preset);
                }
            }
        } catch (Exception e) {
            Log.d(Globals.LOG_TAG, e.getMessage().toString());
        }


        timer = new CountDownTimer(3000, 100) {
            public void onTick(long millisUntilFinished) {
                //Globals.time = (float) millisUntilFinished / 1000;
                TextView txttime = (TextView) findViewById(R.id.txtTime);
                //txttime.setText(Globals.time + "");
            }

            public void onFinish() {
                //Globals.time = 0;
                TextView txttime = (TextView) findViewById(R.id.txtTime);
                txttime.setText(" LOSE ");


//                new AlertDialog.Builder(FullscreenActivity.this)
//                        .setTitle("Delete entry")
//                        .setMessage("Are you sure you want to delete this entry?")
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // continue with delete
//                            }
//                        })
//                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // do nothing
//                            }
//                        })
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();
            }
        };

        timer.start();

        rand = new Random();
        mainPanel = (RelativeLayout) findViewById(R.id.pnlMain);
        addButtons();

        final Button btnClose = (Button) findViewById(R.id.btnMenu);
        btnClose.setOnClickListener(new View.OnClickListener()

                                    {
                                        @Override
                                        public void onClick(View view) {
                                            for (Button btn : Globals.buttons) {
                                                AbsoluteLayout.LayoutParams prms = (AbsoluteLayout.LayoutParams) btn.getLayoutParams();
                                                Log.d(Globals.LOG_TAG, "x: " + prms.x + " Y: " + prms.y);
                                            }
                                        }
                                    }
        );


        final Button btnTest = (Button) findViewById(R.id.btnTest);
        final Button btnRetry = (Button) findViewById(R.id.btnBack);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(Globals.LOG_TAG, "WWWW : " + btnRetry.getLayoutParams().width);
                //RelativeLayout.LayoutParams.WRAP_CONTENT
                if (ret == false) {
                    btnTest.animate().translationX(500).setDuration(1000)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    //btnTest.setVisibility(View.GONE);
                                }
                            });

                } else {
                    btnTest.animate().translationX(0).setDuration(1000)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    //btnTest.setVisibility(View.GONE);
                                }
                            });

                }
                ret = !ret;
            }
        });


//        View v; // Creating an instance for View Object
//        Log.d(Globals.LOG_TAG , "AAA");
//        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        Log.d(Globals.LOG_TAG , "AAA");
//        v = inflater.inflate(R.layout.preset_1, null);
//        Log.d(Globals.LOG_TAG , "AAA");
//        Button btn1 = (Button) v.findViewById(R.id.button);
//        Log.d(Globals.LOG_TAG , "AAA");
//        Button btn1 =btn1.creat
//        mainPanel.addView(btn1);
//        Log.d(Globals.LOG_TAG , "AAA");


//        String[] ids = getResources().getStringArray(R.array.ids);
//        for (String a : ids) {
//            Log.d(Globals.LOG_TAG, "ID : " + a );
//        }


        // Set up the user interaction to manually show or hide the system UI.
//        mContentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggle();
//            }
//        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }


    public boolean checkVictory() {
        boolean vic = true;
        for (Button btn : Globals.buttons) {
            if (btn.isEnabled() == true) {
                vic = false;
                break;
            }
        }
        return vic;
    }

    public void clear() {
        for (Button btn : Globals.buttons) {
            mainPanel.removeView(btn);
        }
        Globals.buttons.clear();
        //Globals.currentValue = 0;

    }

    public void addButtons() {

        //int prevId = 0;
        int pr = rand.nextInt(Globals.presets.size());
        Globals.selectedPresetId = pr;

        Preset preset = Globals.presets.get(Globals.selectedPresetId);
        List<String> values = new ArrayList<>();


        for (int i = 1; i <= preset.getModels().size(); i++) {
            values.add(i + "");
        }

//        values.add("1");
//        values.add("2");
//        values.add("3");
//        values.add("4");
//        values.add("5");


        for (int i = 0; i < preset.getModels().size(); i++) {
            ViewModel model = preset.getModels().get(i);
            final Button button = new Button(this);
            button.setBackgroundResource(R.drawable.button_circle_blue);

            int v = rand.nextInt(values.size());
            //Log.d(Globals.LOG_TAG, "VALUE: " + v + " TEXT: " + values.get(v));
            String text = values.get(v);
            values.remove(v);
            button.setText(text);

            button.setId(Globals.IDS[i]);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (Globals.currentValue + 1 == Integer.parseInt(button.getText() + "")) {
//                        //Globals.currentValue++;
//                        button.setEnabled(false);
//                        if (checkVictory() == true) {
//                            clear();
//                            addButtons();
//                            Globals.score++;
//                            TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
//                            txtvalue.setText(Globals.score + "");
//                            timer.cancel();
//                            timer.start();
//                        }
//                    } else {
//                        clear();
//                        addButtons();
//                    }
                }
            });

            RelativeLayout.LayoutParams params = Utils.createLayout(getResources(), model.getAttribs());
            button.setTextSize(32);
            //button.setGravity(Gravity.CENTER);
            button.setLayoutParams(params);
            mainPanel.addView(button);
            mainPanel.refreshDrawableState();
            Globals.buttons.add(button);
        }


//        for (int i = 0; i < 5; i++) {
//            final Button button = new Button(this);
//            button.setBackgroundResource(R.drawable.button_circle_blue);
//            int v = rand.nextInt(values.size());
//            //Log.d(Globals.LOG_TAG, "VALUE: " + v + " TEXT: " + values.get(v));
//            String text = values.get(v);
//            values.remove(v);
//            button.setText(text);
//
//            int id;
////            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
////                id = Utils.generateViewId();
////            } else {
////                id = View.generateViewId();
////            }
//
//            //Log.d(Globals.LOG_TAG, "IDDDD : " + id);
//            //button.setId(10);
//            button.setId(Globals.IDS[i]);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (Globals.currentValue + 1 == Integer.parseInt(button.getText() + "")) {
//                        Globals.currentValue++;
//                        button.setEnabled(false);
//                        if (checkVictory() == true) {
//                            clear();
//                            addButtons();
//                            Globals.score++;
//                            TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
//                            txtvalue.setText(Globals.score + "");
//                            timer.cancel();
//                            timer.start();
//                        }
//                    } else {
//                        clear();
//                        addButtons();
//                    }
//                }
//            });
//
//            int px = Utils.convertDpToPixel(getResources(), rand.nextInt(200));
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Utils.convertDpToPixel(getResources(), 100), Utils.convertDpToPixel(getResources(), 100));
//
//            int dp = rand.nextInt(75) + 75;
//            px = Utils.convertDpToPixel(getResources(), dp);
//            params.width = px;
//            params.height = px;
//
//            if (i == 0) {
//                //params.addRule(RelativeLayout.END_OF, Globals.IDS[3]);
//                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//            } else {
//
//                params.addRule(RelativeLayout.END_OF, Globals.IDS[i - 1]);
//            }
//            //params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//            params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
//            params.setMargins(10, 10, 10, 10);
//            //button.setGravity(View.SCROLL_INDICATOR_BOTTOM);
//            //mainPanel.setGravity(View.SCROLL_INDICATOR_BOTTOM);
//            button.setTextSize(32);
//            mainPanel.addView(button, params);
//
//
//            Globals.buttons.add(button);
//            //prevId = id;
//        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        //delayedHide(100);
    }

//    private void toggle() {
//        if (mVisible) {
//            //hide();
//        } else {
//            //show();
//        }
//    }

//    private void hide() {
//        // Hide UI first
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
//        mControlsView.setVisibility(View.GONE);
//        mVisible = false;
//
//        // Schedule a runnable to remove the status and navigation bar after a delay
//        mHideHandler.removeCallbacks(mShowPart2Runnable);
//        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
//    }
//
//    @SuppressLint("InlinedApi")
//    private void show() {
//        // Show the system bar
//        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
//        mVisible = true;
//
//        // Schedule a runnable to display UI elements after a delay
//        mHideHandler.removeCallbacks(mHidePart2Runnable);
//        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
//    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
//    private void delayedHide(int delayMillis) {
//        mHideHandler.removeCallbacks(mHideRunnable);
//        mHideHandler.postDelayed(mHideRunnable, delayMillis);
//    }
}
