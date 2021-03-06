package com.pinsonn.foodfacts;

import com.pinsonn.foodfacts.data.db.ProductDao;
import com.pinsonn.foodfacts.model.Product;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.realm.Realm;
import io.realm.log.RealmLog;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest({Realm.class, RealmLog.class})
public class ExampleRealmTest {
    @Rule
    public PowerMockRule rule = new PowerMockRule();
    Realm mockRealm;

    @Before
    public void setup() {
        mockStatic(RealmLog.class);
        mockStatic(Realm.class);

        Realm mockRealm = PowerMockito.mock(Realm.class);

        when(Realm.getDefaultInstance()).thenReturn(mockRealm);

        this.mockRealm = mockRealm;
    }

    @Test
    public void shouldBeAbleToGetDefaultInstance() {
        assertThat(Realm.getDefaultInstance(), is(mockRealm));
    }

    @Test
    public void shouldBeAbleToMockRealmMethods() {
        when(mockRealm.isAutoRefresh()).thenReturn(true);
        assertThat(mockRealm.isAutoRefresh(), is(true));

        when(mockRealm.isAutoRefresh()).thenReturn(false);
        assertThat(mockRealm.isAutoRefresh(), is(false));
    }

    @Test
    public void shouldBeAbleToCreateARealmObject() {
        Product product = new Product();
        when(mockRealm.createObject(Product.class)).thenReturn(product);

        Product output = mockRealm.createObject(Product.class);

        assertThat(output, is(product));
    }

    @Test
    public void shouldVerifyThatProductWasCreated() {
        doCallRealMethod().when(mockRealm).executeTransaction(Mockito.any(Realm.Transaction.class));

        long code = 3450970024181L;

        Product product = new Product();
        product.setCode(code);
        product.setName("Veloute de Légumes - Leclerc Eco + - 1 L");

        ProductDao productDao = new ProductDao();
        productDao.insertOrUpdateProduct(product);

        Product dbProduct = productDao.getProduct(code);
        assertNotNull(dbProduct);
    }
}