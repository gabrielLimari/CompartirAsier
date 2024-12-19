package com.example.sudoku;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class Main extends AppCompatActivity {
    private GridLayout sudokuGrid;
    private static boolean campoCoindice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sudokuGrid = findViewById(R.id.sudokuGrid);
        initializeGrid();
    }

    private void initializeGrid() {
        int totalCells = 81;
        ArrayList<Cell> tablero = new ArrayList<>();
        int margen = 4;

        for (int i = 0; i < totalCells; i++) {
            EditText celdaCampoEt = new EditText(this);
            int fila = i / 9;
            int columna = i % 9;
            Cell newCell = new Cell(fila, columna, celdaCampoEt);

            celdaCampoEt.setGravity(Gravity.CENTER);
            celdaCampoEt.setBackgroundColor(android.graphics.Color.WHITE);
            celdaCampoEt.setTextSize(18);
            celdaCampoEt.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);


            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.rowSpec = GridLayout.spec(fila, 1f);
            params.columnSpec = GridLayout.spec(columna, 1f);


            // Ajusta márgenes para solo mostrar líneas internas
            int topMargin = (fila == 0) || (fila == 3) || (fila == 6) ? margen : 0;    //1º,4 y 7º fila con margen superior
            int bottomMargin = (fila == 8) ? margen : 0;   //Ultima fila fila con margen superior
            int rightMargin = (columna == 2) || (columna == 5) || (columna == 8) ? margen : 0;
            int leftMargin = (columna == 0) ? margen : 0;

            params.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

            celdaCampoEt.setLayoutParams(params);

            sudokuGrid.addView(celdaCampoEt);
            tablero.add(newCell);
            if (!campoCoindice) {
                logicaCelda(tablero, newCell);
            } else {
                if (newCell.isCoincide()) {
                    logicaCelda(tablero, newCell);
                } else {
                    newCell.getEditText().setFocusable(false);
                    newCell.getEditText().setClickable(false);
                    Log.d("Prieba9", "Entra");
                }
            }

        }
        rellenarTablero(tablero);


    }

    private void rellenarTablero(ArrayList<Cell> tablero) {
        Random rand = new Random();
        int nCeldasRellenas = 0;

        while (nCeldasRellenas < 20) {
            int fila = rand.nextInt(9);
            int columna = rand.nextInt(9);

            for (Cell celda : tablero) {
                if (celda.getFila() == fila && celda.getColumna() == columna) {
                    if (celda.getEditText().getText().toString().isEmpty()) {
                        int num = rand.nextInt(9) + 1;

                        celda.getEditText().setText(String.valueOf(num));
                        celda.getEditText().setFocusable(false);
                        celda.getEditText().setClickable(false);
                        celda.setCoincide(true);

                        celda.getEditText().setBackgroundColor(Color.LTGRAY);

                        nCeldasRellenas++;
                    }
                    break;
                }
            }
        }
    }

/*
    // Función para verificar si el número ya existe en la fila
    public static boolean coindiceEnLinea(ArrayList<Cell> tablero, Cell celda) {
        for (int i = 0; i < tablero.size(); i++) {
            if (tablero.get(i).getColumna() == celda.getColumna()) {
                continue; // saltamos al siguiente elemento
            }

            //Si coincide la fila del campo selecciodado con la fila de la celda
            if (tablero.get(i).getFila() == celda.getFila()) {
                //Comprobamos que los valroes esten rellenos y en caso de que si comparamos si el valor ya exite previamente
                if (!tablero.get(i).getEditText().getText().toString().isEmpty() &&
                        Integer.parseInt(tablero.get(i).getEditText().getText().toString()) ==
                                Integer.parseInt(celda.getEditText().getText().toString())) {
                    return true;
                }
            }
        }
        return false;
    }*/

    // Función para verificar si el número ya existe en la columna
    public static void coincide(ArrayList<Cell> tablero, Cell celda) {
        for (int i = 0; i < tablero.size(); i++) {


            if (tablero.get(i).getFila() == celda.getFila() || tablero.get(i).getColumna() == celda.getColumna()) {
                tablero.get(i).getEditText().setBackgroundColor(Color.BLUE);
                if (!tablero.get(i).getEditText().getText().toString().isEmpty() &&
                        Integer.parseInt(tablero.get(i).getEditText().getText().toString()) ==
                                Integer.parseInt(celda.getEditText().getText().toString())) {
                    tablero.get(i).getEditText().setBackgroundColor(Color.RED);
                }
            } else {
                tablero.get(i).getEditText().setBackgroundColor(Color.WHITE);

            }

        }
    }

    public void logicaCelda(ArrayList<Cell> tablero, Cell celda) {
        // Agregar un TextWatcher para controlar la edición

        celda.getEditText().addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // No se requiere accion aquí
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String text = charSequence.toString();

                if (!text.isEmpty()) {
                    // Si no es un número del 1 al 9, lo limpiamos
                    if (!text.matches("[1-9]")) {
                        celda.getEditText().setText("");
                    } else {
                        int num = Integer.parseInt(text);
                        coincide(tablero, celda);
                    }
                }
            }
            @Override
            public void afterTextChanged(android.text.Editable editable) {
                // No se requiere accion aquí
            }
        });
    }

    public static boolean solveSudoku(int[][] grid) {
        // Iterate through all cellsS
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) { // If cell is empty
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(grid, i, j, num)) {
                            grid[i][j] = num; // Try a number
                            if (solveSudoku(grid)) {
                                return true; // Continue if valid
                            }
                            grid[i][j] = 0; // Backtrack if needed
                        }
                    }
                    return false; // No valid number found, backtrack
                }
            }
        }
        return true; // Solved
    }

    public static boolean isValid(int[][] grid, int row, int col, int num) {
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;

        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num || grid[i][col] == num ||
                    grid[boxRowStart + i / 3][boxColStart + i % 3] == num) {
                return false;
            }
        }
        return true;
    }

    public static void generateSudoku(int[][] grid) {
        // Generate a solved Sudoku
        solveSudoku(grid);

        // Remove cells randomly to create the puzzle
        Random rand = new Random();
        for (int i = 0; i < 40; i++) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            grid[row][col] = 0; // Empty cell
        }
    }



}
