package com.enerdia.charts;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.enerdia.commons.ConstanteURL;
import com.enerdia.interfaces.Interface_API_esios_ree;
import com.enerdia.json.peticiones.PeticionPrecioMedioAnualFinal;
import com.enerdia.json.peticiones.Value;
import com.enerdia.main.R;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

public class PrecioMedioAnualFinal extends AppCompatActivity {

    private LineChart lineChart;
    private String[] aniosString = {"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022"};
    private Spinner spinner_year;

    private String nombreGrafica;
    private TextView textView_showBottomResult;

    private List<Value> list_valorRespuesta;

    private int colorAmarillo = Color.parseColor("#FFA64D");

    private String token;
    double media;

    private GraficaLineChart graficaLineChart;

    private String anioCogidoDelCombo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.precio_medio_anual_final); //esto aquí está cambiado porque se me liaba con el constructor recibiendo parámetros

        token = "650f93610a192da6e6f4dcd46b87cc85718c0b5b4594639f1433a844fe4f55dc";

        anioCogidoDelCombo = "2010";

        this.media = 0;

        setNombreGrafica("Precio Medio Anual Final");

        textView_showBottomResult = null;

        this.list_valorRespuesta = new ArrayList();

        textView_showBottomResult = (TextView) findViewById(R.id.textView_showBottomResult);

        //llamamos al diagrama de barras y a continuación necesitaremos los datos a insertar en la gráfica

        lineChart = (LineChart) findViewById(R.id.lineChart);

        graficaLineChart = new GraficaLineChart(lineChart, colorAmarillo);

        spinner_year = (Spinner) findViewById(R.id.spinner_year);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, aniosString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstanteURL.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Interface_API_esios_ree apiService = retrofit.create(Interface_API_esios_ree.class);

        //// A Q U Í  E S  D O N D E  E S T A R Í A  E L  L I S T E N E R  D E L  C O M B O  ///

        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                anioCogidoDelCombo = spinner_year.getSelectedItem().toString();

                Call<PeticionPrecioMedioAnualFinal> peticionPrecioMedioAnualFinal = apiService.precioMedioAnualFinal("?start_date=2010-01-01T00:00:00Z",
                        anioCogidoDelCombo + "-12-31T00:00:00Z",
                        "Token token=" + token);

                peticionPrecioMedioAnualFinal.enqueue(new Callback<PeticionPrecioMedioAnualFinal>() {

                    @Override
                    public void onResponse
                            (Call<PeticionPrecioMedioAnualFinal> call, Response<PeticionPrecioMedioAnualFinal> response) {

                        if (response.code() == HttpURLConnection.HTTP_OK) {

                            list_valorRespuesta = response.body().getIndicator().getValues();

                            Log.i(LOG_TAG, response.toString());

                            System.out.println("El tamaño de la lista es " + list_valorRespuesta.size());

                            graficaLineChart.setList_valorRespuesta(list_valorRespuesta);

                            media = graficaLineChart.calcularMedia(list_valorRespuesta);

                            media = Math.floor(media * 100) / 100;

                            graficaLineChart.createChart();

                            if(spinner_year.getSelectedItem().toString() == "2010"){

                                textView_showBottomResult.setText("PRECIO MEDIO ANUAL FINAL \n\n" + "0 €/MWh");

                            }else{

                                textView_showBottomResult.setText("PRECIO MEDIO ANUAL FINAL \n\n" +  String.valueOf(media) + "€/MWh");

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<PeticionPrecioMedioAnualFinal> call, Throwable t) {

                        Toast.makeText(PrecioMedioAnualFinal.this, "Error en la petición", Toast.LENGTH_SHORT).show();

                        Log.e(LOG_TAG, t.toString());

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public String getNombreGrafica() {
        return nombreGrafica;
    }

    public void setNombreGrafica(String nombreGrafica) {
        this.nombreGrafica = nombreGrafica;
    }

}