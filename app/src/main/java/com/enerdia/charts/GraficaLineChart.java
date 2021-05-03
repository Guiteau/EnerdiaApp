package com.enerdia.charts;

import android.graphics.Color;

import com.enerdia.json.peticiones.Value;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class GraficaLineChart {

    private LineChart lineChart;
    private int color;
    private List<Value> list_valorRespuesta;

    public GraficaLineChart(LineChart lineChart, int color) {
        this.lineChart = lineChart;
        this.color = color;
    }

    public void setList_valorRespuesta(List<Value> list_valorRespuesta) {
        this.list_valorRespuesta = list_valorRespuesta;
    }

    public double calcularMedia(List<Value> list_valorRespuesta){

        double media = 0;
        double acumulador = 0;

        for(int i = 0; i < list_valorRespuesta.size(); i++) {

            acumulador += list_valorRespuesta.get(i).getValue();

        }

        media = acumulador/list_valorRespuesta.size()+1;

        return media;
    }

    //aquí recibiremos la gráfica formateada digamos

    private Chart getSameChart(Chart chart, String descripcionGrafica, int textColor, int backgroundColor, int tiempoAnimacion) {

        chart.getDescription().setText(descripcionGrafica);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(backgroundColor);
        chart.animateY(tiempoAnimacion);

        return chart;

    }

    //aquí LE METEMOS LOS DATOS del eje x, y que provienen de la selección del SPINNER

    private ArrayList<Entry> getLineEntries() {

        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < this.list_valorRespuesta.size(); i++) {

            entries.add(new Entry(i, (float) list_valorRespuesta.get(i).getValue())); //el valor de x e y

        }

        return entries;

    }

    private void axisX(XAxis axis) {  //aquí indicamos lo que queramos que se muestre en el eje X (los años String)

        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        axis.setAvoidFirstLastClipping(true);
        axis.setLabelRotationAngle(-90);
        axis.setDrawGridLinesBehindData(true);
        axis.setLabelCount(list_valorRespuesta.size(), true);
        axis.setValueFormatter(new IndexAxisValueFormatter(introducirValoresFechaEjeX()));

    }

    public ArrayList<String> introducirValoresFechaEjeX() {

        ArrayList<String> label = new ArrayList<>();

        for (int i = 0; i < list_valorRespuesta.size(); i++) {

            if(list_valorRespuesta.size() <= 12){

                label.add(list_valorRespuesta.get(i).getDatetimeUtc().substring(0, 16));

            } else if (list_valorRespuesta.size() > 12){

                if(i%3 == 0){

                    label.add(list_valorRespuesta.get(i).getDatetimeUtc().substring(0, 16));

                }else{

                    label.add(i, "");

                }

            }

        }

        return label;

    }

    private void axisLeft(YAxis axis) { //aquí en el eje Y le decimos el valor máximo y mínimo que mostrará el eje

        axis.setSpaceTop(90); //valor máximo mostrado será 70
        axis.setAxisMinimum(0); //valor mostrado mínimo será 30
        axis.setSpaceTop(1f);

    }

    private void axisRight(YAxis axis) { //aquí sería para la parte derecha del eje Y, pero lo único que queremos es que no aparezca

        axis.setEnabled(false);

    }

    //AQUÍ TENGO QUE TENER CUIDADO CON LA VARIABLE nombreGrafica PORQUE ES UN DATO QUE SE RECIBE DEL SERVIDOR Y TAL VEZ PARPADEE

    public void createChart() {

        this.lineChart = (LineChart) getSameChart(this.lineChart, " ", Color.BLACK, Color.WHITE, 300);
        lineChart.setData(getLineData());
        lineChart.invalidate();
        axisX(this.lineChart.getXAxis());
        axisLeft(this.lineChart.getAxisLeft());
        axisRight(lineChart.getAxisRight());

        this.lineChart.setTouchEnabled(true);
        this.lineChart.setDragEnabled(true);
        this.lineChart.setScaleEnabled(false);
        this.lineChart.setPinchZoom(true);

    }

    public DataSet getData(DataSet dataset) {

        dataset.setColors(color);
        dataset.setValueTextSize(10);
        dataset.setValueTextColor(Color.BLACK);

        return dataset;

    }

    // ahora vamos a personalizar específicamente el contenido de la gráfica tomando los datos que configuramos en getData

    private LineData getLineData() {

        LineDataSet lineDataSet = (LineDataSet) getData(new LineDataSet(getLineEntries(), ""));
        lineDataSet.setLineWidth(2.5f);
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);

        LineData lineData = new LineData(lineDataSet);

        lineData.setHighlightEnabled(true);

        return lineData;

    }


}
