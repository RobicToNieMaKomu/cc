package com.polmos.cc.service.mst;

import com.polmos.cc.constants.OperationType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

    private static final float DEF_ASK = 0.5f;
    private static final float DEF_BID = 0.5f;
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
                Assert.assertEquals((float) Math.sqrt((1 - mx[i][j]) * 0.5), output[i][j], 0.0001f);
            }
        }
    }

    @Test
    public void testSomethingDude() throws IOException {
        List<String> currencies = new ArrayList<>();
        currencies.add("PLN");
        currencies.add("USD");
        currencies.add("EUR");
        float[][] mx = {{0, 1, 0.1f}, {1, 0, 0.5f}, {0.1f, 0.5f, 0}};
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
        float[][] mx = {{0, 1, 0.1f}, {1, 0, 0.5f}, {0.1f, 0.5f, 0}};
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
        float[][] mx = {
            {0, 0.01f, 0.02f, 0.03f, 0.03f, 0.05f},
            {0.01f, 0, 0.05f, 0.05f, 0.05f, 0.05f},
            {0.02f, 0.05f, 0, 0.05f, 0.05f, 0.05f},
            {0.03f, 0.05f, 0.05f, 0, 0.05f, 0.05f},
            {0.03f, 0.05f, 0.05f, 0.05f, 0, 0.01f},
            {0.05f, 0.05f, 0.05f, 0.05f, 0.01f, 0},};
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

    @Test
    public void passNullsToCorrelationMxGeneratorTest() {
        float[][] output1 = mstutils.generateCorrelationMx(null, null, null);
        float[][] output2 = mstutils.generateCorrelationMx(null, null, OperationType.ASK);
        float[][] output3 = mstutils.generateCorrelationMx(null, Collections.<TimeWindow>emptyList(), OperationType.ASK);
        Assert.assertNull(output1);
        Assert.assertNull(output2);
        Assert.assertNull(output3);
    }

    @Test
    public void passEmptyInputToCorrelationMxGeneratorTest() {
        float[][] output = mstutils.generateCorrelationMx(Collections.<String>emptyList(), Collections.<TimeWindow>emptyList(), OperationType.ASK);
        Assert.assertEquals(0, output.length);
    }

    @Test
    public void simpleCorrelationMxGenerationTest() {
        List<String> currencies = currencies(1);
        List<TimeWindow> timeSeries = createTimeSeries(1, 2);
        float[][] output = mstutils.generateCorrelationMx(currencies, timeSeries, OperationType.ASK);
        Assert.assertEquals(1, output.length);
        Assert.assertEquals(new Float(1), new Float(output[0][0]));
    }

    @Test
    public void correlated2x2MxTest() {
        List<String> currencies = currencies(2);
        List<TimeWindow> timeSeries = createTimeSeries(2, 200);
        float[][] output = mstutils.generateCorrelationMx(currencies, timeSeries, OperationType.ASK);
        Assert.assertEquals(2, output.length);
        Assert.assertEquals(new Float(1), new Float(output[0][0]));
        Assert.assertEquals(new Float(1), new Float(output[1][1]));
        Assert.assertEquals(new Float(output[0][1]), new Float(output[1][0]));
        Assert.assertEquals(new Float(1), new Float(output[1][0]));
    }

    @Test
    public void antiCorrelated2x2MxTest() {
        List<String> currencies = currencies(2);
        List<TimeWindow> timeSeries = new ArrayList<>();
        ExRate x1 = new ExRate("0", 0.5f, 0.5f);
        ExRate y1 = new ExRate("1", 1.5f, 1.5f);
        List<ExRate> exRates1 = new ArrayList<>();
        exRates1.add(x1);
        exRates1.add(y1);
        TimeWindow t1 = new TimeWindow(exRates1);
        ExRate x2 = new ExRate("0", 1.5f, 1.5f);
        ExRate y2 = new ExRate("1", 0.5f, 0.5f);
        List<ExRate> exRates2 = new ArrayList<>();
        exRates2.add(x2);
        exRates2.add(y2);
        TimeWindow t2 = new TimeWindow(exRates2);
        timeSeries.add(t1);
        timeSeries.add(t2);
        float[][] output = mstutils.generateCorrelationMx(currencies, timeSeries, OperationType.ASK);
        Assert.assertEquals(2, output.length);
        Assert.assertEquals(new Float(1), new Float(output[0][0]));
        Assert.assertEquals(new Float(1), new Float(output[1][1]));
        Assert.assertEquals(new Float(output[0][1]), new Float(output[1][0]));
        Assert.assertEquals(new Float(-1), new Float(output[1][0]));
    }

    @Test
    public void somehowCorrelated2x2MxTest() {
        List<String> currencies = currencies(2);
        List<TimeWindow> timeSeries = new ArrayList<>();
        ExRate x1 = new ExRate("0", 1f, 1f);
        ExRate y1 = new ExRate("1", 1f, 1f);
        List<ExRate> exRates1 = new ArrayList<>();
        exRates1.add(x1);
        exRates1.add(y1);
        TimeWindow t1 = new TimeWindow(exRates1);
        ExRate x2 = new ExRate("0", 2f, 2f);
        ExRate y2 = new ExRate("1", 2f, 2f);
        List<ExRate> exRates2 = new ArrayList<>();
        exRates2.add(x2);
        exRates2.add(y2);
        TimeWindow t2 = new TimeWindow(exRates2);
        ExRate x3 = new ExRate("0", 2f, 2f);
        ExRate y3 = new ExRate("1", 4f, 4f);
        List<ExRate> exRates3 = new ArrayList<>();
        exRates3.add(x3);
        exRates3.add(y3);
        TimeWindow t3 = new TimeWindow(exRates3);
        timeSeries.add(t1);
        timeSeries.add(t2);
        timeSeries.add(t3);
        float[][] output = mstutils.generateCorrelationMx(currencies, timeSeries, OperationType.ASK);
        Assert.assertEquals(2, output.length);
        Assert.assertEquals(new Float(1), new Float(output[0][0]));
        Assert.assertEquals(new Float(1), new Float(output[1][1]));
        Assert.assertEquals(new Float(output[0][1]), new Float(output[1][0]));
        Assert.assertEquals(new Float(0.7559), new Float(output[1][0]), new Float(0.0001));
    }

    @Test
    public void testIfCorrelationMatrixIsSymetric() {
        List<String> currencies = currencies(100);
        List<TimeWindow> timeSeries = createTimeSeries(100, 200);
        float[][] output = mstutils.generateCorrelationMx(currencies, timeSeries, OperationType.ASK);
        Assert.assertEquals(100, output.length);
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                Assert.assertEquals(new Float(output[j][i]), new Float(output[i][j]));
            }
        }
    }

    private List<TimeWindow> createTimeSeries(int currCount, int size) {
        List<TimeWindow> output = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<ExRate> exRates = new ArrayList<>();
            for (int j = 0; j < currCount; j++) {
                exRates.add(new ExRate(String.valueOf(j), i + j + DEF_ASK, i + j + DEF_BID));
            }
            output.add(new TimeWindow(exRates));
        }
        return output;
    }

    private List<String> currencies(int count) {
        List<String> currencies = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            currencies.add(new Integer(i).toString());
        }
        return currencies;
    }
}