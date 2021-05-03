package com.enerdia.main;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.enerdia.charts.PrecioMedioAnualFinal;
import com.enerdia.charts.PrecioMedioAnualFinalContratacionLibre;
import com.enerdia.charts.PrecioMedioDiario;
import com.enerdia.charts.PrecioMedioMensual;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrecioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrecioFragment extends Fragment {

    private ConstraintLayout precio_fragment_layout;
    private AnimationDrawable animDrawable;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
 /*   private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;*/

    public PrecioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrecioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrecioFragment newInstance(String param1, String param2) {
        PrecioFragment fragment = new PrecioFragment();
   /*     Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    Button button_precio_medio_anual = null;
    Button button_precio_medio_contratacion_libre = null;
    Button button_precio_medio_mensual = null;
    Button button_precio_medio_diario = null;
    View inflatedView = null;

    @Override //CUIDADO AQUÍ PORQUE ESTO LO SAQUÉ DE ESTA PÁGINA  https://stackoverflow.com/questions/17996339/call-of-findviewbyid-in-fragment-to-find-a-button-returns-null
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

        this.inflatedView = inflater.inflate(R.layout.fragment_precio, container, false);

        precio_fragment_layout = (ConstraintLayout)inflatedView.findViewById((R.id.precio_fragment_xml));
        animDrawable = (AnimationDrawable) precio_fragment_layout.getBackground();
        animDrawable.setEnterFadeDuration(10);
        animDrawable.setExitFadeDuration(5000);
        animDrawable.start();

        button_precio_medio_anual = (Button) inflatedView.findViewById(R.id.button_precio_medio_anual);
        button_precio_medio_contratacion_libre = (Button) inflatedView.findViewById(R.id.button_precio_medio_contratacion_libre);
        button_precio_medio_mensual = (Button) inflatedView.findViewById(R.id.button_precio_medio_mensual);
        button_precio_medio_diario = (Button) inflatedView.findViewById(R.id.button_precio_medio_diario);


        if((button_precio_medio_anual == null) | (button_precio_medio_contratacion_libre == null) | (button_precio_medio_mensual == null) | (button_precio_medio_diario == null))
        {
            Log.d("debugCheck", "HeadFrag: button is null");
            return inflatedView;
        }
        button_precio_medio_anual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activity2Intent = new Intent(getContext(), PrecioMedioAnualFinal.class);
                startActivity(activity2Intent);

            }
        });

        button_precio_medio_contratacion_libre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activity2Intent = new Intent(getContext(), PrecioMedioAnualFinalContratacionLibre.class);
                startActivity(activity2Intent);

            }
        });

        button_precio_medio_mensual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activity2Intent = new Intent(getContext(), PrecioMedioMensual.class);
                startActivity(activity2Intent);

            }
        });

        button_precio_medio_diario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activity2Intent = new Intent(getContext(), PrecioMedioDiario.class);
                startActivity(activity2Intent);

            }
        });


        return inflatedView;

    }

}