package com.adriantache.expense_tracker;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddAmount extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    boolean fabClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_amount);


        floatingActionButton = findViewById(R.id.floatingActionButton);
        Drawable drawable;
        drawable = getDrawable(R.drawable.plustocheck);
        if (drawable instanceof Animatable) ((Animatable) drawable).start();




        //onClickListener for the FAB
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //animate button icon
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
                    finish();
                }
                if (drawable instanceof Animatable) ((Animatable) drawable).start();

                //todo add new intent here to move to next activity
                //todo keep FAB settings in new activity
            }
        });
    }
}
