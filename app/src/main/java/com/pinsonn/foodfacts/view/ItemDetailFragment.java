package com.pinsonn.foodfacts.view;

import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.pinsonn.foodfacts.R;
import com.pinsonn.foodfacts.data.db.ProductDao;
import com.pinsonn.foodfacts.model.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.content.Context.MODE_PRIVATE;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {TODO : ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private ProductDao productDao;
    private Product mProduct;

    /**
     * Instantiates a new Item detail fragment.
     */
    public ItemDetailFragment() {
        Log.d(this.getClass().getName(), "ItemDetailFragment");
        productDao = new ProductDao();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            if (getArguments().containsKey(ARG_ITEM_ID)) {
                Log.d(this.getClass().getName(), "onCreate " + getArguments().get(ARG_ITEM_ID));
                mProduct = productDao.getProduct(Long.parseLong(getArguments().get(ARG_ITEM_ID).toString()));
            }
        } catch (Exception exp) {
            Log.e(this.getClass().getName(), exp.toString());
            exp.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(this.getClass().getName(), "onCreateView");
        final View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mProduct != null) {
            ((TextView) rootView.findViewById(R.id.id)).setText(String.valueOf(mProduct.getBarcode()));
            ((TextView) rootView.findViewById(R.id.name)).setText(mProduct.getName());
            ((TextView) rootView.findViewById(R.id.nutriments)).setText(mProduct.getNutriments().getEnergy());
            ((TextView) rootView.findViewById(R.id.ingredients)).setText(mProduct.getIngredientsString());

            if (mProduct.getUri() == null || mProduct.getUri().isEmpty()) {
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                ImageRequest imageRequest = new ImageRequest(
                        mProduct.getImageUrl(),
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                //Do something with response
                                ((ImageView) rootView.findViewById(R.id.image)).setImageBitmap(response);

                                //Save this downloaded bitmap to internal storage
                                Uri uri = saveImageToInternalStorage(String.valueOf(mProduct.getBarcode()), response);
                                if (uri != null) {
                                    productDao.updateUri(mProduct, uri.toString());
                                } else {
                                    Log.e(this.getClass().getName(), "uri is null !");
                                }
                            }
                        },
                        0, // Image width
                        0, // Image height
                        ImageView.ScaleType.CENTER_CROP, // Image scale type
                        Bitmap.Config.RGB_565, //Image decode configuration
                        new Response.ErrorListener() { // Error listener
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(this.getClass().getName(), error.toString());
                                error.printStackTrace();
                            }
                        }
                );
                requestQueue.add(imageRequest);
            } else {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), Uri.parse("file://" + mProduct.getUri()));
                    ((ImageView) rootView.findViewById(R.id.image)).setImageBitmap(bitmap);
                } catch (IOException e) {
                    Log.e(this.getClass().getName(), e.toString());
                    e.printStackTrace();
                }
            }
        } else {
            ((TextView) rootView.findViewById(R.id.id)).setText(getString(R.string.noproductselected));
        }

        return rootView;
    }

    private Uri saveImageToInternalStorage(String code, Bitmap bitmap) {
        Log.d(this.getClass().getName(), "saveImageToInternalStorage " + code + " " + bitmap);

        if (getActivity() == null) {
            Log.e(this.getClass().getName(), "saveImageToInternalStorage getActivity is null !");
            return null;
        }

        // Initialize ContextWrapper
        ContextWrapper wrapper = new ContextWrapper(getActivity().getApplicationContext());

        // Initializing a new file
        // The bellow line return a directory in internal storage
        File file = wrapper.getDir("Images", MODE_PRIVATE);
        file = new File(file, code + ".jpg");

        try {
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            // Flushes and close the stream
            stream.flush();
            stream.close();

        } catch (IOException e) {
            Log.e(this.getClass().getName(), e.toString());
            e.printStackTrace();
        }

        // Parse the gallery image url to uri
        Uri savedImageURI = Uri.parse(file.getAbsolutePath());
        Log.d(this.getClass().getName(), "saveImageToInternalStorage savedImageURI " + savedImageURI);

        // Return the saved image Uri
        return savedImageURI;
    }
}
