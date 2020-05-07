package com.parsveda.brainboost.numbers.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parsveda.brainboost.numbers.R;
import com.parsveda.brainboost.numbers.base.Globals;
import com.parsveda.brainboost.numbers.base.Preset;
import com.parsveda.brainboost.numbers.base.Utils;
import com.parsveda.brainboost.numbers.base.ViewModel;
import com.parsveda.brainboost.numbers.model.GameType;
import com.parsveda.brainboost.numbers.model.Level;
import com.parsveda.brainboost.numbers.model.StageTouchOrderType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    RelativeLayout mainPanel;
    Random rand;
    public CountDownTimer timer;
    private boolean ret = false;
    MediaPlayer mediaPlayer;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_main);

        //Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Reckoner.ttf");
        final TextView txttime = (TextView) findViewById(R.id.txtTime);
        txttime.setTypeface(Globals.ReckonerFace, Typeface.BOLD);
        txttime.setTextColor(getResources().getColor(R.color.mid_blue));

        TextView txtscore = (TextView) findViewById(R.id.txtScoreValue);
        txtscore.setTypeface(Globals.ReckonerFace, Typeface.BOLD);
        txtscore.setTextColor(getResources().getColor(R.color.mid_blue));


//        mediaPlayer= MediaPlayer.create(this, R.raw.loose_click);
//        mediaPlayer.setVolume(45,45);
//        try {
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        mediaPlayer.start();

        timer = new CountDownTimer(Globals.TimeForAnyStageParts, Globals.IntervalOfShowingTime) {
            public void onTick(long millisUntilFinished) {
                Globals.currentStage.setTime((float) millisUntilFinished / 1000);
                TextView txttime = (TextView) findViewById(R.id.txtTime);
                txttime.setText(Globals.currentStage.getTime() + "");
            }


            public void onFinish() {

                if (Globals.currentStage.getType() == GameType.SUDDEN_DEATH) {
                    clear();
                    txttime.setText("0.000");
                    timer.cancel();
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }


                Globals.currentStage.setTime(0);
                TextView txttime = (TextView) findViewById(R.id.txtTime);
                txttime.setText("0.000");
                clear();
                //addButtons();
                Globals.currentStage.addScore(-2);
                TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
                txtvalue.setText(Globals.currentStage.getScore() + "");
                timer.cancel();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addButtons();

                        timer.start();
                    }
                }, Globals.DelayBetweenStageParts);


                //timer.start();


            }
        };

        timer.start();

        rand = new Random();
        mainPanel = (RelativeLayout) findViewById(R.id.pnlMain);

        clear();
        Globals.currentStage.setScore(0);
        Globals.currentStage.setPartCount(0);
        addButtons();

//        final Button btnExit = (Button) findViewById(R.id.btnExit);
//        btnExit.setOnClickListener(new View.OnClickListener() {
//                                       @Override
//                                       public void onClick(View view) {
//                                           Intent intent = new Intent(MainActivity.this, SelectSceneActivity.class);
//                                           startActivity(intent);
//                                       }
//                                   }
//        );


//        final Button btnTest = (Button) findViewById(R.id.btnTest);
        final Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                timer.cancel();
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
                //Log.d(Globals.LOG_TAG, "WWWW : " + btnRetry.getLayoutParams().width);
                //RelativeLayout.LayoutParams.WRAP_CONTENT
//                if (ret == false) {
//                    btnTest.animate().translationX(500).setDuration(1000)
//                            .setListener(new AnimatorListenerAdapter() {
//                                @Override
//                                public void onAnimationEnd(Animator animation) {
//                                    super.onAnimationEnd(animation);
//                                    //btnTest.setVisibility(View.GONE);
//                                }
//                            });
//
//                } else {
//                    btnTest.animate().translationX(0).setDuration(1000)
//                            .setListener(new AnimatorListenerAdapter() {
//                                @Override
//                                public void onAnimationEnd(Animator animation) {
//                                    super.onAnimationEnd(animation);
//                                    //btnTest.setVisibility(View.GONE);
//                                }
//                            });
//
//                }
//                ret = !ret;
            }
        });


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
        mainPanel.removeAllViews();
