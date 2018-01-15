package com.pinsonn.foodfacts.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pinsonn.foodfacts.R;
import com.pinsonn.foodfacts.model.Product;

import java.util.List;

/**
 * Created by Julien on 12/01/2018.
 */
public class ProductRecyclerViewAdapter
        extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {

    private final ItemListActivity mParentActivity;
    private final List<Product> mProducts;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(this.getClass().getName(), "ProductRecyclerViewAdapter " + view);
            /*TODO : check if tablet or mobile and open a fullscreen Activity from here if mobile*/
            Product product = (Product) view.getTag();
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, String.valueOf(product.getBarcode()));
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            mParentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        }
    };

    /**
     * Instantiates a new Product recycler view adapter.
     *
     * @param parent the parent
     * @param items  the items
     */
    public ProductRecyclerViewAdapter(ItemListActivity parent, List<Product> items) {
        Log.d(this.getClass().getName(), "ProductRecyclerViewAdapter " + parent + " " + items);
        mProducts = items;
        mParentActivity = parent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(this.getClass().getName(), "onCreateViewHolder " + parent + " " + viewType);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d(this.getClass().getName(), "onBindViewHolder " + holder + " " + position);
        holder.mIdView.setText(String.valueOf(mProducts.get(position).getBarcode()));
        holder.mContentView.setText(mProducts.get(position).getName());

        holder.itemView.setTag(mProducts.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        Log.d(this.getClass().getName(), "getItemCount");
        if (mProducts != null) {
            return mProducts.size();
        } else {
            return 0;
        }
    }

    /**
     * The type View holder.
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * The M id view.
         */
        final TextView mIdView;
        /**
         * The M content view.
         */
        final TextView mContentView;

        /**
         * Instantiates a new View holder.
         *
         * @param view the view
         */
        public ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id_text);
            mContentView = (TextView) view.findViewById(R.id.content);
        }
    }
}
