package com.example.myapplication23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private long backPressedTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button buttonset = (Button) findViewById(R.id.ButtonSetings);
        buttonset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Intent intent = new Intent(MainActivity.this, com.example.myapplication23.Settings.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception e){}
            }
        });

        Button buttongame = (Button) findViewById(R.id.ButtonStart);
        buttongame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Intent intent = new Intent(MainActivity.this, Tetris.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception e){}
            }
        });

        Button buttonexit = (Button) findViewById(R.id.Buttonexit);
        buttonexit.setOnClickListener(this.ExitButton);


    }

    View.OnClickListener ExitButton = new View.OnClickListener(){
        @Override
        public void onClick(View view){  System.exit(0);
        }
    };

    @Override
    public void onBackPressed(){


        if (backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(getBaseContext(),"Нажмите ещё раз, чтобы выйти", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}