package com.pinsonn.foodfacts;

import android.util.Log;

import com.pinsonn.foodfacts.data.api.FoodFactsApi;
import com.pinsonn.foodfacts.data.api.IFoodFactsApi;
import com.pinsonn.foodfacts.data.api.Status;
import com.pinsonn.foodfacts.model.Product;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Julien on 15/01/2018.
 */

public class ApiTest {
    @Test
    public void getProductStatus() {
        FoodFactsApi foodFactsApi = new FoodFactsApi();
        IFoodFactsApi api = foodFactsApi.getApi();

        //https://world.openfoodfacts.org/api/v0/product/3329770057258.json
        Call<Status> call = api.getProductStatus("3329770057258");

        try {
            Response<Status> response = call.execute();
            Product product = response.body().getProduct();
            assertEquals("Petits Filous Tub's Goût Fraise, Pêche, Framboise", product.getName());
            assertEquals(15, product.getIngredients().size());
            assertEquals("385", product.getNutriments().getEnergy());
        } catch (IOException e) {
            Log.e("FoodFactsTest", "IOException", e);
        }
    }
}
