
package com.enerdia.json.peticiones;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Indicator {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("composited")
    @Expose
    private boolean composited;
    @SerializedName("step_type")
    @Expose
    private String stepType;
    @SerializedName("disaggregated")
    @Expose
    private boolean disaggregated;
    @SerializedName("magnitud")
    @Expose
    private List<Magnitud> magnitud = null;
    @SerializedName("tiempo")
    @Expose
    private List<Tiempo> tiempo = null;
    @SerializedName("geos")
    @Expose
    private List<Geo> geos = null;
    @SerializedName("values_updated_at")
    @Expose
    private String valuesUpdatedAt;
    @SerializedName("values")
    @Expose
    private List<Value> values = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Indicator() {
    }

    /**
     * 
     * @param composited
     * @param geos
     * @param stepType
     * @param valuesUpdatedAt
     * @param disaggregated
     * @param tiempo
     * @param values
     * @param name
     * @param magnitud
     * @param id
     * @param shortName
     */
    public Indicator(String name, String shortName, long id, boolean composited, String stepType, boolean disaggregated, List<Magnitud> magnitud, List<Tiempo> tiempo, List<Geo> geos, String valuesUpdatedAt, List<Value> values) {
        super();
        this.name = name;
        this.shortName = shortName;
        this.id = id;
        this.composited = composited;
        this.stepType = stepType;
        this.disaggregated = disaggregated;
        this.magnitud = magnitud;
        this.tiempo = tiempo;
        this.geos = geos;
        this.valuesUpdatedAt = valuesUpdatedAt;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Indicator withName(String name) {
        this.name = name;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Indicator withShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Indicator withId(long id) {
        this.id = id;
        return this;
    }

    public boolean isComposited() {
        return composited;
    }

    public void setComposited(boolean composited) {
        this.composited = composited;
    }

    public Indicator withComposited(boolean composited) {
        this.composited = composited;
        return this;
    }

    public String getStepType() {
        return stepType;
    }

    public void setStepType(String stepType) {
        this.stepType = stepType;
    }

    public Indicator withStepType(String stepType) {
        this.stepType = stepType;
        return this;
    }

    public boolean isDisaggregated() {
        return disaggregated;
    }

    public void setDisaggregated(boolean disaggregated) {
        this.disaggregated = disaggregated;
    }

    public Indicator withDisaggregated(boolean disaggregated) {
        this.disaggregated = disaggregated;
        return this;
    }

    public List<Magnitud> getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(List<Magnitud> magnitud) {
        this.magnitud = magnitud;
    }

    public Indicator withMagnitud(List<Magnitud> magnitud) {
        this.magnitud = magnitud;
        return this;
    }

    public List<Tiempo> getTiempo() {
        return tiempo;
    }

    public void setTiempo(List<Tiempo> tiempo) {
        this.tiempo = tiempo;
    }

    public Indicator withTiempo(List<Tiempo> tiempo) {
        this.tiempo = tiempo;
        return this;
    }

    public List<Geo> getGeos() {
        return geos;
    }

    public void setGeos(List<Geo> geos) {
        this.geos = geos;
    }

    public Indicator withGeos(List<Geo> geos) {
        this.geos = geos;
        return this;
    }

    public String getValuesUpdatedAt() {
        return valuesUpdatedAt;
    }

    public void setValuesUpdatedAt(String valuesUpdatedAt) {
        this.valuesUpdatedAt = valuesUpdatedAt;
    }

    public Indicator withValuesUpdatedAt(String valuesUpdatedAt) {
        this.valuesUpdatedAt = valuesUpdatedAt;
        return this;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public Indicator withValues(List<Value> values) {
        this.values = values;
        return this;
    }

}
