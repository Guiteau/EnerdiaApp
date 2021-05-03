package com.enerdia.charts;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.enerdia.commons.ConstanteURL;
import com.enerdia.interfaces.Interface_API_esios_ree;
import com.enerdia.json.peticiones.PeticionGeneracionNoRenovable;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

public class GeneracionNoRenovable extends AppCompatActivity implements View.OnClickListener{

    private Button button_fecha_inicio;
    private Button button_fecha_fin;

    private EditText editTextDate_inicio;
    private EditText editTextDate_fin;

    private Spinner spinner_hora_inicio;
    private Spinner spinner_hora_fin;
    private Spinner spinner_minuto_inicio;
    private Spinner spinner_minuto_fin;

    private LineChart lineChart;
    private String[] horasString = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "21", "20", "22", "23"};
    private String[] minutosInicioString = {"00", "10", "20", "30", "40", "50"};
    private String[] minutosFinString = {"00", "10", "20", "30", "40", "50"};

    private String hora_inicio = "";
    private String hora_fin = "";
    private String minuto_inicio = "";
    private String minuto_fin = "";

    private int dia_inicio, mes_inicio, anio_inicio;
    private int dia_fin, mes_fin, anio_fin;

    private TextView textView_showBottomResult;

    private List<Value> list_valorRespuesta;

    private int colorAmarillo = Color.parseColor("#FFA64D");

    private String token;
    double media;

    private String anioCogidoDelCombo;

    private GraficaLineChart graficaLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generacion_demanda_real);

        token = "650f93610a192da6e6f4dcd46b87cc85718c0b5b4594639f1433a844fe4f55dc";

        this.media = 0;

        this.list_valorRespuesta = new ArrayList();

        button_fecha_inicio = (Button) findViewById(R.id.button_fecha_inicio);
        button_fecha_fin = (Button) findViewById(R.id.button_fecha_fin);

        button_fecha_inicio.setOnClickListener(this);
        button_fecha_fin.setOnClickListener(this);

        textView_showBottomResult = (TextView) findViewById(R.id.textView_showBottomResult);

        textView_showBottomResult.setText("GENERACIÓN NO RENOVABLE \n\n" + "MW");

        editTextDate_inicio = (EditText) findViewById(R.id.editTextDate_inicio);
        editTextDate_fin = (EditText) findViewById(R.id.editTextDate_fin);

        editTextDate_inicio.setText("");
        editTextDate_fin.setText("");

        //llamamos al diagrama de barras y a continuación necesitaremos los datos a insertar en la gráfica

        lineChart = (LineChart) findViewById(R.id.lineChart);

        graficaLineChart = new GraficaLineChart(lineChart, colorAmarillo);

        spinner_hora_inicio = (Spinner) findViewById(R.id.spinner_hora_inicio);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, horasString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_hora_inicio.setAdapter(adapter);

        spinner_hora_fin = (Spinner) findViewById(R.id.spinner_hora_fin);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, horasString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_hora_fin.setAdapter(adapter1);

        spinner_minuto_inicio = (Spinner) findViewById(R.id.spinner_minuto_inicio);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, minutosInicioString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_minuto_inicio.setAdapter(adapter2);

        spinner_minuto_fin = (Spinner) findViewById(R.id.spinner_minuto_fin);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, minutosFinString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_minuto_fin.setAdapter(adapter3);

        spinner_hora_inicio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                hora_inicio  = spinner_hora_inicio.getSelectedItem().toString();

                if(!editTextDate_fin.getText().toString().matches("") && !editTextDate_inicio.getText().toString().matches("")){

                    Call<PeticionGeneracionNoRenovable> peticionGeneracionNoRenovable = apiService.generacionTiempoRealNoRenovables(String.valueOf(anio_inicio) + "-" +
                                    String.valueOf(mes_inicio) + "-" + String.valueOf(dia_inicio) + "T" + String.valueOf(hora_inicio) + ":" + minuto_inicio +":00Z",
                            String.valueOf(anio_fin) + "-" + String.valueOf(mes_fin) + "-" + String.valueOf(dia_fin) + "T" + String.valueOf(hora_fin) + ":" + minuto_fin +":00Z", "month",
                            "Token token=" + token);

                    peticionGeneracionNoRenovable.enqueue(new Callback<PeticionGeneracionNoRenovable>() {

                        @Override
                        public void onResponse
                                (Call<PeticionGeneracionNoRenovable> call, Response<PeticionGeneracionNoRenovable> response) {

                            if (response.code() == HttpURLConnection.HTTP_OK) {

                                list_valorRespuesta = response.body().getIndicator().getValues();

                                Log.i(LOG_TAG, response.toString());

                                System.out.println("El tamaño de la lista es " + list_valorRespuesta.size());

                                graficaLineChart.setList_valorRespuesta(list_valorRespuesta);

                                media = graficaLineChart.calcularMedia(list_valorRespuesta);

                                media = Math.floor(media * 100) / 100;

                                graficaLineChart.createChart();

                                if(list_valorRespuesta.size() == 0){

                                    Toast.makeText(GeneracionNoRenovable.this, "No hay datos disponibles para esas fechas", Toast.LENGTH_SHORT).show();

                                }else{

                                    textView_showBottomResult.setText("GENERACIÓN NO RENOVABLE \n\n" + String.valueOf(media) + " MW");

                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<PeticionGeneracionNoRenovable> call, Throwable t) {

                            Toast.makeText(GeneracionNoRenovable.this, "Error en la petición. El mercado diario genera grandes cantidades de datos. Procura elegir franjas cortas de tiempo", Toast.LENGTH_SHORT).show();

                            Log.e(LOG_TAG, t.toString());

                        }
                    });

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_hora_fin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                hora_fin  = spinner_hora_fin.getSelectedItem().toString();

                if(!editTextDate_fin.getText().toString().matches("") && !editTextDate_inicio.getText().toString().matches("")){

                    Call<PeticionGeneracionNoRenovable> peticionGeneracionNoRenovable = apiService.generacionTiempoRealNoRenovables(String.valueOf(anio_inicio) + "-" +
                                    String.valueOf(mes_inicio) + "-" + String.valueOf(dia_inicio) + "T" + String.valueOf(hora_inicio) + ":" + minuto_inicio +":00Z",
                            String.valueOf(anio_fin) + "-" + String.valueOf(mes_fin) + "-" + String.valueOf(dia_fin) + "T" + String.valueOf(hora_fin) + ":" + minuto_fin +":00Z", "month",
                            "Token token=" + token);

                    peticionGeneracionNoRenovable.enqueue(new Callback<PeticionGeneracionNoRenovable>() {

                        @Override
                        public void onResponse
                                (Call<PeticionGeneracionNoRenovable> call, Response<PeticionGeneracionNoRenovable> response) {

                            if (response.code() == HttpURLConnection.HTTP_OK) {

                                list_valorRespuesta = response.body().getIndicator().getValues();

                                Log.i(LOG_TAG, response.toString());

                                System.out.println("El tamaño de la lista es " + list_valorRespuesta.size());

                                graficaLineChart.setList_valorRespuesta(list_valorRespuesta);

                                media = graficaLineChart.calcularMedia(list_valorRespuesta);

                                media = Math.floor(media * 100) / 100;

                                graficaLineChart.createChart();

                                if(list_valorRespuesta.size() == 0){

                                    Toast.makeText(GeneracionNoRenovable.this, "No hay datos disponibles para esas fechas", Toast.LENGTH_SHORT).show();

                                }else{

                                    textView_showBottomResult.setText("GENERACIÓN NO RENOVABLE \n\n" + String.valueOf(media) + " MW");

                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<PeticionGeneracionNoRenovable> call, Throwable t) {

                            Toast.makeText(GeneracionNoRenovable.this, "Error en la petición. El mercado diario genera grandes cantidades de datos. Procura elegir franjas cortas de tiempo", Toast.LENGTH_SHORT).show();

                            Log.e(LOG_TAG, t.toString());

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /////////////////////////////////////////// MINUTO INICIO

        spinner_minuto_inicio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                minuto_inicio  = spinner_minuto_inicio.getSelectedItem().toString();

                if(!editTextDate_fin.getText().toString().matches("") && !editTextDate_inicio.getText().toString().matches("")){

                    Call<PeticionGeneracionNoRenovable> peticionGeneracionNoRenovable = apiService.generacionTiempoRealNoRenovables(String.valueOf(anio_inicio) + "-" +
                                    String.valueOf(mes_inicio) + "-" + String.valueOf(dia_inicio) + "T" + String.valueOf(hora_inicio) + ":" + minuto_inicio +":00Z",
                            String.valueOf(anio_fin) + "-" + String.valueOf(mes_fin) + "-" + String.valueOf(dia_fin) + "T" + String.valueOf(hora_fin) + ":" + minuto_fin +":00Z", "month",
                            "Token token=" + token);

                    peticionGeneracionNoRenovable.enqueue(new Callback<PeticionGeneracionNoRenovable>() {

                        @Override
                        public void onResponse
                                (Call<PeticionGeneracionNoRenovable> call, Response<PeticionGeneracionNoRenovable> response) {

                            if (response.code() == HttpURLConnection.HTTP_OK) {

                                list_valorRespuesta = response.body().getIndicator().getValues();

                                Log.i(LOG_TAG, response.toString());

                                System.out.println("El tamaño de la lista es " + list_valorRespuesta.size());

                                graficaLineChart.setList_valorRespuesta(list_valorRespuesta);

                                media = graficaLineChart.calcularMedia(list_valorRespuesta);

                                media = Math.floor(media * 100) / 100;

                                graficaLineChart.createChart();

                                if(list_valorRespuesta.size() == 0){

                                    Toast.makeText(GeneracionNoRenovable.this, "No hay datos disponibles para esas fechas", Toast.LENGTH_SHORT).show();

                                }else{

                                    textView_showBottomResult.setText("GENERACIÓN NO RENOVABLE \n\n" + String.valueOf(media) + " MW");

                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<PeticionGeneracionNoRenovable> call, Throwable t) {

                            Toast.makeText(GeneracionNoRenovable.this, "Error en la petición. El mercado diario genera grandes cantidades de datos. Procura elegir franjas cortas de tiempo", Toast.LENGTH_SHORT).show();

                            Log.e(LOG_TAG, t.toString());

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /////////////////////////////////////////// MINUTO FIN

        spinner_minuto_fin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                minuto_fin  = spinner_minuto_fin.getSelectedItem().toString();

                if(!editTextDate_fin.getText().toString().matches("") && !editTextDate_inicio.getText().toString().matches("")){

                    Call<PeticionGeneracionNoRenovable> peticionGeneracionNoRenovable = apiService.generacionTiempoRealNoRenovables(String.valueOf(anio_inicio) + "-" +
                                    String.valueOf(mes_inicio) + "-" + String.valueOf(dia_inicio) + "T" + String.valueOf(hora_inicio) + ":" + minuto_inicio +":00Z",
                            String.valueOf(anio_fin) + "-" + String.valueOf(mes_fin) + "-" + String.valueOf(dia_fin) + "T" + String.valueOf(hora_fin) + ":" + minuto_fin +":00Z", "month",
                            "Token token=" + token);

                    peticionGeneracionNoRenovable.enqueue(new Callback<PeticionGeneracionNoRenovable>() {

                        @Override
                        public void onResponse
                                (Call<PeticionGeneracionNoRenovable> call, Response<PeticionGeneracionNoRenovable> response) {

                            if (response.code() == HttpURLConnection.HTTP_OK) {

                                list_valorRespuesta = response.body().getIndicator().getValues();

                                Log.i(LOG_TAG, response.toString());

                                System.out.println("El tamaño de la lista es " + list_valorRespuesta.size());

                                graficaLineChart.setList_valorRespuesta(list_valorRespuesta);

                                media = graficaLineChart.calcularMedia(list_valorRespuesta);

                                media = Math.floor(media * 100) / 100;

                                graficaLineChart.createChart();

                                if(list_valorRespuesta.size() == 0){

                                    Toast.makeText(GeneracionNoRenovable.this, "No hay datos disponibles para esas fechas", Toast.LENGTH_SHORT).show();

                                }else{

                                    textView_showBottomResult.setText("GENERACIÓN NO RENOVABLE \n\n" + String.valueOf(media) + " MW");

                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<PeticionGeneracionNoRenovable> call, Throwable t) {

                            Toast.makeText(GeneracionNoRenovable.this, "Error en la petición. El mercado diario genera grandes cantidades de datos. Procura elegir franjas cortas de tiempo", Toast.LENGTH_SHORT).show();

                            Log.e(LOG_TAG, t.toString());

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstanteURL.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Interface_API_esios_ree apiService = retrofit.create(Interface_API_esios_ree.class);

    @Override
    public void onClick(View v) {

        if (v == button_fecha_inicio) {

            final Calendar calendar_inicio = new GregorianCalendar(2010, Calendar.JANUARY, 1);

            DatePickerDialog datePickerDialog_inicio = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    editTextDate_inicio.setText(dayOfMonth+"/"+(month+1)+"/"+year);

                    dia_inicio = dayOfMonth;
                    mes_inicio = month+1;
                    anio_inicio = year;

                    if(!editTextDate_fin.getText().toString().matches((""))){

                        Call<PeticionGeneracionNoRenovable> peticionGeneracionNoRenovable = apiService.generacionTiempoRealNoRenovables(String.valueOf(anio_inicio) + "-" +
                                        String.valueOf(mes_inicio) + "-" + String.valueOf(dia_inicio) + "T" + String.valueOf(hora_inicio) + ":" + minuto_inicio +":00Z",
                                String.valueOf(anio_fin) + "-" + String.valueOf(mes_fin) + "-" + String.valueOf(dia_fin) + "T" + String.valueOf(hora_fin) + ":" + minuto_fin +":00Z", "month",
                                "Token token=" + token);

                        peticionGeneracionNoRenovable.enqueue(new Callback<PeticionGeneracionNoRenovable>() {

                            @Override
                            public void onResponse
                                    (Call<PeticionGeneracionNoRenovable> call, Response<PeticionGeneracionNoRenovable> response) {

                                if (response.code() == HttpURLConnection.HTTP_OK) {

                                    list_valorRespuesta = response.body().getIndicator().getValues();

                                    Log.i(LOG_TAG, response.toString());

                                    System.out.println("El tamaño de la lista es " + list_valorRespuesta.size());

                                    graficaLineChart.setList_valorRespuesta(list_valorRespuesta);

                                    media = graficaLineChart.calcularMedia(list_valorRespuesta);

                                    media = Math.floor(media * 100) / 100;

                                    graficaLineChart.createChart();

                                    if(list_valorRespuesta.size() == 0){

                                        Toast.makeText(GeneracionNoRenovable.this, "No hay datos disponibles para esas fechas", Toast.LENGTH_SHORT).show();

                                    }else{

                                        textView_showBottomResult.setText("GENERACIÓN NO RENOVABLE \n\n" + String.valueOf(media) + " MW");

                                    }

                                }

                            }

                            @Override
                            public void onFailure(Call<PeticionGeneracionNoRenovable> call, Throwable t) {

                                Toast.makeText(GeneracionNoRenovable.this, "Error en la petición. El mercado diario genera grandes cantidades de datos. Procura elegir franjas cortas de tiempo", Toast.LENGTH_SHORT).show();

                                Log.e(LOG_TAG, t.toString());

                            }
                        });
                    }
                }
            }
                    , anio_inicio, mes_inicio, dia_inicio);

            datePickerDialog_inicio.getWindow().setBackgroundDrawable(new ColorDrawable((colorAmarillo)));
            datePickerDialog_inicio.getDatePicker().setMinDate(calendar_inicio.getTimeInMillis());
            datePickerDialog_inicio.show();

        }

        if (v == button_fecha_fin) {

            //    final Calendar calendar_fin = Calendar.getInstance();

            final Calendar calendar_fin = new GregorianCalendar(2010, Calendar.JANUARY, 1);

            DatePickerDialog datePickerDialog_fin = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    editTextDate_fin.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                    dia_fin = dayOfMonth;
                    mes_fin = month+1;
                    anio_fin = year;

                    if(!editTextDate_inicio.getText().toString().matches((""))){

                        Call<PeticionGeneracionNoRenovable> peticionGeneracionNoRenovable = apiService.generacionTiempoRealNoRenovables(String.valueOf(anio_inicio) + "-" +
                                        String.valueOf(mes_inicio) + "-" + String.valueOf(dia_inicio) + "T" + String.valueOf(hora_inicio) + ":" + minuto_inicio +":00Z",
                                String.valueOf(anio_fin) + "-" + String.valueOf(mes_fin) + "-" + String.valueOf(dia_fin) + "T" + String.valueOf(hora_fin) + ":" + minuto_fin +":00Z", "month",
                                "Token token=" + token);

                        peticionGeneracionNoRenovable.enqueue(new Callback<PeticionGeneracionNoRenovable>() {

                            @Override
                            public void onResponse
                                    (Call<PeticionGeneracionNoRenovable> call, Response<PeticionGeneracionNoRenovable> response) {

                                if (response.code() == HttpURLConnection.HTTP_OK) {

                                    list_valorRespuesta = response.body().getIndicator().getValues();

                                    Log.i(LOG_TAG, response.toString());

                                    System.out.println("El tamaño de la lista es " + list_valorRespuesta.size());

                                    graficaLineChart.setList_valorRespuesta(list_valorRespuesta);

                                    media = graficaLineChart.calcularMedia(list_valorRespuesta);

                                    media = Math.floor(media * 100) / 100;

                                    graficaLineChart.createChart();

                                    if(list_valorRespuesta.size() == 0){

                                        Toast.makeText(GeneracionNoRenovable.this, "No hay datos disponibles para esas fechas", Toast.LENGTH_SHORT).show();

                                    }else{

                                        textView_showBottomResult.setText("GENERACIÓN NO RENOVABLE \n\n" + String.valueOf(media) + " MW");

                                    }

                                }

                            }

                            @Override
                            public void onFailure(Call<PeticionGeneracionNoRenovable> call, Throwable t) {

                                Toast.makeText(GeneracionNoRenovable.this, "Error en la petición. El mercado diario genera grandes cantidades de datos. Procura elegir franjas cortas de tiempo", Toast.LENGTH_SHORT).show();

                                Log.e(LOG_TAG, t.toString());

                            }
                        });
                    }

                }
            }
                    , anio_fin, mes_fin, dia_fin);

            datePickerDialog_fin.getWindow().setBackgroundDrawable(new ColorDrawable((colorAmarillo)));
            datePickerDialog_fin.getDatePicker().setMinDate(calendar_fin.getTimeInMillis());
            datePickerDialog_fin.show();

        }

    }
}
