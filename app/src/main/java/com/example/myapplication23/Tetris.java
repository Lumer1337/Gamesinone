package com.example.myapplication23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tetris extends AppCompatActivity {
    private LinearLayout linear_layout_rotate;
    private LinearLayout linear_layout_left;
    private LinearLayout linear_layout_right;
    private LinearLayout linear_layout_down;
    private HashMap<Integer, LinearLayout> integerLinearLayoutHashMap;
    private HashMap<LinearLayout, Boolean> booleanLinearLayoutHashMap;
    private int offset;
    private boolean isGameGoesOn;
    private long millisInFuture = 300L;
    private final long countDownInterval = 1L;
    private boolean isRotatePressed;
    private int randomBlock = 0;
    private boolean isRotateRightPressedOneTime;
    private boolean isRotateRightPressedTwoTimes;

    private boolean isRotateRightPressedThreeTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tetris);
        initialize();
        startGame();
        Button buttonset = (Button) findViewById(R.id.vernutca_button);
        buttonset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Tetris.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e) {}
            }
        });
    }

    private void initialize() {
        integerLinearLayoutHashMap = new HashMap<>();
        booleanLinearLayoutHashMap = new HashMap<>();
        isGameGoesOn = false;
        isRotatePressed = false;
        isRotateRightPressedOneTime = false;
        isRotateRightPressedTwoTimes = false;
        isRotateRightPressedThreeTimes = false;
        offset = 0;
        initButtons();
        buttonsSetOnClickListener();
        populateIntegerHashMapWithInitializedLinearLayouts();
        populateBooleanHashMapWithBooleanValuesIfEachLinearLayoutIsEmpty();
    }

    private void buttonsSetOnClickListener() {
        linearLayoutLeftSetOnClickListener();
        linearLayoutRightSetOnClickListener();
        linearLayoutRotateSetOnClickListener();
        linearLayoutDownSetOnClickListener();
    }

    private void linearLayoutDownSetOnClickListener() {
        linear_layout_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGameGoesOn) {
                    millisInFuture = 5L;
                }
            }
        });
    }

    private void linearLayoutRotateSetOnClickListener() {
        linear_layout_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRotatePressed = true;
            }
        });
    }

    private void linearLayoutLeftSetOnClickListener() {
        linear_layout_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGameGoesOn) {
                    offset--;
                }
            }
        });
    }

    private void linearLayoutRightSetOnClickListener() {
        linear_layout_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGameGoesOn) {
                    offset++;
                }
            }
        });
    }

    private int generateRandomPositionToStartBlock() {
        return 191 + (int) (Math.random() * 10);
    }

    private int generateRandomBlock() {
        return 1 + (int) (Math.random() * 7);
    }

    private void startGame() {
        new CountDownTimer(millisInFuture * 5, millisInFuture) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                isGameGoesOn = true;
                continueGame();
            }
        }.start();
    }

    private void continueGame() {
        if (isGameGoesOn) {
            int randomPositionToStartBlock = generateRandomPositionToStartBlock();
            randomBlock = generateRandomBlock();
            startBlockMove(randomBlock, randomPositionToStartBlock);
        }
    }

    private void startBlockMove(
            int randomBlock,
            int randomPositionToStartBlock
    ) {
        int positionForLinearLayout1;
        int positionForLinearLayout2;
        int positionForLinearLayout3;
        int positionForLinearLayout4;

        switch (randomBlock) {
            case 1:
                // I-Block
                positionForLinearLayout1 = randomPositionToStartBlock;
                positionForLinearLayout2 = getNextPosition(positionForLinearLayout1);
                positionForLinearLayout3 = getNextPosition(positionForLinearLayout2);
                positionForLinearLayout4 = getNextPosition(positionForLinearLayout3);
                continueBlockMove(
                        positionForLinearLayout4,
                        positionForLinearLayout3,
                        positionForLinearLayout2,
                        positionForLinearLayout1);
                break;
            case 2:
                // T-Block
                if (randomPositionToStartBlock >= 199) {
                    randomPositionToStartBlock = 198;
                }
                positionForLinearLayout1 = randomPositionToStartBlock;
                positionForLinearLayout2 = randomPositionToStartBlock + 1;
                positionForLinearLayout3 = randomPositionToStartBlock + 2;
                positionForLinearLayout4 = randomPositionToStartBlock + 1 - 10;
                continueBlockMove(
                        positionForLinearLayout4,
                        positionForLinearLayout3,
                        positionForLinearLayout2,
                        positionForLinearLayout1);
                break;
            case 3:
                // O-Block
                if (randomPositionToStartBlock >= 200) {
                    randomPositionToStartBlock = 199;
                }
                positionForLinearLayout1 = randomPositionToStartBlock;
                positionForLinearLayout2 = randomPositionToStartBlock - 10;
                positionForLinearLayout3 = randomPositionToStartBlock + 1;
                positionForLinearLayout4 = randomPositionToStartBlock + 1 - 10;
                continueBlockMove(
                        positionForLinearLayout4,
                        positionForLinearLayout3,
                        positionForLinearLayout2,
                        positionForLinearLayout1);
                break;
            case 4:
                // L-Block
                if (randomPositionToStartBlock >= 199) {
                    randomPositionToStartBlock = 198;
                }
                positionForLinearLayout1 = randomPositionToStartBlock - 10;
                positionForLinearLayout2 = randomPositionToStartBlock + 1 - 10;
                positionForLinearLayout3 = randomPositionToStartBlock + 2 - 10;
                positionForLinearLayout4 = randomPositionToStartBlock + 2;
                continueBlockMove(
                        positionForLinearLayout4,
                        positionForLinearLayout3,
                        positionForLinearLayout2,
                        positionForLinearLayout1);
                break;
            case 5:
                // J-Block
                if (randomPositionToStartBlock >= 199) {
                    randomPositionToStartBlock = 198;
                }
                positionForLinearLayout1 = randomPositionToStartBlock;
                positionForLinearLayout2 = randomPositionToStartBlock - 10;
                positionForLinearLayout3 = randomPositionToStartBlock + 1 - 10;
                positionForLinearLayout4 = randomPositionToStartBlock + 2 - 10;
                continueBlockMove(
                        positionForLinearLayout4,
                        positionForLinearLayout3,
                        positionForLinearLayout2,
                        positionForLinearLayout1);
                break;
            case 6:
                // S-Block
                if (randomPositionToStartBlock >= 198) {
                    randomPositionToStartBlock = 197;
                }
                positionForLinearLayout1 = randomPositionToStartBlock - 10;
                positionForLinearLayout2 = randomPositionToStartBlock + 1 - 10;
                positionForLinearLayout3 = randomPositionToStartBlock + 1;
                positionForLinearLayout4 = randomPositionToStartBlock + 2;
                continueBlockMove(
                        positionForLinearLayout4,
                        positionForLinearLayout3,
                        positionForLinearLayout2,
                        positionForLinearLayout1);
                break;
            case 7:
                // Z-Block
                if (randomPositionToStartBlock >= 198) {
                    randomPositionToStartBlock = 197;
                }
                positionForLinearLayout1 = randomPositionToStartBlock;
                positionForLinearLayout2 = randomPositionToStartBlock + 1;
                positionForLinearLayout3 = randomPositionToStartBlock + 1 - 10;
                positionForLinearLayout4 = randomPositionToStartBlock + 2 - 10;
                continueBlockMove(
                        positionForLinearLayout4,
                        positionForLinearLayout3,
                        positionForLinearLayout2,
                        positionForLinearLayout1);
                break;
            default:
                break;
        }
    }

    private void continueBlockMove(
            int positionLinearLayout4,
            int positionLinearLayout3,
            int positionLinearLayout2,
            int positionLinearLayout1
    ) {
        int nextPositionForLinearLayout1 = getNextPosition(positionLinearLayout1);
        int nextPositionForLinearLayout2 = getNextPosition(positionLinearLayout2);
        int nextPositionForLinearLayout3 = getNextPosition(positionLinearLayout3);
        int nextPositionForLinearLayout4 = getNextPosition(positionLinearLayout4);

        boolean areAllLinearLayoutPositionsGreaterThenZero = checkIfAllLinearLayoutPositionsAreGreaterThenZero(
                nextPositionForLinearLayout4,
                nextPositionForLinearLayout3,
                nextPositionForLinearLayout2,
                nextPositionForLinearLayout1
        );

        if (areAllLinearLayoutPositionsGreaterThenZero) {
            LinearLayout linearLayoutNextPosition1 = getLinearLayoutAtPosition(nextPositionForLinearLayout1);
            LinearLayout linearLayoutNextPosition2 = getLinearLayoutAtPosition(nextPositionForLinearLayout2);
            LinearLayout linearLayoutNextPosition3 = getLinearLayoutAtPosition(nextPositionForLinearLayout3);
            LinearLayout linearLayoutNextPosition4 = getLinearLayoutAtPosition(nextPositionForLinearLayout4);

            boolean areAllLinearLayoutsEmpty = checkIfAllLinearLayoutsAreEmpty(
                    linearLayoutNextPosition4,
                    linearLayoutNextPosition3,
                    linearLayoutNextPosition2,
                    linearLayoutNextPosition1
            );

            if (areAllLinearLayoutsEmpty) {
                setCountDownForBlock(
                        nextPositionForLinearLayout4,
                        nextPositionForLinearLayout3,
                        nextPositionForLinearLayout2,
                        nextPositionForLinearLayout1);
            } else {
                if (positionLinearLayout4 >= 161 &&
                        positionLinearLayout3 >= 161 &&
                        positionLinearLayout2 >= 161 &&
                        positionLinearLayout1 >= 161
                ) {
                    setLastFinishedPartOfItemsOnTheTopOfFieldBeforeGameIsOver(
                            positionLinearLayout4,
                            positionLinearLayout3,
                            positionLinearLayout2,
                            positionLinearLayout1);
                } else {
                    finishMovement(
                            positionLinearLayout4,
                            positionLinearLayout3,
                            positionLinearLayout2,
                            positionLinearLayout1);
                }
            }
        } else {
            finishMovement(
                    positionLinearLayout4,
                    positionLinearLayout3,
                    positionLinearLayout2,
                    positionLinearLayout1);
        }
    }

    private void setLastFinishedPartOfItemsOnTheTopOfFieldBeforeGameIsOver(
            int positionLinearLayout4,
            int positionLinearLayout3,
            int positionLinearLayout2,
            int positionLinearLayout1
    ) {
        LinearLayout linearLayout4 = getLinearLayoutAtPosition(positionLinearLayout4);
        LinearLayout linearLayout3 = getLinearLayoutAtPosition(positionLinearLayout3);
        LinearLayout linearLayout2 = getLinearLayoutAtPosition(positionLinearLayout2);
        LinearLayout linearLayout1 = getLinearLayoutAtPosition(positionLinearLayout1);

        if (booleanLinearLayoutHashMap.get(linearLayout4) == Boolean.TRUE) {
            setBackground(linearLayout4, R.drawable.one_item_background_4);
        }
        if (booleanLinearLayoutHashMap.get(linearLayout3) == Boolean.TRUE) {
            setBackground(linearLayout3, R.drawable.one_item_background_4);
        }
        if (booleanLinearLayoutHashMap.get(linearLayout2) == Boolean.TRUE) {
            setBackground(linearLayout2, R.drawable.one_item_background_4);
        }
        if (booleanLinearLayoutHashMap.get(linearLayout1) == Boolean.TRUE) {
            setBackground(linearLayout1, R.drawable.one_item_background_4);
        }

        isGameGoesOn = false;
        showToastMessageGameIsOver();
    }

    private void showToastMessageGameIsOver() {
        Toast.makeText(this, getResources().getString(R.string.game_is_over), Toast.LENGTH_LONG).show();
    }

    private void finishMovement(
            int positionForLinearLayout4,
            int positionForLinearLayout3,
            int positionForLinearLayout2,
            int positionForLinearLayout1
    ) {
        if (millisInFuture == 5L) {
            millisInFuture = 300L;
        }
        setBackgroundForAllFourItemsInBlockAtFinishedPosition(
                positionForLinearLayout4,
                positionForLinearLayout3,
                positionForLinearLayout2,
                positionForLinearLayout1);

        isRotateRightPressedOneTime = false;
        isRotateRightPressedTwoTimes = false;
        isRotateRightPressedThreeTimes = false;
        isRotatePressed = false;
        offset = 0;
        dropNextBlock();
    }

    private boolean[] getBooleanArrayValuesIfPreviousPositionOfAnyItemOfBlockNotTouchLeftOrRightBorder(
            int positionForLinearLayout4,
            int positionForLinearLayout3,
            int positionForLinearLayout2,
            int positionForLinearLayout1,
            int offset
    ) {
        boolean[] booleans = new boolean[20 * 4];
        int counter = 0;

        if (offset < 0) {
            for (int i = 1; i <= 200; i += 10) {
                booleans[counter] = positionForLinearLayout4 == i;
                counter++;
                booleans[counter] = positionForLinearLayout3 == i;
                counter++;
                booleans[counter] = positionForLinearLayout2 == i;
                counter++;
                booleans[counter] = positionForLinearLayout1 == i;
                counter++;
            }
        } else {
            for (int i = 200; i >= 1; i -= 10) {
                booleans[counter] = positionForLinearLayout4 == i;
                counter++;
                booleans[counter] = positionForLinearLayout3 == i;
                counter++;
                booleans[counter] = positionForLinearLayout2 == i;
                counter++;
                booleans[counter] = positionForLinearLayout1 == i;
                counter++;
            }
        }
        return booleans;
    }

    private void dropNextBlock() {
        new CountDownTimer(millisInFuture * 2, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                continueGame();
            }
        }.start();
    }

    private void setBackgroundForAllFourItemsInBlockAtFinishedPosition(
            int positionForLinearLayout4,
            int positionForLinearLayout3,
            int positionForLinearLayout2,
            int positionForLinearLayout1
    ) {
        LinearLayout linearLayout4 = getLinearLayoutAtPosition(positionForLinearLayout4);
        LinearLayout linearLayout3 = getLinearLayoutAtPosition(positionForLinearLayout3);
        LinearLayout linearLayout2 = getLinearLayoutAtPosition(positionForLinearLayout2);
        LinearLayout linearLayout1 = getLinearLayoutAtPosition(positionForLinearLayout1);

        setCountDownFinishedLine(
                linearLayout4,
                linearLayout3,
                linearLayout2,
                linearLayout1);
    }

    private int getNextPosition(int position) {
        return position - 10;
    }

    private LinearLayout getLinearLayoutAtPosition(int position) {
        return integerLinearLayoutHashMap.get(position);
    }

    private int setForOffsetFixedValueToMoveToLeftOrRightBorderOnlyOneStep(
            int offset
    ) {
        if (offset < 0) {
            offset = -1;
        } else {
            offset = 1;
        }
        return offset;
    }

    private void setCountDownForBlock(
            int positionForLinearLayout4,
            int positionForLinearLayout3,
            int positionForLinearLayout2,
            int positionForLinearLayout1
    ) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (offset != 0) {
                    offset = setForOffsetFixedValueToMoveToLeftOrRightBorderOnlyOneStep(offset);

                    boolean[] booleans = getBooleanArrayValuesIfPreviousPositionOfAnyItemOfBlockNotTouchLeftOrRightBorder(
                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1,
                            offset
                    );

                    boolean areAllItemsInArrayHaveFalseValue = checkIfAllItemsInArrayHaveFalseValue(booleans);

                    if (areAllItemsInArrayHaveFalseValue) {
                        setCountDownIfOffsetNotZero(
                                positionForLinearLayout4,
                                positionForLinearLayout3,
                                positionForLinearLayout2,
                                positionForLinearLayout1);
                    } else {
                        offset = 0;
                        setCountDownIfOffsetZero(
                                positionForLinearLayout4,
                                positionForLinearLayout3,
                                positionForLinearLayout2,
                                positionForLinearLayout1);
                    }
                } else {
                    if (isRotatePressed) {
                        setCountDownIfRotateButtonWasPressed(
                                positionForLinearLayout4,
                                positionForLinearLayout3,
                                positionForLinearLayout2,
                                positionForLinearLayout1);
                    } else {
                        setCountDownIfOffsetZero(
                                positionForLinearLayout4,
                                positionForLinearLayout3,
                                positionForLinearLayout2,
                                positionForLinearLayout1);
                    }
                }
            }
        });
    }

    private boolean checkIfAllLinearLayoutPositionsAreGreaterThenZero(
            int positionForLinearLayout4,
            int positionForLinearLayout3,
            int positionForLinearLayout2,
            int positionForLinearLayout1
    ) {
        return positionForLinearLayout4 > 0 &&
                positionForLinearLayout3 > 0 &&
                positionForLinearLayout2 > 0 &&
                positionForLinearLayout1 > 0;
    }

    private boolean checkIfAllLinearLayoutsAreNotNull(
            LinearLayout linearLayout4,
            LinearLayout linearLayout3,
            LinearLayout linearLayout2,
            LinearLayout linearLayout1
    ) {
        return linearLayout4 != null &&
                linearLayout3 != null &&
                linearLayout2 != null &&
                linearLayout1 != null;
    }

    private boolean checkIfAllLinearLayoutsAreEmpty(
            LinearLayout linearLayout4,
            LinearLayout linearLayout3,
            LinearLayout linearLayout2,
            LinearLayout linearLayout1
    ) {
        return booleanLinearLayoutHashMap.get(linearLayout4) == Boolean.TRUE &&
                booleanLinearLayoutHashMap.get(linearLayout3) == Boolean.TRUE &&
                booleanLinearLayoutHashMap.get(linearLayout2) == Boolean.TRUE &&
                booleanLinearLayoutHashMap.get(linearLayout1) == Boolean.TRUE;
    }

    private void setCountDownIfOffsetNotZero(
            int positionForLinearLayout4,
            int positionForLinearLayout3,
            int positionForLinearLayout2,
            int positionForLinearLayout1
    ) {
        int positionForLinearLayoutWithOffset1 = getNewPositionWithOffset(positionForLinearLayout1);
        int positionForLinearLayoutWithOffset2 = getNewPositionWithOffset(positionForLinearLayout2);
        int positionForLinearLayoutWithOffset3 = getNewPositionWithOffset(positionForLinearLayout3);
        int positionForLinearLayoutWithOffset4 = getNewPositionWithOffset(positionForLinearLayout4);

        boolean areAllLinearLayoutPositionsGreaterThenZero
                = checkIfAllLinearLayoutPositionsAreGreaterThenZero(
                positionForLinearLayoutWithOffset4,
                positionForLinearLayoutWithOffset3,
                positionForLinearLayoutWithOffset2,
                positionForLinearLayoutWithOffset1);

        boolean isNewPositionOfBlockNotOverlapPreviousBlocks
                = checkIfNewPositionOfBlockNotOverlapPreviousBlocks(
                positionForLinearLayoutWithOffset4,
                positionForLinearLayoutWithOffset3,
                positionForLinearLayoutWithOffset2,
                positionForLinearLayoutWithOffset1);

        if (areAllLinearLayoutPositionsGreaterThenZero && isNewPositionOfBlockNotOverlapPreviousBlocks) {
            LinearLayout linearLayoutWithOffset1 = getLinearLayoutAtPosition(positionForLinearLayoutWithOffset1);
            LinearLayout linearLayoutWithOffset2 = getLinearLayoutAtPosition(positionForLinearLayoutWithOffset2);
            LinearLayout linearLayoutWithOffset3 = getLinearLayoutAtPosition(positionForLinearLayoutWithOffset3);
            LinearLayout linearLayoutWithOffset4 = getLinearLayoutAtPosition(positionForLinearLayoutWithOffset4);

            boolean areAllLinearLayoutsNotNull = checkIfAllLinearLayoutsAreNotNull(
                    linearLayoutWithOffset4,
                    linearLayoutWithOffset3,
                    linearLayoutWithOffset2,
                    linearLayoutWithOffset1);

            if (areAllLinearLayoutsNotNull) {
                boolean areAllLinearLayoutsEmpty = checkIfAllLinearLayoutsAreEmpty(
                        linearLayoutWithOffset4,
                        linearLayoutWithOffset3,
                        linearLayoutWithOffset2,
                        linearLayoutWithOffset1
                );

                if (areAllLinearLayoutsEmpty) {
                    new CountDownTimer(millisInFuture, countDownInterval) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            setBackground(
                                    linearLayoutWithOffset4,
                                    linearLayoutWithOffset3,
                                    linearLayoutWithOffset2,
                                    linearLayoutWithOffset1,
                                    R.drawable.one_item_background_4);
                        }

                        @Override
                        public void onFinish() {
                            int nextPositionForLinearLayout4 = getNextPosition(positionForLinearLayoutWithOffset4);
                            int nextPositionForLinearLayout3 = getNextPosition(positionForLinearLayoutWithOffset3);
                            int nextPositionForLinearLayout2 = getNextPosition(positionForLinearLayoutWithOffset2);
                            int nextPositionForLinearLayout1 = getNextPosition(positionForLinearLayoutWithOffset1);

                            boolean areAllLinearLayoutPositionsGreaterThenZero = checkIfAllLinearLayoutPositionsAreGreaterThenZero(
                                    nextPositionForLinearLayout4,
                                    nextPositionForLinearLayout3,
                                    nextPositionForLinearLayout2,
                                    nextPositionForLinearLayout1
                            );

                            if (areAllLinearLayoutPositionsGreaterThenZero) {
                                LinearLayout linearLayoutNext1 = getLinearLayoutAtPosition(nextPositionForLinearLayout1);
                                LinearLayout linearLayoutNext2 = getLinearLayoutAtPosition(nextPositionForLinearLayout2);
                                LinearLayout linearLayoutNext3 = getLinearLayoutAtPosition(nextPositionForLinearLayout3);
                                LinearLayout linearLayoutNext4 = getLinearLayoutAtPosition(nextPositionForLinearLayout4);

                                boolean areAllLinearLayoutsEmpty = checkIfAllLinearLayoutsAreEmpty(
                                        linearLayoutNext4,
                                        linearLayoutNext3,
                                        linearLayoutNext2,
                                        linearLayoutNext1);

                                if (areAllLinearLayoutsEmpty) {
                                    setBackground(
                                            linearLayoutWithOffset4,
                                            linearLayoutWithOffset3,
                                            linearLayoutWithOffset2,
                                            linearLayoutWithOffset1,
                                            R.drawable.one_item_background_1);

                                    continueBlockMove(
                                            positionForLinearLayoutWithOffset4,
                                            positionForLinearLayoutWithOffset3,
                                            positionForLinearLayoutWithOffset2,
                                            positionForLinearLayoutWithOffset1);
                                } else {
                                    finishMovement(
                                            positionForLinearLayoutWithOffset4,
                                            positionForLinearLayoutWithOffset3,
                                            positionForLinearLayoutWithOffset2,
                                            positionForLinearLayoutWithOffset1);
                                }
                            } else {
                                finishMovement(
                                        positionForLinearLayoutWithOffset4,
                                        positionForLinearLayoutWithOffset3,
                                        positionForLinearLayoutWithOffset2,
                                        positionForLinearLayoutWithOffset1);
                            }
                        }
                    }.start();
                }
            }
        } else {
            setCountDownIfOffsetZero(
                    positionForLinearLayout4,
                    positionForLinearLayout3,
                    positionForLinearLayout2,
                    positionForLinearLayout1);
        }
        offset = 0;
    }

    private void setCountDownIfOffsetZero(
            int positionForLinearLayout4,
            int positionForLinearLayout3,
            int positionForLinearLayout2,
            int positionForLinearLayout1
    ) {
        LinearLayout linearLayout1 = getLinearLayoutAtPosition(positionForLinearLayout1);
        LinearLayout linearLayout2 = getLinearLayoutAtPosition(positionForLinearLayout2);
        LinearLayout linearLayout3 = getLinearLayoutAtPosition(positionForLinearLayout3);
        LinearLayout linearLayout4 = getLinearLayoutAtPosition(positionForLinearLayout4);

        new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                setBackground(
                        linearLayout4,
                        linearLayout3,
                        linearLayout2,
                        linearLayout1,
                        R.drawable.one_item_background_4);
            }

            @Override
            public void onFinish() {
                int nextPositionForLinearLayout1 = getNextPosition(positionForLinearLayout1);
                int nextPositionForLinearLayout2 = getNextPosition(positionForLinearLayout2);
                int nextPositionForLinearLayout3 = getNextPosition(positionForLinearLayout3);
                int nextPositionForLinearLayout4 = getNextPosition(positionForLinearLayout4);

                boolean areAllLinearLayoutPositionsGreaterThenZero = checkIfAllLinearLayoutPositionsAreGreaterThenZero(
                        nextPositionForLinearLayout4,
                        nextPositionForLinearLayout3,
                        nextPositionForLinearLayout2,
                        nextPositionForLinearLayout1);

                if (areAllLinearLayoutPositionsGreaterThenZero) {
                    LinearLayout linearLayoutNext1 = getLinearLayoutAtPosition(nextPositionForLinearLayout1);
                    LinearLayout linearLayoutNext2 = getLinearLayoutAtPosition(nextPositionForLinearLayout2);
                    LinearLayout linearLayoutNext3 = getLinearLayoutAtPosition(nextPositionForLinearLayout3);
                    LinearLayout linearLayoutNext4 = getLinearLayoutAtPosition(nextPositionForLinearLayout4);

                    boolean areAllLinearLayoutsEmpty = checkIfAllLinearLayoutsAreEmpty(
                            linearLayoutNext4,
                            linearLayoutNext3,
                            linearLayoutNext2,
                            linearLayoutNext1);

                    if (areAllLinearLayoutsEmpty) {
                        setBackground(
                                linearLayout4,
                                linearLayout3,
                                linearLayout2,
                                linearLayout1,
                                R.drawable.one_item_background_1);

                        continueBlockMove(
                                positionForLinearLayout4,
                                positionForLinearLayout3,
                                positionForLinearLayout2,
                                positionForLinearLayout1);
                    } else {
                        finishMovement(
                                positionForLinearLayout4,
                                positionForLinearLayout3,
                                positionForLinearLayout2,
                                positionForLinearLayout1);
                    }
                } else {
                    finishMovement(
                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1);
                }
            }
        }.start();
    }

    private boolean checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
            int newPositionForLinearLayout4,
            int newPositionForLinearLayout3,
            int newPositionForLinearLayout2,
            int newPositionForLinearLayout1,

            int positionForLinearLayout4,
            int positionForLinearLayout3,
            int positionForLinearLayout2,
            int positionForLinearLayout1
    ) {
        List<Boolean> booleanList = new ArrayList<>();

        booleanList.add(checkIfNewRotatedPositionOfOneItemOfBlockIsOutOfLeftAndRightBordersOfField(
                newPositionForLinearLayout4,
                positionForLinearLayout4));

        booleanList.add(checkIfNewRotatedPositionOfOneItemOfBlockIsOutOfLeftAndRightBordersOfField(
                newPositionForLinearLayout3,
                positionForLinearLayout3));

        booleanList.add(checkIfNewRotatedPositionOfOneItemOfBlockIsOutOfLeftAndRightBordersOfField(
                newPositionForLinearLayout2,
                positionForLinearLayout2));

        booleanList.add(checkIfNewRotatedPositionOfOneItemOfBlockIsOutOfLeftAndRightBordersOfField(
                newPositionForLinearLayout1,
                positionForLinearLayout1));

        for (Boolean b : booleanList) if (b) return true;
        return false;
    }

    private boolean checkIfNewRotatedPositionOfOneItemOfBlockIsOutOfLeftAndRightBordersOfField(
            int newPositionForLinearLayout,
            int positionForLinearLayout
    ) {
        List<Boolean> booleanList = new ArrayList<>();

        for (int i = 10; i <= 200; i += 10) {
            for (int j = 0; j <= 40; j += 10) {
                booleanList.add(positionForLinearLayout >= i - 3 &&
                        positionForLinearLayout <= i &&
                        newPositionForLinearLayout >= (i + j) + 1 &&
                        newPositionForLinearLayout <= (i + j) + 3);

                booleanList.add(positionForLinearLayout >= i - 3 &&
                        positionForLinearLayout <= i &&
                        newPositionForLinearLayout >= (i - j) + 1 &&
                        newPositionForLinearLayout <= (i - j) + 3);

                booleanList.add(positionForLinearLayout >= i - 9 &&
                        positionForLinearLayout <= i - 6 &&
                        newPositionForLinearLayout >= (i + j) - 3 &&
                        newPositionForLinearLayout <= (i + j));

                booleanList.add(positionForLinearLayout >= i - 9 &&
                        positionForLinearLayout <= i - 6 &&
                        newPositionForLinearLayout >= (i - j) - 3 &&
                        newPositionForLinearLayout <= (i - j));
            }
        }

        for (Boolean b : booleanList) if (b) return true;
        return false;
    }

    private boolean checkIfNewPositionOfBlockNotOverlapPreviousBlocks(
            int newPositionForLinearLayout4,
            int newPositionForLinearLayout3,
            int newPositionForLinearLayout2,
            int newPositionForLinearLayout1
    ) {
        LinearLayout linearLayout4 = getLinearLayoutAtPosition(newPositionForLinearLayout4);
        LinearLayout linearLayout3 = getLinearLayoutAtPosition(newPositionForLinearLayout3);
        LinearLayout linearLayout2 = getLinearLayoutAtPosition(newPositionForLinearLayout2);
        LinearLayout linearLayout1 = getLinearLayoutAtPosition(newPositionForLinearLayout1);

        return booleanLinearLayoutHashMap.get(linearLayout4) == Boolean.TRUE &&
                booleanLinearLayoutHashMap.get(linearLayout3) == Boolean.TRUE &&
                booleanLinearLayoutHashMap.get(linearLayout2) == Boolean.TRUE &&
                booleanLinearLayoutHashMap.get(linearLayout1) == Boolean.TRUE;
    }

    private void setCountDownIfRotateButtonWasPressed(
            int positionForLinearLayout4,
            int positionForLinearLayout3,
            int positionForLinearLayout2,
            int positionForLinearLayout1
    ) {
        int newPositionForLinearLayout4 = positionForLinearLayout4;
        int newPositionForLinearLayout3 = positionForLinearLayout3;
        int newPositionForLinearLayout2 = positionForLinearLayout2;
        int newPositionForLinearLayout1 = positionForLinearLayout1;

        if (isRotatePressed) {
            if (randomBlock == 1) {
                // I-Block
                if (!isRotateRightPressedOneTime &&
                        !isRotateRightPressedTwoTimes &&
                        !isRotateRightPressedThreeTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 + 1 + 20;
                    newPositionForLinearLayout3 = positionForLinearLayout3 + 10;
                    newPositionForLinearLayout2 = positionForLinearLayout2 - 1;
                    newPositionForLinearLayout1 = positionForLinearLayout1 - 2 - 10;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;
                    } else {
                        isRotateRightPressedOneTime = true;
                    }

                } else if (isRotateRightPressedOneTime &&
                        !isRotateRightPressedTwoTimes &&
                        !isRotateRightPressedThreeTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 - 1 - 20;
                    newPositionForLinearLayout3 = positionForLinearLayout3 - 10;
                    newPositionForLinearLayout2 = positionForLinearLayout2 + 1;
                    newPositionForLinearLayout1 = positionForLinearLayout1 + 2 + 10;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;

                    } else {
                        isRotateRightPressedOneTime = false;
                        isRotatePressed = false;
                    }
                }

            } else if (randomBlock == 2) {
                // T-Block
                if (!isRotateRightPressedOneTime &&
                        !isRotateRightPressedTwoTimes &&
                        !isRotateRightPressedThreeTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 - 1 + 10;
                    newPositionForLinearLayout3 = positionForLinearLayout3 - 1 - 10;
                    newPositionForLinearLayout2 = positionForLinearLayout2;
                    newPositionForLinearLayout1 = positionForLinearLayout1 + 1 + 10;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;
                    } else {
                        isRotateRightPressedOneTime = true;
                    }

                } else if (isRotateRightPressedOneTime &&
                        !isRotateRightPressedTwoTimes &&
                        !isRotateRightPressedThreeTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 + 1 + 10;
                    newPositionForLinearLayout3 = positionForLinearLayout3 - 1 + 10;
                    newPositionForLinearLayout2 = positionForLinearLayout2;
                    newPositionForLinearLayout1 = positionForLinearLayout1 + 1 - 10;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;

                    } else {
                        isRotateRightPressedOneTime = false;
                        isRotateRightPressedTwoTimes = true;
                    }

                } else if (!isRotateRightPressedOneTime &&
                        isRotateRightPressedTwoTimes &&
                        !isRotateRightPressedThreeTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 + 1 - 10;
                    newPositionForLinearLayout3 = positionForLinearLayout3 + 1 + 10;
                    newPositionForLinearLayout2 = positionForLinearLayout2;
                    newPositionForLinearLayout1 = positionForLinearLayout1 - 1 - 10;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;

                    } else {
                        isRotateRightPressedTwoTimes = false;
                        isRotateRightPressedThreeTimes = true;
                    }

                } else if (!isRotateRightPressedOneTime &&
                        !isRotateRightPressedTwoTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 - 1 - 10;
                    newPositionForLinearLayout3 = positionForLinearLayout3 + 1 - 10;
                    newPositionForLinearLayout2 = positionForLinearLayout2;
                    newPositionForLinearLayout1 = positionForLinearLayout1 - 1 + 10;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;

                    } else {
                        isRotateRightPressedThreeTimes = false;
                        isRotatePressed = false;
                    }
                }

            } else if (randomBlock == 4) {
                // L-Block
                if (!isRotateRightPressedOneTime &&
                        !isRotateRightPressedTwoTimes &&
                        !isRotateRightPressedThreeTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 - 10;
                    newPositionForLinearLayout3 = positionForLinearLayout3 - 1;
                    newPositionForLinearLayout2 = positionForLinearLayout2 + 10;
                    newPositionForLinearLayout1 = positionForLinearLayout1 + 1 + 20;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;

                    } else {
                        isRotateRightPressedOneTime = true;
                    }

                } else if (isRotateRightPressedOneTime &&
                        !isRotateRightPressedTwoTimes &&
                        !isRotateRightPressedThreeTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 - 1;
                    newPositionForLinearLayout3 = positionForLinearLayout3 + 10;
                    newPositionForLinearLayout2 = positionForLinearLayout2 + 1;
                    newPositionForLinearLayout1 = positionForLinearLayout1 - 10 + 2;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;

                    } else {
                        isRotateRightPressedOneTime = false;
                        isRotateRightPressedTwoTimes = true;
                    }

                } else if (!isRotateRightPressedOneTime &&
                        isRotateRightPressedTwoTimes &&
                        !isRotateRightPressedThreeTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 + 10;
                    newPositionForLinearLayout3 = positionForLinearLayout3 + 1;
                    newPositionForLinearLayout2 = positionForLinearLayout2 - 10;
                    newPositionForLinearLayout1 = positionForLinearLayout1 - 1 - 20;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;

                    } else {
                        isRotateRightPressedTwoTimes = false;
                        isRotateRightPressedThreeTimes = true;
                    }

                } else if (!isRotateRightPressedOneTime &&
                        !isRotateRightPressedTwoTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 + 1 - 10;
                    newPositionForLinearLayout3 = positionForLinearLayout3 - 20;
                    newPositionForLinearLayout2 = positionForLinearLayout2 - 1 - 10;
                    newPositionForLinearLayout1 = positionForLinearLayout1 - 2;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;

                    } else {
                        isRotateRightPressedThreeTimes = false;
                        isRotatePressed = false;
                    }
                }

            } else if (randomBlock == 5) {
                // J-Block
                if (!isRotateRightPressedOneTime &&
                        !isRotateRightPressedTwoTimes &&
                        !isRotateRightPressedThreeTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 - 1 - 10;
                    newPositionForLinearLayout3 = positionForLinearLayout3;
                    newPositionForLinearLayout2 = positionForLinearLayout2 + 1 + 10;
                    newPositionForLinearLayout1 = positionForLinearLayout1 + 2;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;

                    } else {
                        isRotateRightPressedOneTime = true;
                    }

                } else if (isRotateRightPressedOneTime &&
                        !isRotateRightPressedTwoTimes &&
                        !isRotateRightPressedThreeTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 - 1 + 10;
                    newPositionForLinearLayout3 = positionForLinearLayout3;
                    newPositionForLinearLayout2 = positionForLinearLayout2 + 1 - 10;
                    newPositionForLinearLayout1 = positionForLinearLayout1 - 20;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;

                    } else {
                        isRotateRightPressedOneTime = false;
                        isRotateRightPressedTwoTimes = true;
                    }

                } else if (!isRotateRightPressedOneTime &&
                        isRotateRightPressedTwoTimes &&
                        !isRotateRightPressedThreeTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 + 1 + 10;
                    newPositionForLinearLayout3 = positionForLinearLayout3;
                    newPositionForLinearLayout2 = positionForLinearLayout2 - 1 - 10;
                    newPositionForLinearLayout1 = positionForLinearLayout1 - 2;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;

                    } else {
                        isRotateRightPressedTwoTimes = false;
                        isRotateRightPressedThreeTimes = true;
                    }

                } else if (!isRotateRightPressedOneTime &&
                        !isRotateRightPressedTwoTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 + 1 - 10;
                    newPositionForLinearLayout3 = positionForLinearLayout3;
                    newPositionForLinearLayout2 = positionForLinearLayout2 - 1 + 10;
                    newPositionForLinearLayout1 = positionForLinearLayout1 + 20;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;

                    } else {
                        isRotateRightPressedThreeTimes = false;
                        isRotatePressed = false;
                    }
                }

            } else if (randomBlock == 6) {
                // S-Block
                if (!isRotateRightPressedOneTime &&
                        !isRotateRightPressedTwoTimes &&
                        !isRotateRightPressedThreeTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 - 1 - 10;
                    newPositionForLinearLayout3 = positionForLinearLayout3;
                    newPositionForLinearLayout2 = positionForLinearLayout2 - 1 + 10;
                    newPositionForLinearLayout1 = positionForLinearLayout1 + 20;


                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;

                    } else {
                        isRotateRightPressedOneTime = true;
                    }

                } else if (isRotateRightPressedOneTime &&
                        !isRotateRightPressedTwoTimes &&
                        !isRotateRightPressedThreeTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 + 1 + 10;
                    newPositionForLinearLayout3 = positionForLinearLayout3;
                    newPositionForLinearLayout2 = positionForLinearLayout2 + 1 - 10;
                    newPositionForLinearLayout1 = positionForLinearLayout1 - 20;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;

                    } else {
                        isRotateRightPressedOneTime = false;
                        isRotatePressed = false;
                    }
                }

            } else if (randomBlock == 7) {
                // Z-Block
                if (!isRotateRightPressedOneTime &&
                        !isRotateRightPressedTwoTimes &&
                        !isRotateRightPressedThreeTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 - 1;
                    newPositionForLinearLayout3 = positionForLinearLayout3 + 10;
                    newPositionForLinearLayout2 = positionForLinearLayout2 + 1;
                    newPositionForLinearLayout1 = positionForLinearLayout1 + 2 + 10;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;

                    } else {
                        isRotateRightPressedOneTime = true;
                    }

                } else if (isRotateRightPressedOneTime &&
                        !isRotateRightPressedTwoTimes &&
                        !isRotateRightPressedThreeTimes
                ) {
                    newPositionForLinearLayout4 = positionForLinearLayout4 + 1;
                    newPositionForLinearLayout3 = positionForLinearLayout3 - 10;
                    newPositionForLinearLayout2 = positionForLinearLayout2 - 1;
                    newPositionForLinearLayout1 = positionForLinearLayout1 - 2 - 10;

                    boolean isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField
                            = checkIfNewRotatedPositionOfWholeBlockIsOutOfLeftAndRightBordersOfField(
                            newPositionForLinearLayout4,
                            newPositionForLinearLayout3,
                            newPositionForLinearLayout2,
                            newPositionForLinearLayout1,

                            positionForLinearLayout4,
                            positionForLinearLayout3,
                            positionForLinearLayout2,
                            positionForLinearLayout1
                    );

                    if (isNewRotatedPositionOfWholeBlockOutOfLeftAndRightBordersOfField) {
                        newPositionForLinearLayout4 = positionForLinearLayout4;
                        newPositionForLinearLayout3 = positionForLinearLayout3;
                        newPositionForLinearLayout2 = positionForLinearLayout2;
                        newPositionForLinearLayout1 = positionForLinearLayout1;

                    } else {
                        isRotateRightPressedOneTime = false;
                        isRotatePressed = false;
                    }
                }
            }
        }

        boolean isNewPositionOfBlockNotOverlapPreviousBlocks
                = checkIfNewPositionOfBlockNotOverlapPreviousBlocks(
                newPositionForLinearLayout4,
                newPositionForLinearLayout3,
                newPositionForLinearLayout2,
                newPositionForLinearLayout1);

        if (isNewPositionOfBlockNotOverlapPreviousBlocks) {
            LinearLayout linearLayoutNewPosition1 = getLinearLayoutAtPosition(newPositionForLinearLayout1);
            LinearLayout linearLayoutNewPosition2 = getLinearLayoutAtPosition(newPositionForLinearLayout2);
            LinearLayout linearLayoutNewPosition3 = getLinearLayoutAtPosition(newPositionForLinearLayout3);
            LinearLayout linearLayoutNewPosition4 = getLinearLayoutAtPosition(newPositionForLinearLayout4);

            int finalNewPositionForLinearLayout1 = newPositionForLinearLayout1;
            int finalNewPositionForLinearLayout2 = newPositionForLinearLayout2;
            int finalNewPositionForLinearLayout3 = newPositionForLinearLayout3;
            int finalNewPositionForLinearLayout4 = newPositionForLinearLayout4;

            new CountDownTimer(millisInFuture, countDownInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setBackground(
                            linearLayoutNewPosition4,
                            linearLayoutNewPosition3,
                            linearLayoutNewPosition2,
                            linearLayoutNewPosition1,
                            R.drawable.one_item_background_4);
                }

                @Override
                public void onFinish() {
                    int nextPositionForLinearLayout1 = getNextPosition(finalNewPositionForLinearLayout1);
                    int nextPositionForLinearLayout2 = getNextPosition(finalNewPositionForLinearLayout2);
                    int nextPositionForLinearLayout3 = getNextPosition(finalNewPositionForLinearLayout3);
                    int nextPositionForLinearLayout4 = getNextPosition(finalNewPositionForLinearLayout4);

                    boolean areAllLinearLayoutPositionsGreaterThenZero = checkIfAllLinearLayoutPositionsAreGreaterThenZero(
                            nextPositionForLinearLayout4,
                            nextPositionForLinearLayout3,
                            nextPositionForLinearLayout2,
                            nextPositionForLinearLayout1);

                    if (areAllLinearLayoutPositionsGreaterThenZero) {
                        LinearLayout linearLayoutNext1 = getLinearLayoutAtPosition(nextPositionForLinearLayout1);
                        LinearLayout linearLayoutNext2 = getLinearLayoutAtPosition(nextPositionForLinearLayout2);
                        LinearLayout linearLayoutNext3 = getLinearLayoutAtPosition(nextPositionForLinearLayout3);
                        LinearLayout linearLayoutNext4 = getLinearLayoutAtPosition(nextPositionForLinearLayout4);

                        boolean areAllLinearLayoutsEmpty = checkIfAllLinearLayoutsAreEmpty(
                                linearLayoutNext4,
                                linearLayoutNext3,
                                linearLayoutNext2,
                                linearLayoutNext1);

                        if (areAllLinearLayoutsEmpty) {
                            setBackground(
                                    linearLayoutNewPosition4,
                                    linearLayoutNewPosition3,
                                    linearLayoutNewPosition2,
                                    linearLayoutNewPosition1,
                                    R.drawable.one_item_background_1);

                            continueBlockMove(
                                    finalNewPositionForLinearLayout4,
                                    finalNewPositionForLinearLayout3,
                                    finalNewPositionForLinearLayout2,
                                    finalNewPositionForLinearLayout1);
                        } else {
                            finishMovement(
                                    finalNewPositionForLinearLayout4,
                                    finalNewPositionForLinearLayout3,
                                    finalNewPositionForLinearLayout2,
                                    finalNewPositionForLinearLayout1);
                        }
                    } else {
                        finishMovement(
                                finalNewPositionForLinearLayout4,
                                finalNewPositionForLinearLayout3,
                                finalNewPositionForLinearLayout2,
                                finalNewPositionForLinearLayout1);
                    }
                }
            }.start();
        } else {
            finishMovement(
                    positionForLinearLayout4,
                    positionForLinearLayout3,
                    positionForLinearLayout2,
                    positionForLinearLayout1);
        }
        isRotatePressed = false;
    }

    private int getNewPositionWithOffset(int position) {
        return position + offset;
    }

    private void setBackground(
            View view,
            int drawable
    ) {
        view.setBackground(ResourcesCompat.getDrawable(getResources(), drawable, null));
    }

    private void setBackground(
            LinearLayout linearLayout4,
            LinearLayout linearLayout3,
            LinearLayout linearLayout2,
            LinearLayout linearLayout1,
            int drawable
    ) {
        setBackground(linearLayout4, drawable);
        setBackground(linearLayout3, drawable);
        setBackground(linearLayout2, drawable);
        setBackground(linearLayout1, drawable);
    }

    private void setCountDownFinishedLine(
            LinearLayout linearLayout4,
            LinearLayout linearLayout3,
            LinearLayout linearLayout2,
            LinearLayout linearLayout1
    ) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                new CountDownTimer(millisInFuture, countDownInterval) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        setBackground(
                                linearLayout4,
                                linearLayout3,
                                linearLayout2,
                                linearLayout1,
                                R.drawable.one_item_background_4);
                    }

                    @Override
                    public void onFinish() {
                        finishLastMovement(
                                linearLayout4,
                                linearLayout3,
                                linearLayout2,
                                linearLayout1);
                    }
                }.start();
            }
        });
    }

    private void finishLastMovement(
            LinearLayout linearLayout4,
            LinearLayout linearLayout3,
            LinearLayout linearLayout2,
            LinearLayout linearLayout1
    ) {
        booleanLinearLayoutHashMap.put(linearLayout4, Boolean.FALSE);
        booleanLinearLayoutHashMap.put(linearLayout3, Boolean.FALSE);
        booleanLinearLayoutHashMap.put(linearLayout2, Boolean.FALSE);
        booleanLinearLayoutHashMap.put(linearLayout1, Boolean.FALSE);

        int filledLineNumber = checkIfWholeLineIsFilled();
        if (filledLineNumber != 0) {
            changeLineColor(filledLineNumber);
        }
    }

    private void changeLineColor(int filledLineNumber) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.whole_line_animation);
        for (int i = 1; i <= 10; i++) {
            int position = (filledLineNumber - 1) * 10 + i;
            LinearLayout linearLayout = getLinearLayoutAtPosition(position);
            if (linearLayout != null) {
                setBackground(linearLayout, R.drawable.one_item_background_5);
                linearLayout.startAnimation(anim);
            }
        }
        clearWholeFilledHorizontalLineAndRefreshScreen(filledLineNumber);
    }

    private void clearWholeFilledHorizontalLineAndRefreshScreen(int filledLineNumber) {
        millisInFuture = 300L;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                repaintEachItemBackgroundAndMoveBooleanValueToAccordingDownLineItem(filledLineNumber);
            }
        }, millisInFuture);
    }

    private void repaintEachItemBackgroundAndMoveBooleanValueToAccordingDownLineItem(
            int filledLineNumber
    ) {
        for (int i = filledLineNumber; i <= 20; i++) {
            for (int j = 1; j <= 10; j++) {
                int positionCurrent = (i - 1) * 10 + j;
                LinearLayout linearLayoutCurrent = getLinearLayoutAtPosition(positionCurrent);
                if (i == 20) {
                    booleanLinearLayoutHashMap.put(linearLayoutCurrent, Boolean.TRUE);
                    setBackground(linearLayoutCurrent, R.drawable.one_item_background_1);
                } else {
                    int positionUpperLine = i * 10 + j;
                    LinearLayout linearLayoutUpperLine = getLinearLayoutAtPosition(positionUpperLine);
                    Boolean ifItemIsEmptyUpperLine = booleanLinearLayoutHashMap.get(linearLayoutUpperLine);
                    booleanLinearLayoutHashMap.put(linearLayoutCurrent, ifItemIsEmptyUpperLine);
                    if (ifItemIsEmptyUpperLine == Boolean.FALSE) {
                        setBackground(linearLayoutCurrent, R.drawable.one_item_background_4);
                    } else {
                        setBackground(linearLayoutCurrent, R.drawable.one_item_background_1);
                    }
                }
            }
        }

        int newFilledLineNumber = checkIfWholeLineIsFilled();
        if (newFilledLineNumber != 0) {
            changeLineColor(newFilledLineNumber);
        }
    }

    private int checkIfWholeLineIsFilled() {
        int filledLineNumber = 0;
        for (int i = 1; i <= 20; i++) {
            boolean[] booleans = new boolean[10];
            for (int j = 0; j < 10; j++) {
                int position = (i - 1) * 10 + (j + 1);
                LinearLayout linearLayout = getLinearLayoutAtPosition(position);
                Boolean ifItemIsEmpty = booleanLinearLayoutHashMap.get(linearLayout);

                if (ifItemIsEmpty != null) {
                    booleans[j] = ifItemIsEmpty;
                }
            }
            boolean areAllItemsInArrayHaveFalseValue = checkIfAllItemsInArrayHaveFalseValue(booleans);
            if (areAllItemsInArrayHaveFalseValue) {
                filledLineNumber = i;
                break;
            } else {
                filledLineNumber = 0;
            }
        }
        return filledLineNumber;
    }

    private boolean checkIfAllItemsInArrayHaveFalseValue(boolean[] booleans) {
        for (boolean b : booleans) if (b) return false;
        return true;
    }

    private void populateBooleanHashMapWithBooleanValuesIfEachLinearLayoutIsEmpty() {
        for (int i = 1; i <= integerLinearLayoutHashMap.size(); i++) {
            LinearLayout linearLayout = getLinearLayoutAtPosition(i);
            booleanLinearLayoutHashMap.put(linearLayout, Boolean.TRUE);
        }
    }

    private void initButtons() {
        linear_layout_rotate = findViewById(R.id.linear_layout_rotate);
        linear_layout_left = findViewById(R.id.linear_layout_left);
        linear_layout_right = findViewById(R.id.linear_layout_right);
        linear_layout_down = findViewById(R.id.linear_layout_down);
    }

    private void populateIntegerHashMapWithInitializedLinearLayouts() {
        int counter = 1;
        for (int i = 0; i < 20; i++) {
            for (int j = 1; j <= 10; j++) {
                String name = "linear_layout_" + (i + 1) + "_" + j;
                int id = getResources().getIdentifier(name, "id", getPackageName());
                LinearLayout linearLayout = findViewById(id);
                integerLinearLayoutHashMap.put(counter, linearLayout);
                counter++;
            }
        }
    }
}
