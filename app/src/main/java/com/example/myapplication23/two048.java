package com.example.myapplication23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class two048 extends AppCompatActivity {
    private float x1,x2,y1,y2;
    static final int MIN_DISTANCE = 350;
    private int[][] grid = new int[4][4];
    public int score =0;
    public int moves=0;
    int randomX;
    int randomY;
    public View phon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two048);

        phon = findViewById(R.id.main);
        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        int bg = sharedPref.getInt("background", android.R.color.white); // the second parameter will be fallback if the preference is not found
        phon.setBackgroundResource(bg);

        initialize();
        randomSpawn();
        Button buttonset = (Button) findViewById(R.id.ver_button);
        buttonset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(two048.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e) {}
            }
        });
    }


    /*
     * Detect Swipes
     */

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                float deltaY = y2 - y1;
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE && deltaX > 0){
                    // Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show();
                    onSwipeRight();
                    update();
                    randomSpawn();
                    moves++;

                }
                else if(Math.abs(deltaX) > MIN_DISTANCE && deltaX < 0){
                    //Toast.makeText(this, "right2left swipe", Toast.LENGTH_SHORT).show();
                    onSwipeLeft();
                    update();
                    randomSpawn();
                    moves++;
                }

                if (Math.abs(deltaY) > MIN_DISTANCE && deltaY > 0){
                    // Toast.makeText(this, "up2down swipe", Toast.LENGTH_SHORT).show();
                    onSwipeDown();
                    update();
                    randomSpawn();
                    moves++;
                }
                else if(Math.abs(deltaY) > MIN_DISTANCE && deltaY < 0){
                    //Toast.makeText(this, "down2up swipe", Toast.LENGTH_SHORT).show();
                    onSwipeUp();
                    update();
                    randomSpawn();
                    moves++;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /*
     --- Game Action Functions ---
     */

    public void onSwipeUp() {
        for (int x = 0; x < grid.length; x++) {
            for (int i = 0; i < grid[grid.length - 1].length; i++) {
                for (int k = grid.length - 1; k > 0; k--) {
                    try{
                        if (grid[k][i] == grid[k - 1][i]) {
                            grid[k - 1][i] = grid[k][i] + grid[k - 1][i];
                            grid[k][i] = 0;
                            score += grid[k-1][i];
                        } else if (grid[k - 1][i] == 0) {
                            grid[k - 1][i] = grid[k][i];
                            grid[k][i] = 0;
                        }
                    } catch (Exception e){

                    }
                }
            }
        }
    }

    public void onSwipeDown() {
        for (int x = 0; x < grid.length; x++) {
            for (int i = 0; i < grid[grid.length - 1].length; i++) {
                for (int k = 0; k < grid.length - 1; k++) {
                    try{
                        if (grid[k][i] == grid[k + 1][i]) {
                            grid[k + 1][i] = grid[k][i] + grid[k + 1][i];
                            grid[k][i] = 0;
                            score += grid[k+1][i];
                        } else if (grid[k + 1][i] == 0) {
                            grid[k + 1][i] = grid[k][i];
                            grid[k][i] = 0;
                        }
                    } catch (Exception e){

                    }
                }
            }
        }
    }


    public void onSwipeLeft() {
        for (int x = 0; x < grid.length; x++) {
            for (int k = 0; k < grid[grid.length - 1].length; k++) {
                for (int i = grid.length - 1; i > 0; i--) {
                    try {
                        if (grid[k][i] == grid[k][i - 1]) {
                            grid[k][i - 1] = grid[k][i] + grid[k][i - 1];
                            grid[k][i] = 0;
                            score += grid[k][i - 1];
                        } else if (grid[k][i - 1] == 0) {
                            grid[k][i - 1] = grid[k][i];
                            grid[k][i] = 0;
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }
    }

    public void onSwipeRight() {
        for (int x = 0; x < grid.length; x++) {
            for (int k = 0; k < grid[grid.length - 1].length; k++) {
                for (int i = 0; i < grid.length - 1; i++) {
                    try{
                        if (grid[k][i] == grid[k][i + 1]) {
                            grid[k][i + 1] = grid[k][i] + grid[k][i + 1];
                            grid[k][i] = 0;
                            score += grid[k][i+1];
                        } else if (grid[k][i + 1] == 0) {
                            grid[k][i + 1] = grid[k][i];
                            grid[k][i] = 0;
                        }
                    } catch (Exception e) {

                    }

                }
            }
        }
    }


    /*
    --- Helper Methods ---
     */


    public void update(){
        for(int i =0;i<grid.length;i++){
            for(int k = 0; k<grid[i].length;k++){
                String temp = "i" + i + k;
                ImageView img;
                switch (grid[i][k]) {
                    case 0:
                        img = (ImageView) findViewById(findID(temp));
                        img.setImageResource(R.drawable.zero);
                        break;
                    case 2:
                        img = (ImageView) findViewById(findID(temp));
                        img.setImageResource(R.drawable.two);
                        break;
                    case 4:
                        img = (ImageView) findViewById(findID(temp));
                        img.setImageResource(R.drawable.four);
                        break;
                    case 8:
                        img = (ImageView) findViewById(findID(temp));
                        img.setImageResource(R.drawable.eight);
                        break;
                    case 16:
                        img = (ImageView) findViewById(findID(temp));
                        img.setImageResource(R.drawable.sixteen);
                        break;
                    case 32:
                        img = (ImageView) findViewById(findID(temp));
                        img.setImageResource(R.drawable.thirtytwo);
                        break;
                    case 64:
                        img = (ImageView) findViewById(findID(temp));
                        img.setImageResource(R.drawable.sixtyfour);
                        break;
                    case 128:
                        img = (ImageView) findViewById(findID(temp));
                        img.setImageResource(R.drawable.onetwentyeight);
                        break;
                    case 256:
                        img = (ImageView) findViewById(findID(temp));
                        img.setImageResource(R.drawable.twofivesix);
                        break;
                    case 512:
                        img = (ImageView) findViewById(findID(temp));
                        img.setImageResource(R.drawable.fiveonetwo);
                        break;
                    case 1024:
                        img = (ImageView) findViewById(findID(temp));
                        img.setImageResource(R.drawable.onezerotwofour);
                        break;
                    case 2048:
                        img = (ImageView) findViewById(findID(temp));
                        img.setImageResource(R.drawable.twentyfoureight);
                        break;
                }
            }
        }


        TextView tv1 = (TextView)findViewById(R.id.score);
        String scoreStr = "Счёт - " + String.valueOf(score);
        tv1.setText(scoreStr);

        TextView tv2 = (TextView)findViewById(R.id.moves);
        String movesStr = "Количество ходов - " + String.valueOf(moves);
        tv2.setText(movesStr);
    }

    public void initialize(){
        for(int i=0;i<grid.length;i++){
            for(int k=0;k<grid[i].length;k++){
                grid[i][k] = 0;
            }
        }
    }

    public int findID(String input) {
        String in = input;
        switch (in) {
            case "i00":
                return R.id.i00;
            case "i01":
                return R.id.i01;
            case "i02":
                return R.id.i02;
            case "i03":
                return R.id.i03;
            case "i10":
                return R.id.i10;
            case "i11":
                return R.id.i11;
            case "i12":
                return R.id.i12;
            case "i13":
                return R.id.i13;
            case "i20":
                return R.id.i20;
            case "i21":
                return R.id.i21;
            case "i22":
                return R.id.i22;
            case "i23":
                return R.id.i23;
            case "i30":
                return R.id.i30;
            case "i31":
                return R.id.i31;
            case "i32":
                return R.id.i32;
            case "i33":
                return R.id.i33;
        }
        return 0;
    }


    public boolean isSpaceEmpty()
    {
        for(int i = 0; i< grid.length; i++)
        {
            for(int j = 0; j<grid[i].length; j++)
            {
                if(grid[i][j] == 0)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canCombine()
    {
        for(int k =0; k<grid[grid.length-1].length;k++){
            for(int i =0; i<grid.length-1;i++) {
                if (grid[k][i] == grid[k][i+1]) {
                    return true;
                }
            }
        }
        for(int i =0; i<grid[grid.length-1].length;i++) {
            for (int k = 0; k < grid.length - 1; k++) {
                if (grid[k][i] == grid[k + 1][i]) {
                    return true;
                }
            }
        }
        return false;
    }

    public void randomCords(){
        randomX = (int) (Math.random() * 4);
        randomY = (int) (Math.random() * 4);
    }

    // TODO: spawn in empty tiles only
    public void randomSpawn(){
        randomCords();
        if (grid[randomY][randomX] > 0){
            if(isSpaceEmpty()){
                randomSpawn();
            } else{
                if(!canCombine()){
                    new AlertDialog.Builder(this).setTitle("Вы проиграли(")
                            .setMessage("Счёт - " + score)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    initialize();
                                    score = 0;
                                    moves = 0;
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    update();
                }
            }
        } else {
            grid[randomY][randomX] = 2;
            update();
        }
    }
}
