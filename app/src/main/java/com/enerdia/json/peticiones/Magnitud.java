
package com.enerdia.json.peticiones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Magnitud {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private long id;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Magnitud() {
    }

    /**
     * 
     * @param name
     * @param id
     */
    public Magnitud(String name, long id) {
        super();
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Magnitud withName(String name) {
        this.name = name;
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Magnitud withId(long id) {
        this.id = id;
        return this;
    }

}
