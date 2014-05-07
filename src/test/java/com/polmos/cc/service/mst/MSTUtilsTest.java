package com.polmos.cc.service.mst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author RobicToNieMaKomu
 */
@RunWith(MockitoJUnitRunner.class)
public class MSTUtilsTest {

    @InjectMocks
    private MSTUtils mstutils = new MSTUtilsImpl();

    @Test(expected = IOException.class)
    public void nullCorrelationMxToDistanceMxTest() throws IOException {
        mstutils.convertCorrelationMxToDistanceMx(null);
    }

    @Test(expected = IOException.class)
    public void correlationMxToDistanceMx1x1Test() throws IOException {
        float[][] mx = {{1}};
        mstutils.convertCorrelationMxToDistanceMx(mx);
    }

    @Test(expected = IOException.class)
    public void correlationMxToDistanceMx3x1Test() throws IOException {
        float[][] mx = {{1}, {2}, {3}};
        mstutils.convertCorrelationMxToDistanceMx(mx);
    }

    @Test
    public void convertCorrelationMxToDistanceMxTest() throws IOException {
        float[][] mx = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        float[][] output = mstutils.convertCorrelationMxToDistanceMx(mx);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Assert.assertEquals((float)Math.sqrt((1-mx[i][j])*0.5), output[i][j], 0.0001f);
            }
        }
    }
    
    @Test
    public void testSomethingDude() throws IOException {
        List<String> currencies = new ArrayList<>();
        currencies.add("PLN");
        currencies.add("USD");
        currencies.add("EUR");
        float [][] mx = {{0, 1, 0.1f},{1, 0, 0.5f},{0.1f, 0.5f, 0}};
        List<String> output = mstutils.sortByDistanceAsc(currencies, mx);
        // 0 - 2, 1 - 2, 0 - 1 
        Assert.assertNotNull(output);
        Assert.assertEquals(3, output.size());
        Assert.assertEquals("PLN$$EUR", output.get(0));
        Assert.assertEquals("USD$$EUR", output.get(1));
        Assert.assertEquals("PLN$$USD", output.get(2));
    }
    
    @Test
    public void simplestMSTTest() throws IOException {
        List<String> currencies = new ArrayList<>();
        currencies.add("PLN");
        currencies.add("USD");
        currencies.add("EUR");
        float [][] mx = {{0, 1, 0.1f},{1, 0, 0.5f},{0.1f, 0.5f, 0}};
        Map<String, Set<String>> mst = mstutils.constructMst(currencies, mx);
        Assert.assertEquals(3, mst.size());
        Assert.assertTrue(mst.containsKey("PLN"));
        Assert.assertTrue(mst.containsKey("USD"));
        Assert.assertTrue(mst.containsKey("EUR"));
        Set<String> plny = mst.get("PLN");
        Set<String> usdny = mst.get("USD");
        Set<String> eureny = mst.get("EUR");
        Assert.assertEquals(1, plny.size());
        Assert.assertEquals(1, usdny.size());
        Assert.assertEquals(2, eureny.size());
        Assert.assertTrue(plny.contains("EUR"));
        Assert.assertTrue(usdny.contains("EUR"));
        Assert.assertTrue(eureny.contains("PLN"));
        Assert.assertTrue(eureny.contains("USD"));
    }
    
    @Test
    public void uberSophisticatedMSTTest() throws IOException {
        List<String> currencies = new ArrayList<>();
        currencies.add("PLN");
        currencies.add("USD");
        currencies.add("EUR");
        currencies.add("AUD");
        currencies.add("CHF");
        currencies.add("NOK");
        float [][] mx = {
            {0, 0.01f, 0.02f, 0.03f, 0.03f, 0.05f},
            {0.01f, 0, 0.05f, 0.05f, 0.05f, 0.05f},
            {0.02f, 0.05f, 0, 0.05f, 0.05f, 0.05f},
            {0.03f, 0.05f, 0.05f, 0, 0.05f, 0.05f},
            {0.03f, 0.05f, 0.05f, 0.05f, 0, 0.01f},
            {0.05f, 0.05f, 0.05f, 0.05f, 0.01f, 0},
        };
        Map<String, Set<String>> mst = mstutils.constructMst(currencies, mx);
        Assert.assertEquals(6, mst.size());
        Assert.assertTrue(mst.containsKey("PLN"));
        Assert.assertTrue(mst.containsKey("USD"));
        Assert.assertTrue(mst.containsKey("EUR"));
        Assert.assertTrue(mst.containsKey("AUD"));
        Assert.assertTrue(mst.containsKey("CHF"));
        Assert.assertTrue(mst.containsKey("NOK"));
        Set<String> pN = mst.get("PLN");
        Set<String> uN = mst.get("USD");
        Set<String> eN = mst.get("EUR");
        Set<String> aN = mst.get("AUD");
        Set<String> cN = mst.get("CHF");
        Set<String> nN = mst.get("NOK");
        Assert.assertTrue(pN.contains("USD"));
        Assert.assertTrue(pN.contains("EUR"));
        Assert.assertTrue(pN.contains("AUD"));
        Assert.assertTrue(pN.contains("CHF"));
        Assert.assertTrue(uN.contains("PLN"));
        Assert.assertTrue(eN.contains("PLN"));
        Assert.assertTrue(aN.contains("PLN"));
        Assert.assertTrue(cN.contains("PLN"));
        Assert.assertTrue(cN.contains("NOK"));
        Assert.assertTrue(nN.contains("CHF"));
    }
}