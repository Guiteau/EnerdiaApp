
package com.enerdia.json.peticiones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {

    @SerializedName("value")
    @Expose
    private double value;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("datetime_utc")
    @Expose
    private String datetimeUtc;
    @SerializedName("tz_time")
    @Expose
    private String tzTime;
    @SerializedName("geo_id")
    @Expose
    private long geoId;
    @SerializedName("geo_name")
    @Expose
    private String geoName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Value() {
    }

    /**
     * 
     * @param geoName
     * @param datetime
     * @param tzTime
     * @param geoId
     * @param datetimeUtc
     * @param value
     */
    public Value(double value, String datetime, String datetimeUtc, String tzTime, long geoId, String geoName) {
        super();
        this.value = value;
        this.datetime = datetime;
        this.datetimeUtc = datetimeUtc;
        this.tzTime = tzTime;
        this.geoId = geoId;
        this.geoName = geoName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Value withValue(double value) {
        this.value = value;
        return this;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Value withDatetime(String datetime) {
        this.datetime = datetime;
        return this;
    }

    public String getDatetimeUtc() {
        return datetimeUtc;
    }

    public void setDatetimeUtc(String datetimeUtc) {
        this.datetimeUtc = datetimeUtc;
    }

    public Value withDatetimeUtc(String datetimeUtc) {
        this.datetimeUtc = datetimeUtc;
        return this;
    }

    public String getTzTime() {
        return tzTime;
    }

    public void setTzTime(String tzTime) {
        this.tzTime = tzTime;
    }

    public Value withTzTime(String tzTime) {
        this.tzTime = tzTime;
        return this;
    }

    public long getGeoId() {
        return geoId;
    }

    public void setGeoId(long geoId) {
        this.geoId = geoId;
    }

    public Value withGeoId(long geoId) {
        this.geoId = geoId;
        return this;
    }

    public String getGeoName() {
        return geoName;
    }

    public void setGeoName(String geoName) {
        this.geoName = geoName;
    }

    public Value withGeoName(String geoName) {
        this.geoName = geoName;
        return this;
    }

}
