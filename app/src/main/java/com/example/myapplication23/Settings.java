package com.example.myapplication23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Settings extends AppCompatActivity {
    public RelativeLayout relativeLayout;
    public SharedPreferences pref;
    public RelativeLayout phon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        phon = findViewById(R.id.rlVar1);

        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        int bg = sharedPref.getInt("background", android.R.color.white);
        phon.setBackgroundResource(bg);


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

    }

    public void onClickbtVar1(View view) {
        relativeLayout.setBackgroundResource(R.color.op1);

        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPref.edit();
        editor2.putInt("background", R.drawable.krasivii);
        editor2.apply();
    }

    public void onClickbtVar2(View view) {
        relativeLayout.setBackgroundResource(R.color.op1);

        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPref.edit();
        editor2.putInt("background", R.drawable.grey1);
        editor2.apply();
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

