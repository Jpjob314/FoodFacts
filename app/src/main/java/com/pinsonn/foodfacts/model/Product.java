package com.pinsonn.foodfacts.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Julien on 12/01/2018.
 */
public class Product extends RealmObject {
    @PrimaryKey
    @SerializedName("code")
    private long code;

    @SerializedName("product_name")
    private String name;

    @SerializedName("image_thumb_url")
    private String thumb;

    private String uri;

    @SerializedName("nutriments")
    private Nutriment nutriments;

    @SerializedName("ingredients")
    private RealmList<Ingredient> ingredients;

    @SerializedName("_keywords")
    private RealmList<String> keywords;


    /**
     * Gets barcode.
     *
     * @return the barcode
     */
    public long getBarcode() {
        return code;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets image url.
     *
     * @return the image url
     */
    public String getImageUrl() {
        return thumb;
    }

    /**
     * Gets uri.
     *
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * Gets nutriments.
     *
     * @return the nutriments
     */
    public Nutriment getNutriments() {
        return nutriments;
    }

    /**
     * Gets ingredients.
     *
     * @return the ingredients
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Gets ingredients string.
     *
     * @return the ingredients string
     */
    public String getIngredientsString() {
        String inStr = "";
        if (ingredients != null) {
            for (Ingredient ingredient : ingredients) {
                inStr += ingredient.getText() + "\n";
            }
        }
        return inStr;
    }

    /**
     * Gets keywords.
     *
     * @return the keywords
     */
    public List<String> getKeywords() {
        return keywords;
    }

    public String toString() {
        return String.valueOf(getBarcode());
    }

    /**
     * Sets uri.
     *
     * @param uri the uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(long code) {
        this.code = code;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets thumb.
     *
     * @param thumb the thumb
     */
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    /**
     * Sets nutriments.
     *
     * @param nutriments the nutriments
     */
    public void setNutriments(Nutriment nutriments) {
        this.nutriments = nutriments;
    }

    /**
     * Sets ingredients.
     *
     * @param ingredients the ingredients
     */
    public void setIngredients(RealmList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Sets keywords.
     *
     * @param keywords the keywords
     */
    public void setKeywords(RealmList<String> keywords) {
        this.keywords = keywords;
    }
}