//        for (Button btn : Globals.buttons) {
//            mainPanel.removeView(btn);
//        }
        Globals.buttons.clear();
        Globals.currentStage.setCurrentValue(0);

    }

    public void addButtons() {

        Globals.currentStage.addPartCount(1);

        Level level = null;
        for (Level lvl : Globals.levels) {
            if (lvl.getPartCount() > Globals.currentStage.getPartCount()) {
                level = lvl;
                break;
            }
        }

        List<Preset> presetsForRand = new ArrayList<>();
        if (level != null) {
            for (Preset prst : Globals.presets) {
                for (int shpcnt : level.getPresetShapesCount()) {
                    if (shpcnt == prst.getModels().size()) {
                        presetsForRand.add(prst);
                        break;
                    }
                }
            }
        } else {
            for (Preset prst : Globals.presets) {
                presetsForRand.add(prst);
            }
        }

        //int prevId = 0;
        int pr = rand.nextInt(presetsForRand.size());
        Globals.selectedPresetId = pr;

        Preset preset = presetsForRand.get(Globals.selectedPresetId);
        List<String> values = new ArrayList<>();


        for (int i = 1; i <= preset.getModels().size(); i++) {
            values.add(i + "");
        }


        if (Globals.currentStage.getTouchOrderType() == StageTouchOrderType.REVERSE) {
            Globals.currentStage.setCurrentValue(Integer.parseInt(values.get(values.size() - 1)));
        } else if (Globals.currentStage.getTouchOrderType() == StageTouchOrderType.COMPLEX) {
            boolean b = rand.nextBoolean();
            if (b == false) {
                Globals.currentStage.partTouchOrderType = StageTouchOrderType.NORMAL;
            } else {
                Globals.currentStage.partTouchOrderType = StageTouchOrderType.REVERSE;
                Globals.currentStage.setCurrentValue(Integer.parseInt(values.get(values.size() - 1)));
            }
        }


//        values.add("1");
//        values.add("2");
//        values.add("3");
//        values.add("4");
//        values.add("5");


        for (int i = 0; i < preset.getModels().size(); i++) {

            ViewModel model = preset.getModels().get(i);
            final Button button = new Button(this);
            int v = rand.nextInt(values.size());
            //Log.d(Globals.LOG_TAG, "VALUE: " + v + " TEXT: " + values.get(v));
            String text = values.get(v);
            values.remove(v);
            button.setText(text);

            button.setId(Globals.IDS[i]);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (Globals.currentStage.getTouchOrderType() == StageTouchOrderType.NORMAL) {
                        if (Globals.currentStage.getCurrentValue() + 1 == Integer.parseInt(button.getText() + "")) {
                            Globals.currentStage.addCurrentValue(1);
                            button.setEnabled(false);
                            button.animate().setDuration(100).alpha(0).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    button.setVisibility(View.INVISIBLE);
                                }
                            });
                            //button.setVisibility(View.INVISIBLE);
                            //button.setEnabled(false);
                            if (checkVictory() == true) {
                                clear();
//                            addButtons();
//                            Globals.score++;
//                            TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
//                            txtvalue.setText(Globals.score + "");

                                Globals.currentStage.addScore(Globals.EARNING_SCRORE_FOR_NORMAL_MODE);
                                TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
                                txtvalue.setText(Globals.currentStage.getScore() + "");
                                timer.cancel();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        addButtons();
                                        timer.start();
                                    }
                                }, Globals.DelayBetweenStageParts);

