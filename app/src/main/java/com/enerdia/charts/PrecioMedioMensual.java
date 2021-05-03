package com.enerdia.charts;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.enerdia.commons.ConstanteURL;
import com.enerdia.interfaces.Interface_API_esios_ree;
import com.enerdia.json.peticiones.PeticionPrecioMedioMensual;
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

public class PrecioMedioMensual extends AppCompatActivity {

    private LineChart lineChart;

    private String[] aniosString = {"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022"};
    private String[] mesesString = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    private String[] mesesStringFormato_numero = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String nombreGrafica;
    private String token;

    private String valor_comboYear_inicio;
    private String valor_comboMonth_inicio;
    private String valor_comboYear_fin;
    private String valor_comboMonth_fin;

    private String posicion_combo_month_inicio_string;
    private String posicion_combo_month_fin_string;


    private Spinner spinner_inicio_year;
    private Spinner spinner_fin_year;
    private Spinner spinner_inicio_month;
    private Spinner spinner_fin_month;


    private TextView textView_showBottomResult;

    private List<Value> list_valorRespuesta;

    private int colorAmarillo = Color.parseColor("#FFA64D");
    private int posicionYearInicio_combo = 0;
    private int posicionYearFin_combo = 0;
    private int posicionMonthInicio_combo = 0;
    private int posicionMonthFin_combo = 0;

    private GraficaLineChart graficaLineChart;

