package com.example.fragmnetejerciciotarea_12_12;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment implements TareaReceiver {
    private ArrayList<Tarea> tareas;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
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
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        tareas = (ArrayList<Tarea>) getArguments().getSerializable("tareas");
        Log.d("Tareas", tareas.toString());

        Button btnPendientes = view.findViewById(R.id.btnPendientes);
        Button btnRealizdas = view.findViewById(R.id.btnRealizadas);
        Button btnAnhadir = view.findViewById(R.id.btnNueva);

        btnPendientes.setOnClickListener(v -> irAfragment(new Fragment2()));
        btnRealizdas.setOnClickListener(v -> irAfragment(new Fragment3()));
        btnAnhadir.setOnClickListener(v -> irAfragment(new Fragment4()));

        return  view;
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

    public ArrayList<Tarea> getTareas(){
        return  tareas;

    }
    @Override
    public void receiveObjectTarea(ArrayList<Tarea> tareas) {
        if (tareas != null) {
            this.tareas = tareas;
        }
    }
}