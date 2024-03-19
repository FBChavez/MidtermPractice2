package com.example.midtermpractice2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private int[][] colors = new int[3][3];
//    private int[] color_list = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};
    private int[] color_list = {Color.RED, Color.GREEN, Color.BLUE};

    private TextView TVwinner;
    private Button btnRestart;
    private boolean gameEnded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TVwinner = findViewById(R.id.TVwinner);
        btnRestart = findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(this);

        // Initialize buttons and colors
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "btn" + (i + 1) + "_" + (j + 1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
                colors[i][j] = generateRandomColor();
                buttons[i][j].setBackgroundColor(colors[i][j]);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRestart) {
            resetGame();
        } else if (!gameEnded) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (v.getId() == buttons[i][j].getId()) {
                        changeColor(i, j);
                        checkWinCondition();
                        return;
                    }
                }
            }
        }
    }

    private void changeColor(int row, int col) {
        if (row > 0) {
            colors[row - 1][col] = color_list[nextColorIndex(colors[row - 1][col])];
            buttons[row - 1][col].setBackgroundColor(colors[row - 1][col]);
        }
        if (row < 2) {
            colors[row + 1][col] = color_list[nextColorIndex(colors[row + 1][col])];
            buttons[row + 1][col].setBackgroundColor(colors[row + 1][col]);
        }
        if (col > 0) {
            colors[row][col - 1] = color_list[nextColorIndex(colors[row][col - 1])];
            buttons[row][col - 1].setBackgroundColor(colors[row][col - 1]);
        }
        if (col < 2) {
            colors[row][col + 1] = color_list[nextColorIndex(colors[row][col + 1])];
            buttons[row][col + 1].setBackgroundColor(colors[row][col + 1]);
        }
    }

    private int nextColorIndex(int color) {
        for (int i = 0; i < color_list.length; i++) {
            if (color_list[i] == color) {
                return (i + 1) % color_list.length;
            }
        }
        return 0;
    }

    private int generateRandomColor() {
        Random random = new Random();
        int colorIndex = random.nextInt(color_list.length);
        switch (colorIndex) {
            case 0:
                return color_list[0];
            case 1:
                return color_list[1];
//            case 2:
//                return color_list[2];
            default:
                return color_list[2];
        }
    }

    private void checkWinCondition() {
        int firstColor = colors[0][0];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (colors[i][j] != firstColor) {
                    return;
                }
            }
        }
        gameEnded = true;
        TVwinner.setText(R.string.win);
    }

    private void resetGame() {
        gameEnded = false;
        TVwinner.setText("");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                colors[i][j] = generateRandomColor();
                buttons[i][j].setBackgroundColor(colors[i][j]);
            }
        }
    }
}
