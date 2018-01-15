package com.pinsonn.foodfacts.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.pinsonn.foodfacts.R;
import com.pinsonn.foodfacts.data.api.FoodFactsApi;
import com.pinsonn.foodfacts.data.db.ProductDao;
import com.pinsonn.foodfacts.model.Product;

import javax.annotation.Nullable;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.OrderedCollectionChangeSet;
import io.realm.RealmResults;

/**
 * Created by Julien on 12/01/2018.
 */
public class ItemListViewModel extends AbstractViewModel implements FoodFactsApi.Listener {
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FoodFactsApi foodFactsApi;
    private RealmResults<Product> products;
    private ProductDao productDao;

    /**
     * Gets products.
     *
     * @return the products
     */
    public RealmResults<Product> getProducts() {
        return products;
    }

    /**
     * Instantiates a new Item list view model.
     *
     * @param context the context
     */
    public ItemListViewModel(@NonNull Context context) {
        Log.d(this.getClass().getName(), "ItemListViewModel " + context);
        productDao = new ProductDao();
        foodFactsApi = new FoodFactsApi();
    }

    private void unSubscribeFromObservable() {
        Log.d(this.getClass().getName(), "unSubscribeFromObservable");
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    /**
     * Reset.
     */
    public void reset() {
        Log.d(this.getClass().getName(), "reset");
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }

    @Override
    public void onChange(RealmResults<Product> products, @Nullable OrderedCollectionChangeSet changeSet) {
        Log.d(this.getClass().getName(), "onChange " + products + " " + changeSet);
        this.products = products;
        setChanged();
        notifyObservers();
    }

    /**
     * Query data.
     */
    public void queryData() {
        Log.d(this.getClass().getName(), "queryData");
        productDao.getProducts(this);
    }

    /**
     * Query api.
     *
     * @param code the code
     */
    public void queryApi(String code) {
        Log.d(this.getClass().getName(), "queryApi " + code);
        foodFactsApi.getProduct(code, this);
    }

    @Override
    public void requestCompleted(Product product) {
        Log.d(this.getClass().getName(), "requestCompleted " + product);
        if (product != null) {
            productDao.insertOrUpdateProduct(product);
        } else {
            Toast.makeText(context, R.string.request_not_found, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void requestFailure(Throwable t) {
        Log.d(this.getClass().getName(), "requestFailure " + t);
        Toast.makeText(context, R.string.request_error, Toast.LENGTH_LONG).show();
    }
}
