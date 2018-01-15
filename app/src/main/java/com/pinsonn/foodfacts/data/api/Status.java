package com.pinsonn.foodfacts.data.api;

import com.google.gson.annotations.SerializedName;
import com.pinsonn.foodfacts.model.Product;

/**
 * Created by Julien on 12/01/2018.
 */
public class Status {
    @SerializedName("status")
    private int status;

    @SerializedName("status_verbose")
    private String statusVerbose;

    @SerializedName("code")
    private long code;

    @SerializedName("product")
    private Product product;

    /**
     * Gets status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Gets status verbose.
     *
     * @return the status verbose
     */
    public String getStatusVerbose() {
        return statusVerbose;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public long getCode() {
        return code;
    }

    /**
     * Gets product.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }
}
