package com.example.sudoku;

import android.widget.EditText;

public class Cell {
    private int fila;
    private int columna;
    private EditText editText;
    private boolean coincide = false;

    public Cell(int fila, int columna, EditText editText) {
        this.fila = fila;
        this.columna = columna;
        this.editText = editText;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public EditText getEditText() {
        return editText;
    }

    public boolean isCoincide() {
        return coincide;
    }

    public void setCoincide(boolean coincide) {
        this.coincide = coincide;
    }
}
