package com.pinsonn.foodfacts.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.pinsonn.foodfacts.R;
import com.pinsonn.foodfacts.databinding.ItemListActivityBinding;
import com.pinsonn.foodfacts.viewmodel.ItemListViewModel;

import java.util.Observable;
import java.util.Observer;


/**
 * The type Item list activity.
 */
public class ItemListActivity extends AppCompatActivity implements Observer {

    private ItemListActivityBinding itemListActivityBinding;
    private ItemListViewModel itemListViewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();

        setSupportActionBar(itemListActivityBinding.toolbar);
        setFab(itemListActivityBinding.fab);

        recyclerView = (RecyclerView) itemListActivityBinding.frameLayout.findViewById(R.id.item_list);
        setupRecyclerView(recyclerView);

        setupObserver(itemListViewModel);

        //query datas from DB
        itemListViewModel.queryData();
    }

    private void initDataBinding() {
        Log.d(this.getClass().getName(), "initDataBinding");
        itemListActivityBinding = DataBindingUtil.setContentView(this, R.layout.item_list_activity);
        itemListViewModel = new ItemListViewModel(this);
        itemListActivityBinding.setMainViewModel(itemListViewModel);
    }

    private void setFab(FloatingActionButton fab) {
        Log.d(this.getClass().getName(), "setFab");
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "TODO : open camera to scan barcode ?", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    private void setupObserver(Observable observable) {
        Log.d(this.getClass().getName(), "setupObserver");
        observable.addObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        itemListViewModel.reset();
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.d(this.getClass().getName(), "update");
        if (observable instanceof ItemListViewModel) {
            Log.d(this.getClass().getName(), "update ItemListViewModel");
            setupRecyclerView(recyclerView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        setupSearch(searchItem);

        return true;
    }

    private void setupSearch(MenuItem searchItem) {
        Log.d(this.getClass().getName(), "setupSearch " + searchItem);
        if (searchItem != null) {
            SearchView search = (SearchView) searchItem.getActionView();
            search.setIconifiedByDefault(true);
            search.setFocusable(true);
            search.setIconified(false);
            search.requestFocusFromTouch();
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.d(this.getClass().getName(), "onQueryTextSubmit " + query);
                    checkCode(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.d(this.getClass().getName(), "onQueryTextChange " + newText);
                    return false;
                }
            });
        }
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        Log.d(this.getClass().getName(), "setupRecyclerView " + recyclerView);
        if (recyclerView != null) {
            recyclerView.setAdapter(new ProductRecyclerViewAdapter(this, itemListViewModel.getProducts()));
            if(itemListViewModel.getProducts() == null || itemListViewModel.getProducts().isEmpty()){
                itemListActivityBinding.frameLayout.findViewById(R.id.noproducts).setVisibility(View.VISIBLE);
            } else {
                itemListActivityBinding.frameLayout.findViewById(R.id.noproducts).setVisibility(View.GONE);
            }
        }
    }

    private void checkCode(String code) {
        if (code != null && code.length() == 13 && code.matches("\\d*")) {
            itemListViewModel.queryApi(code);
        } else {
            Toast.makeText(this, R.string.invalid_ean13, Toast.LENGTH_LONG).show();
        }
    }
}