    double media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.precio_medio_mensual);

        token = "650f93610a192da6e6f4dcd46b87cc85718c0b5b4594639f1433a844fe4f55dc";

        valor_comboYear_inicio = "2010";
        valor_comboYear_fin = "2011";
        valor_comboMonth_inicio = "Enero";
        valor_comboMonth_fin = "Diciembre";

        posicion_combo_month_inicio_string = "";
        posicion_combo_month_fin_string = "";

        posicionYearInicio_combo = 0;
        posicionYearFin_combo = 0;
        posicionMonthInicio_combo = 0;
        posicionMonthFin_combo = 0;

        this.media = 0;

        setNombreGrafica("Precio Medio Mensual");

        textView_showBottomResult = null;

        this.list_valorRespuesta = new ArrayList();

        textView_showBottomResult = (TextView) findViewById(R.id.textView_showBottomResult);

        spinner_inicio_year = (Spinner) findViewById(R.id.spinner_upper_year);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, aniosString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_inicio_year.setAdapter(adapter);

        spinner_inicio_month = (Spinner) findViewById(R.id.spinner_upper_month);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mesesString);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_inicio_month.setAdapter(adapter2);


        spinner_fin_year = (Spinner) findViewById(R.id.spinner_bottom_year);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, aniosString);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_fin_year.setAdapter(adapter3);

        spinner_fin_month = (Spinner) findViewById(R.id.spinner_bottom_month);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mesesString);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_fin_month.setAdapter(adapter4);

        //llamamos al diagrama de barras y a continuación necesitaremos los datos a insertar en la gráfica

        lineChart = (LineChart) findViewById(R.id.lineChart);

        graficaLineChart = new GraficaLineChart(lineChart, colorAmarillo);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstanteURL.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Interface_API_esios_ree apiService = retrofit.create(Interface_API_esios_ree.class);

        spinner_inicio_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                valor_comboYear_inicio = spinner_inicio_year.getSelectedItem().toString();

                posicionYearInicio_combo = (int) spinner_inicio_year.getSelectedItemId();

                Call<PeticionPrecioMedioMensual> peticionPrecioMedioMensual = apiService.precioMedioMensual(valor_comboYear_inicio + "-" + posicion_combo_month_inicio_string + "-01T00:00:00Z",
                        valor_comboYear_fin + "-" + posicion_combo_month_fin_string + "-31T00:00:00Z","Token token=" + token);

                peticionPrecioMedioMensual.enqueue(new Callback<PeticionPrecioMedioMensual>() {

                    @Override
                    public void onResponse
                            (Call<PeticionPrecioMedioMensual> call, Response<PeticionPrecioMedioMensual> response) {

                        if (response.code() == HttpURLConnection.HTTP_OK) {

                            list_valorRespuesta = response.body().getIndicator().getValues();

                            Log.i(LOG_TAG, response.toString());

                            graficaLineChart.setList_valorRespuesta(list_valorRespuesta);

                            media = graficaLineChart.calcularMedia(list_valorRespuesta);

                            media = Math.floor(media * 100) / 100;

                            graficaLineChart.createChart();

                            if(valor_comboYear_inicio == valor_comboYear_fin && valor_comboMonth_inicio == valor_comboMonth_fin){

                                textView_showBottomResult.setText("PRECIO MEDIO MENSUAL \n\n" + "0 €/MWh");

                            }else{

                                textView_showBottomResult.setText("PRECIO MEDIO MENSUAL \n\n" +  String.valueOf(media) + "€/MWh");

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<PeticionPrecioMedioMensual> call, Throwable t) {

                        Toast.makeText(PrecioMedioMensual.this, "Error en la petición", Toast.LENGTH_SHORT).show();

                        Log.e(LOG_TAG, t.toString());

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_inicio_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                valor_comboMonth_inicio = spinner_inicio_month.getSelectedItem().toString();

                posicionMonthInicio_combo = (int) spinner_inicio_month.getSelectedItemId();

                if(posicionMonthInicio_combo < 9){

                    posicion_combo_month_inicio_string = "0" + String.valueOf(posicionMonthInicio_combo+1);

                } else {

                    posicion_combo_month_inicio_string = String.valueOf(posicionMonthInicio_combo+1);

                }

                Call<PeticionPrecioMedioMensual> peticionPrecioMedioMensual = apiService.precioMedioMensual(valor_comboYear_inicio + "-" + posicion_combo_month_inicio_string + "-01T00:00:00Z",
                        valor_comboYear_fin + "-" + posicion_combo_month_fin_string + "-31T00:00:00Z","Token token=" + token);

                peticionPrecioMedioMensual.enqueue(new Callback<PeticionPrecioMedioMensual>() {

                    @Override
                    public void onResponse
                            (Call<PeticionPrecioMedioMensual> call, Response<PeticionPrecioMedioMensual> response) {

                        if (response.code() == HttpURLConnection.HTTP_OK) {

                            list_valorRespuesta = response.body().getIndicator().getValues();

                            Log.i(LOG_TAG, response.toString());

                            graficaLineChart.setList_valorRespuesta(list_valorRespuesta);

                            media = graficaLineChart.calcularMedia(list_valorRespuesta);

                            media = Math.floor(media * 100) / 100;

                            graficaLineChart.createChart();

                            if(valor_comboYear_inicio == valor_comboYear_fin && valor_comboMonth_inicio == valor_comboMonth_fin){

                                textView_showBottomResult.setText("PRECIO MEDIO MENSUAL \n\n" + "0 €/MWh");

                            }else{

                                textView_showBottomResult.setText("PRECIO MEDIO MENSUAL \n\n" +  String.valueOf(media) + "€/MWh");

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<PeticionPrecioMedioMensual> call, Throwable t) {

                        Toast.makeText(PrecioMedioMensual.this, "Error en la petición", Toast.LENGTH_SHORT).show();

                        Log.e(LOG_TAG, t.toString());

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_fin_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                valor_comboMonth_fin = spinner_fin_month.getSelectedItem().toString();

                posicionMonthFin_combo = (int) spinner_fin_month.getSelectedItemId();

                if(posicionMonthFin_combo < 9){

                    posicion_combo_month_fin_string = "0" + String.valueOf(posicionMonthFin_combo+1);

                } else {

                    posicion_combo_month_fin_string = String.valueOf(posicionMonthFin_combo+1);

                }

                Call<PeticionPrecioMedioMensual> peticionPrecioMedioMensual = apiService.precioMedioMensual(valor_comboYear_inicio + "-" + posicion_combo_month_inicio_string + "-01T00:00:00Z",
                        valor_comboYear_fin + "-" + posicion_combo_month_fin_string + "-31T00:00:00Z","Token token=" + token);

                peticionPrecioMedioMensual.enqueue(new Callback<PeticionPrecioMedioMensual>() {

                    @Override
                    public void onResponse
                            (Call<PeticionPrecioMedioMensual> call, Response<PeticionPrecioMedioMensual> response) {

                        if (response.code() == HttpURLConnection.HTTP_OK) {

                            list_valorRespuesta = response.body().getIndicator().getValues();

                            Log.i(LOG_TAG, response.toString());

                            graficaLineChart.setList_valorRespuesta(list_valorRespuesta);

                            media = graficaLineChart.calcularMedia(list_valorRespuesta);

                            media = Math.floor(media * 100) / 100;

                            graficaLineChart.createChart();

                            if(valor_comboYear_inicio == valor_comboYear_fin && valor_comboMonth_inicio == valor_comboMonth_fin){

                                textView_showBottomResult.setText("PRECIO MEDIO MENSUAL \n\n" + "0 €/MWh");

                            }else{

                                textView_showBottomResult.setText("PRECIO MEDIO MENSUAL \n\n" +  String.valueOf(media) + "€/MWh");

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<PeticionPrecioMedioMensual> call, Throwable t) {

                        Toast.makeText(PrecioMedioMensual.this, "Error en la petición", Toast.LENGTH_SHORT).show();

                        Log.e(LOG_TAG, t.toString());

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_fin_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                valor_comboYear_fin = spinner_fin_year.getSelectedItem().toString();

                posicionYearFin_combo = (int) spinner_fin_year.getSelectedItemId();

                Call<PeticionPrecioMedioMensual> peticionPrecioMedioMensual = apiService.precioMedioMensual(valor_comboYear_inicio + "-" + posicion_combo_month_inicio_string + "-01T00:00:00Z",
                        valor_comboYear_fin + "-" + posicion_combo_month_fin_string + "-31T00:00:00Z","Token token=" + token);

                peticionPrecioMedioMensual.enqueue(new Callback<PeticionPrecioMedioMensual>() {

                    @Override
                    public void onResponse
                            (Call<PeticionPrecioMedioMensual> call, Response<PeticionPrecioMedioMensual> response) {

                        if (response.code() == HttpURLConnection.HTTP_OK) {

                            list_valorRespuesta = response.body().getIndicator().getValues();

                            Log.i(LOG_TAG, response.toString());

                            graficaLineChart.setList_valorRespuesta(list_valorRespuesta);

                            media = graficaLineChart.calcularMedia(list_valorRespuesta);

                            media = Math.floor(media * 100) / 100;

                            graficaLineChart.createChart();

                            if(valor_comboYear_inicio == valor_comboYear_fin && valor_comboMonth_inicio == valor_comboMonth_fin){

                                textView_showBottomResult.setText("PRECIO MEDIO MENSUAL \n\n" + "0 €/MWh");

                            }else{

                                textView_showBottomResult.setText("PRECIO MEDIO MENSUAL \n\n" +  String.valueOf(media) + "€/MWh");

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<PeticionPrecioMedioMensual> call, Throwable t) {

                        Toast.makeText(PrecioMedioMensual.this, "Error en la petición", Toast.LENGTH_SHORT).show();

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
