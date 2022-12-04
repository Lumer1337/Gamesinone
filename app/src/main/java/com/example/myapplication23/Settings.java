package com.example.myapplication23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Settings extends AppCompatActivity {
    public RelativeLayout relativeLayout;

    public int getRelativeLayout() {
        return relativeLayout.getSolidColor();
    }

    public void setRelativeLayout(RelativeLayout relativeLayout) {
        this.relativeLayout = relativeLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button buttonset = (Button) findViewById(R.id.returnbutton);
        buttonset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Settings.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e) {}
            }
        });

        Button button1, button2;

        button1 = findViewById(R.id.btVar1);
        button2 = findViewById(R.id.btVar2);
        relativeLayout = findViewById(R.id.rlVar1);
        // onClick function for button 1
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set the color to relative layout
                relativeLayout.setBackgroundResource(R.color.cool);
            }
        });
        // onClick function for button 2
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set the color to relative layout
                relativeLayout.setBackgroundResource(R.color.warm);
            }
        });
    }

    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(Settings.this, MainActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){}
    }

}

