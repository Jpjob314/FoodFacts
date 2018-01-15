package com.pinsonn.foodfacts.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Julien on 12/01/2018.
 */
public class Nutriment extends RealmObject {
    @SerializedName("energy")
    private String energy;

    /**
     * Gets energy.
     *
     * @return the energy
     */
    public String getEnergy() {
        return energy;
    }
}
