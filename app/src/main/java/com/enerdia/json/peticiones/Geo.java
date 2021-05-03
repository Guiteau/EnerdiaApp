
package com.enerdia.json.peticiones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geo {

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
    public Geo() {
    }

    /**
     * 
     * @param geoName
     * @param geoId
     */
    public Geo(long geoId, String geoName) {
        super();
        this.geoId = geoId;
        this.geoName = geoName;
    }

    public long getGeoId() {
        return geoId;
    }

    public void setGeoId(long geoId) {
        this.geoId = geoId;
    }

    public Geo withGeoId(long geoId) {
        this.geoId = geoId;
        return this;
    }

    public String getGeoName() {
        return geoName;
    }

    public void setGeoName(String geoName) {
        this.geoName = geoName;
    }

    public Geo withGeoName(String geoName) {
        this.geoName = geoName;
        return this;
    }

}
