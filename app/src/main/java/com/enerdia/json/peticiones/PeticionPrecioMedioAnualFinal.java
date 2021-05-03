
package com.enerdia.json.peticiones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PeticionPrecioMedioAnualFinal {

    @SerializedName("indicator")
    @Expose
    private Indicator indicator;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PeticionPrecioMedioAnualFinal() {
    }

    /**
     * 
     * @param indicator
     */
    public PeticionPrecioMedioAnualFinal(Indicator indicator) {
        super();
        this.indicator = indicator;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public PeticionPrecioMedioAnualFinal withIndicator(Indicator indicator) {
        this.indicator = indicator;
        return this;
    }

}
