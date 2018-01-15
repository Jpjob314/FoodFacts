package com.pinsonn.foodfacts;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.pinsonn.foodfacts.data.db.ProductDao;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private ProductDao productDao;

    public ApplicationTest() {
        super(Application.class);
    }
}
