package com.pinsonn.foodfacts.data.db;

import com.pinsonn.foodfacts.model.Product;
import com.pinsonn.foodfacts.viewmodel.AbstractViewModel;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Julien on 15/01/2018.
 */
public class ProductDao {
    private Realm realm;
    private RealmResults<Product> result;

    /**
     * Instantiates a new Product dao.
     */
    public ProductDao(){
        realm = Realm.getDefaultInstance();
    }

    /**
     * Get product product.
     *
     * @param code the code
     * @return the product
     */
    public Product getProduct(long code){
        return realm.where(Product.class).equalTo("code", code).findFirst();
    }

    /**
     * Gets products.
     *
     * @param abstractViewModel the abstract view model
     */
    public void getProducts(AbstractViewModel abstractViewModel) {
        result = realm.where(Product.class).sort("code").findAllAsync();
        result.addChangeListener(abstractViewModel);
    }

    /**
     * Insert or update product.
     *
     * @param product the product
     */
    public void insertOrUpdateProduct(Product product){
        realm.beginTransaction();
        realm.insertOrUpdate(product);
        realm.commitTransaction();
    }

    /**
     * Update uri.
     *
     * @param product the product
     * @param uri     the uri
     */
    public void updateUri(Product product, String uri){
        realm.beginTransaction();
        product.setUri(uri);
        realm.commitTransaction();
    }

    /**
     * Delete all.
     */
    public void deleteAll(){
        realm.deleteAll();
    }
}
