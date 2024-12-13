package com.example.fragmnetejerciciotarea_12_12;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Tarea> tareas;
    Fragment fragment1; //fragmento inciaal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cargarFichero("file.txt");



        if (savedInstanceState == null) {
            fragment1 = new Fragment1();
            Bundle bundle = new Bundle();
            bundle.putSerializable("tareas", tareas);
            fragment1.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment1)
                    .commit();
            ((Fragment1) fragment1).receiveObjectTarea(tareas);
        }
    }

    private void cargarFichero(String nombreFile) {
        File path = getApplicationContext().getFilesDir();
        File file = new File(path, nombreFile);

        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                tareas = (ArrayList<Tarea>) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
                Toast.makeText(this, "Archivo cargado", Toast.LENGTH_SHORT).show();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            tareas.add(new Tarea("Titulo1", "Descripcion1"));
            tareas.add(new Tarea("Titulo2", "Descripcion2"));
            Toast.makeText(this, "Archivo no encontrado, creeando datos predeterminados", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        tareas = ((Fragment1) fragment1).getTareas();
        escribirFichero("file.txt", tareas);
    }

    private void escribirFichero(String nombreFile, ArrayList<Tarea> tareas) {
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path, nombreFile));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tareas);

            objectOutputStream.close();
            fileOutputStream.close();
       //     Toast.makeText(this, "Escrito con exito", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}