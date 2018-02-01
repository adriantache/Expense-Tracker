package com.adriantache.expense_tracker;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.callback.OnPieSelectListener;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

public class MainActivity extends AppCompatActivity {

    AnimatedPieView mAnimatedPieView;
    AnimatedPieView gAnimatedPieView;
    FloatingActionButton floatingActionButton;

    //declare all variables
    int savingsTotal;
    int needsTotal;
    int wantsTotal;
    int savingsGoal;
    //todo default savingsGoalDefined to false after testing:
    boolean savingsGoalDefined = true;
    boolean fabClicked = false;

    //todo remove TAG after testing
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //todo get values from CSV
        readCSV();

        //launch the pie charts
        setPie();
        if (savingsGoalDefined) setGoalPie();

        //onClickListener for the FAB
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*//animate button icon
                Drawable drawable;
                if (!fabClicked) {
                    drawable = getDrawable(R.drawable.plustocheck);
                    floatingActionButton.setImageDrawable(drawable);
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7CB342")));
                    fabClicked = true;

                } else {
                    drawable = getDrawable(R.drawable.checktoplus);
                    floatingActionButton.setImageDrawable(drawable);
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EC407A")));
                    fabClicked = false;
                }
                if (drawable instanceof Animatable) ((Animatable) drawable).start();*/

                Intent intent = new Intent("android.intent.action.AddAmount");
                startActivityForResult(intent,1);



                //todo add new intent here to move to next activity
                //todo keep FAB settings in new activity
            }
        });

    }

    //handle returning to the main activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_CANCELED) {}
        else if (resultCode == 1) {
        Drawable drawable;
        //drawable = floatingActionButton.getDrawable();
        drawable = getDrawable(R.drawable.checktoplus);
        floatingActionButton.setImageDrawable(drawable);
        if (drawable instanceof Animatable) ((Animatable) drawable).start();}

    }

    //config and start the pie chart
    private void setPie() {
        mAnimatedPieView = findViewById(R.id.animatedPieView);

        //configure the pie display and elements
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.setStartAngle(-180);
        config.setDuration(1200);
        config.setDrawText(false);
        config.setStrokeWidth(100);
        config.setPieRadiusScale(0.8f);
        config.setSplitAngle(3);

        /* Text Options [disabled]
        config.setTextMarginLine(80);
        config.setTextSize(100);
        */

        //todo remove test values
        savingsTotal = 20;
        needsTotal = 50;
        wantsTotal = 30;
        //todo remove test values

        //calculate the pie slices
        int totalSum = savingsTotal + needsTotal + wantsTotal;
        float savings = (float) savingsTotal / totalSum * 100;
        float needs = (float) needsTotal / totalSum * 100;
        float wants = (float) wantsTotal / totalSum * 100;

        config.addData(new SimplePieInfo(savings, 0xFF1E88E5, "Savings"), false)
                .addData(new SimplePieInfo(needs, 0xFF43A047, "Needs"), false)
                .addData(new SimplePieInfo(wants, 0xFFFFB300, "Wants"), false);

        //add on touch listener
        config.setOnPieSelectListener(new OnPieSelectListener<IPieInfo>() {
            @Override
            public void onSelectPie(@NonNull IPieInfo pieInfo, boolean isScaleUp) {
                if (isScaleUp) {
                    //todo call method that drills down to selected pie segment
                    //todo remove Toast
                    if (pieInfo.getDesc() == "Savings") {
                        Toast.makeText(MainActivity.this, "TEST", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, pieInfo.getDesc(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        //start the pie chart
        mAnimatedPieView.applyConfig(config);
        mAnimatedPieView.start();
    }

    //config and start the goal pie chart
    private void setGoalPie() {
        gAnimatedPieView = findViewById(R.id.goalPieView);

        //configure the pie display and elements
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.setStartAngle(-90);
        config.setDuration(1200);
        config.setDrawText(false);
        config.setStrokeWidth(20);
        config.setPieRadiusScale(.8f);

        /* Text Options [disabled]
        config.setTextMarginLine(80);
        config.setTextSize(100);
        */

        //todo remove test values
        savingsTotal = 20;
        savingsGoal = 25;
        //todo remove test values

        //calculate the pie slices
        int totalSum = savingsTotal + needsTotal + wantsTotal;
        final float savings = (float) savingsTotal / savingsGoal;

        config.addData(new SimplePieInfo(savings, 0xFFffffff, "Achieved"), false)
                .addData(new SimplePieInfo(1 - savings, 0xff1e88e5, "Remaining"), false);

        //add on touch listener
        config.setOnPieSelectListener(new OnPieSelectListener<IPieInfo>() {
            @Override
            public void onSelectPie(@NonNull IPieInfo pieInfo, boolean isScaleUp) {
                if (isScaleUp) {
                    //todo call method that opens savings goal
                    //todo remove Toast
                    Toast.makeText(MainActivity.this, pieInfo.getDesc() + ": " + (int) savings + "%", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //start the pie chart
        gAnimatedPieView.applyConfig(config);
        gAnimatedPieView.start();
    }

    //todo function that reads CSV file with entries
    public void readCSV() {
        //read that damned csv!
    }

    //todo method that calculates evolution month over month or month over average and updates TextView

    //todo method or activity to set and view savings goal

    //todo method that changes screen over to another activity
    public void savingsClick(View view) {
        //todo call method that drills down to selected pie segment
        //todo remove Toast
        Toast.makeText(MainActivity.this, "go to savings", Toast.LENGTH_SHORT).show();
    }

    public void needsClick(View view) {
        //todo call method that drills down to selected pie segment
        //todo remove Toast
        Toast.makeText(MainActivity.this, "go to needs", Toast.LENGTH_SHORT).show();
    }

    public void wantsClick(View view) {
        //todo call method that drills down to selected pie segment
        //todo remove Toast
        Toast.makeText(MainActivity.this, "go to wants", Toast.LENGTH_SHORT).show();
    }

}