//                            timer.cancel();
//                            timer.start();

                            }
                        } else {


                            if (Globals.currentStage.getType() == GameType.SUDDEN_DEATH) {
                                clear();
                                timer.cancel();
                                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            }


                            Globals.currentStage.setTime(0);
                            Globals.currentStage.addScore(-2);
                            //TextView txttime = (TextView) findViewById(R.id.txtTime);
                            //txttime.setText(" LOSE ");
                            clear();
//                        addButtons();
//                        TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
//                        txtvalue.setText(Globals.score + "");
//                        timer.cancel();
//                        timer.start();

                            TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
                            txtvalue.setText(Globals.currentStage.getScore() + "");
                            timer.cancel();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    addButtons();

                                    timer.start();
                                }
                            }, Globals.DelayBetweenStageParts);


                        }
                    } else if (Globals.currentStage.getTouchOrderType() == StageTouchOrderType.REVERSE) {


                        if (Globals.currentStage.getCurrentValue() == Integer.parseInt(button.getText() + "")) {
                            Globals.currentStage.addCurrentValue(-1);
                            button.setEnabled(false);
                            button.animate().setDuration(100).alpha(0).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    button.setVisibility(View.INVISIBLE);
                                }
                            });
                            //button.setVisibility(View.INVISIBLE);
                            //button.setEnabled(false);
                            if (checkVictory() == true) {
                                clear();
//                            addButtons();
//                            Globals.score++;
//                            TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
//                            txtvalue.setText(Globals.score + "");

                                Globals.currentStage.addScore(Globals.EARNING_SCRORE_FOR_REVERSE_MODE);
                                TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
                                txtvalue.setText(Globals.currentStage.getScore() + "");
                                timer.cancel();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        addButtons();
                                        timer.start();
                                    }
                                }, Globals.DelayBetweenStageParts);

//                            timer.cancel();
//                            timer.start();

                            }
                        } else {


                            if (Globals.currentStage.getType() == GameType.SUDDEN_DEATH) {
                                clear();
                                timer.cancel();
                                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            }


                            Globals.currentStage.setTime(0);
                            Globals.currentStage.addScore(-2);
                            //TextView txttime = (TextView) findViewById(R.id.txtTime);
                            //txttime.setText(" LOSE ");
                            clear();
//                        addButtons();
//                        TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
//                        txtvalue.setText(Globals.score + "");
//                        timer.cancel();
//                        timer.start();

                            TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
                            txtvalue.setText(Globals.currentStage.getScore() + "");
                            timer.cancel();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    addButtons();

                                    timer.start();
                                }
                            }, Globals.DelayBetweenStageParts);


                        }


                    } else if (Globals.currentStage.getTouchOrderType() == StageTouchOrderType.COMPLEX) {


                        if (Globals.currentStage.partTouchOrderType == StageTouchOrderType.NORMAL) {

                            if (Globals.currentStage.getCurrentValue() + 1 == Integer.parseInt(button.getText() + "")) {
                                Globals.currentStage.addCurrentValue(1);
                                button.setEnabled(false);
                                button.animate().setDuration(100).alpha(0).setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        button.setVisibility(View.INVISIBLE);
                                    }
                                });
                                //button.setVisibility(View.INVISIBLE);
                                //button.setEnabled(false);
                                if (checkVictory() == true) {
                                    clear();
//                            addButtons();
//                            Globals.score++;
//                            TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
//                            txtvalue.setText(Globals.score + "");

                                    Globals.currentStage.addScore(Globals.EARNING_SCRORE_FOR_COMPLEX_MODE);
                                    TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
                                    txtvalue.setText(Globals.currentStage.getScore() + "");
                                    timer.cancel();

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            addButtons();
                                            timer.start();
                                        }
                                    }, Globals.DelayBetweenStageParts);

