package com.pinsonn.foodfacts.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Julien on 12/01/2018.
 */
public class Ingredient extends RealmObject {
    @SerializedName("id")
    private String id;

    @SerializedName("text")
    private String text;

    @SerializedName("rank")
    private int rank;

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Gets rank.
     *
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    public String toString() {
        return getText();
    }
}
