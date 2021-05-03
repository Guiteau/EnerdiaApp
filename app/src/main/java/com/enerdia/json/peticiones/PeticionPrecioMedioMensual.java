
package com.enerdia.json.peticiones;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PeticionPrecioMedioMensual implements Serializable
{

    @SerializedName("indicator")
    @Expose
    private Indicator indicator;
    private final static long serialVersionUID = 6684131448132476930L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PeticionPrecioMedioMensual() {
    }

    /**
     * 
     * @param indicator
     */
    public PeticionPrecioMedioMensual(Indicator indicator) {
        super();
        this.indicator = indicator;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public PeticionPrecioMedioMensual withIndicator(Indicator indicator) {
        this.indicator = indicator;
        return this;
    }

}
