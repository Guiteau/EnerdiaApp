
package com.enerdia.json.peticiones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PeticionGeneracionNoRenovable {

    @SerializedName("indicator")
    @Expose
    private Indicator indicator;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PeticionGeneracionNoRenovable() {
    }

    /**
     * 
     * @param indicator
     */
    public PeticionGeneracionNoRenovable(Indicator indicator) {
        super();
        this.indicator = indicator;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public PeticionGeneracionNoRenovable withIndicator(Indicator indicator) {
        this.indicator = indicator;
        return this;
    }

}