//                            timer.cancel();
//                            timer.start();

                                }
                            } else {


                                if (Globals.currentStage.getType() == GameType.SUDDEN_DEATH) {
                                    clear();
                                    timer.cancel();
                                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                                    startActivity(intent);
                                    finish();
                                    return;
                                }


                                Globals.currentStage.setTime(0);
                                Globals.currentStage.addScore(-2);
                                //TextView txttime = (TextView) findViewById(R.id.txtTime);
                                //txttime.setText(" LOSE ");
                                clear();
//                        addButtons();
//                        TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
//                        txtvalue.setText(Globals.score + "");
//                        timer.cancel();
//                        timer.start();

                                TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
                                txtvalue.setText(Globals.currentStage.getScore() + "");
                                timer.cancel();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        addButtons();

                                        timer.start();
                                    }
                                }, Globals.DelayBetweenStageParts);


                            }


                        } else if (Globals.currentStage.partTouchOrderType == StageTouchOrderType.REVERSE) {


                            if (Globals.currentStage.getCurrentValue() == Integer.parseInt(button.getText() + "")) {
                                Globals.currentStage.addCurrentValue(-1);
                                button.setEnabled(false);
                                button.animate().setDuration(100).alpha(0).setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        button.setVisibility(View.INVISIBLE);
                                    }
                                });
                                //button.setVisibility(View.INVISIBLE);
                                //button.setEnabled(false);
                                if (checkVictory() == true) {
                                    clear();
//                            addButtons();
//                            Globals.score++;
//                            TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
//                            txtvalue.setText(Globals.score + "");

                                    Globals.currentStage.addScore(Globals.EARNING_SCRORE_FOR_COMPLEX_MODE);
                                    TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
                                    txtvalue.setText(Globals.currentStage.getScore() + "");
                                    timer.cancel();

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            addButtons();
                                            timer.start();
                                        }
                                    }, Globals.DelayBetweenStageParts);

//                            timer.cancel();
//                            timer.start();

                                }
                            } else {


                                if (Globals.currentStage.getType() == GameType.SUDDEN_DEATH) {
                                    clear();
                                    timer.cancel();
                                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                                    startActivity(intent);
                                    finish();
                                    return;
                                }


                                Globals.currentStage.setTime(0);
                                Globals.currentStage.addScore(-2);
                                //TextView txttime = (TextView) findViewById(R.id.txtTime);
                                //txttime.setText(" LOSE ");
                                clear();
//                        addButtons();
//                        TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
//                        txtvalue.setText(Globals.score + "");
//                        timer.cancel();
//                        timer.start();

                                TextView txtvalue = (TextView) findViewById(R.id.txtScoreValue);
                                txtvalue.setText(Globals.currentStage.getScore() + "");
                                timer.cancel();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        addButtons();

                                        timer.start();
                                    }
                                }, Globals.DelayBetweenStageParts);


                            }


                        }


                    }


                }
            });

            RelativeLayout.LayoutParams params = Utils.createLayout(getResources(), model.getAttribs());

            Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Reckoner.ttf");
            button.setTypeface(face, Typeface.BOLD);
            button.setTextSize(48);


            //button.setBackgroundResource(R.drawable.button_circle_blue);
            //button.setTextColor(Color.WHITE);

            button.setBackgroundResource(R.drawable.button_circle_white);
            if (Globals.currentStage.getTouchOrderType() == StageTouchOrderType.NORMAL) {
                //button.setTextColor(getResources().getColor(R.color.mid_blue));
                button.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.mid_blue));
            } else if (Globals.currentStage.getTouchOrderType() == StageTouchOrderType.REVERSE) {
                //button.setTextColor(getResources().getColor(R.color.dark_teal));
                button.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.dark_teal));
            } else if (Globals.currentStage.getTouchOrderType() == StageTouchOrderType.COMPLEX) {
                if (Globals.currentStage.partTouchOrderType == StageTouchOrderType.NORMAL) {
                    button.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.mid_blue));
                } else if (Globals.currentStage.partTouchOrderType == StageTouchOrderType.REVERSE) {
                    button.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.dark_teal));
                }
            }
            //button.setGravity(Gravity.CENTER);
            button.setLayoutParams(params);
            mainPanel.addView(button);
            mainPanel.refreshDrawableState();
            Globals.buttons.add(button);
        }
    }

}
