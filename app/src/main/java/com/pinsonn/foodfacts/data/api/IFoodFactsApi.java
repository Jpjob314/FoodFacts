package com.pinsonn.foodfacts.data.api;

import com.pinsonn.foodfacts.model.Product;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Julien on 15/01/2018.
 */
public interface IFoodFactsApi {
    /**
     * Gets product status.
     *
     * @param barcode the barcode
     * @return the product status
     */
    @GET("product/{barcode}.json")
    Call<Status> getProductStatus(@Path("barcode") String barcode);
}
