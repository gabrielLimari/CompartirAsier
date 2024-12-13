package com.example.fragmnetejerciciotarea_12_12;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment4 extends Fragment implements TareaReceiver  {
    private EditText etTitulo;
    private EditText etDescriocion;

    private ArrayList<Tarea> tareas;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment4.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment4 newInstance(String param1, String param2) {
        Fragment4 fragment = new Fragment4();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_4, container, false);
        tareas = (ArrayList<Tarea>) getArguments().getSerializable("tareas");

        etTitulo  = view.findViewById(R.id.etTitulo);;
        etDescriocion  = view.findViewById(R.id.etDescripcion);

        Button btnAceptar = view.findViewById(R.id.btnAceptar);
        Button btnVolver = view.findViewById(R.id.btCancelar);


        btnAceptar.setOnClickListener(v -> anhadir());

        btnVolver.setOnClickListener(v -> irAfragment(new Fragment1()));

        return  view;

    }


    private void anhadir (){
        if(!etTitulo.getText().toString().isEmpty() &&
                !etDescriocion.getText().toString().isEmpty()){
            tareas.add(new Tarea (etTitulo.getText().toString().trim(),
                    etDescriocion.getText().toString().trim()));
            Toast.makeText(getContext(), "Añadido con éxito", Toast.LENGTH_SHORT).show();
            irAfragment(new Fragment1());
        }else{
            Toast.makeText(getContext(), "Todos los campos deben estar rellenos", Toast.LENGTH_SHORT).show();
        }
    }
    private void irAfragment(Fragment fragment) {
        if (tareas != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("tareas", tareas);
            fragment.setArguments(bundle);
        }

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }



    @Override
    public void receiveObjectTarea(ArrayList<Tarea> tareas) {
        if (tareas != null) {
            this.tareas = tareas;
        }
    }
}