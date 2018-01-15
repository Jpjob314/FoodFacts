package com.pinsonn.foodfacts.viewmodel;

import com.pinsonn.foodfacts.model.Product;

import java.util.Observable;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmResults;

/**
 * Created by Julien on 15/01/2018.
 */
public abstract class AbstractViewModel extends Observable implements OrderedRealmCollectionChangeListener<RealmResults<Product>> {

}
