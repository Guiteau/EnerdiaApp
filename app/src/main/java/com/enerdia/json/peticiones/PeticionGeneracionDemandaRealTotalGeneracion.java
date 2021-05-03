
package com.enerdia.json.peticiones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PeticionGeneracionDemandaRealTotalGeneracion {

    @SerializedName("indicator")
    @Expose
    private Indicator indicator;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PeticionGeneracionDemandaRealTotalGeneracion() {
    }

    /**
     * 
     * @param indicator
     */
    public PeticionGeneracionDemandaRealTotalGeneracion(Indicator indicator) {
        super();
        this.indicator = indicator;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public PeticionGeneracionDemandaRealTotalGeneracion withIndicator(Indicator indicator) {
        this.indicator = indicator;
        return this;
    }

}
