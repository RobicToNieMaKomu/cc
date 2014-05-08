package com.polmos.cc.service;

import com.polmos.cc.constants.BundleName;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author RobicToNieMaKomu
 */
@RunWith(MockitoJUnitRunner.class)
public class ResourceManagerTest {

    @Test
    public void getAllCurrenciesTest() {
        List<String> allKeys = ResourceManager.getAllKeys(BundleName.CURRENCIES);
        Assert.assertTrue(allKeys != null);
        Assert.assertEquals(167, allKeys.size());
    }
}
