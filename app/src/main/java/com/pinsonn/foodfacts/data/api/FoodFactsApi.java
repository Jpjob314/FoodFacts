package com.pinsonn.foodfacts.data.api;

import android.util.Log;

import com.pinsonn.foodfacts.model.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Julien on 15/01/2018.
 */
public class FoodFactsApi {
    /**
     * The constant BASE_URL.
     */
    public static final String BASE_URL = "https://world.openfoodfacts.org/api/v0/";

    private IFoodFactsApi api;

    /**
     * Instantiates a new Food facts api.
     */
    public FoodFactsApi() {
        Log.d(this.getClass().getName(), "FoodFactsApi");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(IFoodFactsApi.class);
    }

    /**
     * Gets product.
     *
     * @param barcode         the barcode
     * @param requestListener the request listener
     */
    public void getProduct(String barcode, final Listener requestListener) {
        Log.d(this.getClass().getName(), "getProduct "+barcode);
        api.getProductStatus(barcode).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                requestListener.requestCompleted(response.body().getProduct());
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                requestListener.requestFailure(t);
            }
        });
    }

    /**
     * Gets api.
     *
     * @return the api
     */
    public IFoodFactsApi getApi() {
        return api;
    }

    /**
     * The interface Listener.
     */
    public interface Listener {
        /**
         * Request completed.
         *
         * @param product the product
         */
        void requestCompleted(Product product);

        /**
         * Request failure.
         *
         * @param t the t
         */
        void requestFailure(Throwable t);
    }
}
