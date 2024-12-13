package com.example.fragmnetejerciciotarea_12_12;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment  implements TareaReceiver {
    private ArrayList<Tarea> tareas;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
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
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        tareas = (ArrayList<Tarea>) getArguments().getSerializable("tareas");
        LinearLayout containerTareas = view.findViewById(R.id.containerTareas);

        if (tareas != null) {
            containerTareas.removeAllViews();

            for (Tarea tarea : tareas) {
                if (!tarea.isRealizada()) {
                    View tareaView = createTareaView(tarea);
                    containerTareas.addView(tareaView);
                }
            }
        }

        Button btnVolver = view.findViewById(R.id.btnVolverDeP);


        btnVolver.setOnClickListener(v -> irAfragment(new Fragment1()));

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

    @Override
    public void receiveObjectTarea(ArrayList<Tarea> tareas) {
        if (tareas != null) {
            this.tareas = tareas;
        }
    }

    private View createTareaView(Tarea tarea) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View itemView = inflater.inflate(R.layout.tarea, null);

        TextView tvTitulo = itemView.findViewById(R.id.cardTitle);
        TextView tvDescripcion = itemView.findViewById(R.id.cardDescription);

        tvTitulo.setText(tarea.getTitulo());
        tvDescripcion.setText(tarea.getDescripcion());


        itemView.setOnClickListener(view -> {
            tarea.setRealizada(true);
            actualizarTareas();
        });
        return itemView;
    }


    private void actualizarTareas() {
        if (tareas != null) {
            LinearLayout containerTareas = getView().findViewById(R.id.containerTareas);
            containerTareas.removeAllViews();

            for (Tarea tarea : tareas) {
                if (!tarea.isRealizada()) {
                    View tareaView = createTareaView(tarea);
                    containerTareas.addView(tareaView);
                }
            }
        }
    }

    /*private void refreshFragment() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("tareas", tareas);
        Fragment2 fragment = new Fragment2();
        fragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void refreshFragment() {
        Fragment currentFragment = requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (currentFragment != null) {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .detach(currentFragment) // Desvincula el fragmento actual
                    .attach(currentFragment) // Vuelve a vincular el fragmento para recargar la vista
                    .commit();
        }
    }
*/
}