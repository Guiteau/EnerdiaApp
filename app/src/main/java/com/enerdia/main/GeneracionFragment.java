package com.enerdia.main;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.enerdia.charts.GeneracionDemandaReal;
import com.enerdia.charts.GeneracionDemandaRealTotalGeneracion;
import com.enerdia.charts.GeneracionNoRenovable;
import com.enerdia.charts.GeneracionRenovable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GeneracionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneracionFragment extends Fragment {

    private ConstraintLayout generacion_fragment_layout;
    private AnimationDrawable animDrawable;

/*    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;*/

    public GeneracionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GeneracionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GeneracionFragment newInstance(String param1, String param2) {
        GeneracionFragment fragment = new GeneracionFragment();
 /*       Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    Button button_demanda_real = null;
    Button button_demanda_real_total_generacion = null;
    Button button_generacion_no_renovable = null;
    Button button_generacion_renovable = null;
    View inflatedView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*    if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.inflatedView = inflater.inflate(R.layout.fragment_generacion, container, false);

        generacion_fragment_layout = (ConstraintLayout)inflatedView.findViewById((R.id.generacion_fragment_xml));
        animDrawable = (AnimationDrawable) generacion_fragment_layout.getBackground();
        animDrawable.setEnterFadeDuration(10);
        animDrawable.setExitFadeDuration(5000);
        animDrawable.start();

        button_demanda_real = (Button) inflatedView.findViewById(R.id.button_demanda_real);
        button_demanda_real_total_generacion = (Button) inflatedView.findViewById(R.id.button_demanda_real_total_generacion);
        button_generacion_no_renovable = (Button) inflatedView.findViewById(R.id.button_generacion_no_renovable);
        button_generacion_renovable = (Button) inflatedView.findViewById(R.id.button_generacion_renovable);

        if((button_demanda_real == null) | (button_demanda_real_total_generacion == null) | (button_generacion_no_renovable == null) | (button_generacion_renovable == null))
        {
            Log.d("debugCheck", "HeadFrag: button is null");
            return inflatedView;
        }

        button_demanda_real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activity2Intent = new Intent(getContext(), GeneracionDemandaReal.class);
                startActivity(activity2Intent);

            }
        });

        button_demanda_real_total_generacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activity2Intent = new Intent(getContext(), GeneracionDemandaRealTotalGeneracion.class);
                startActivity(activity2Intent);

            }
        });

        button_generacion_no_renovable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activity2Intent = new Intent(getContext(), GeneracionNoRenovable.class);
                startActivity(activity2Intent);

            }
        });

        button_generacion_renovable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activity2Intent = new Intent(getContext(), GeneracionRenovable.class);
                startActivity(activity2Intent);

            }
        });


        return inflatedView;
    }
}