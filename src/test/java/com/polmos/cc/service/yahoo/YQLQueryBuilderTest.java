package com.polmos.cc.service.yahoo;

import java.util.Collections;
import java.util.HashSet;
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
public class YQLQueryBuilderTest {

    @InjectMocks
    private YQLQueryBuilder builder = new YQLQueryBuilderImpl();

    @Test
    public void nullInputTest() {
        String output = builder.constructSelectQuery(null);
        Assert.assertEquals("", output);
    }

    @Test
    public void emptyInputTest() {
        String output = builder.constructSelectQuery(Collections.<String>emptySet());
        Assert.assertEquals("", output);
    }

    @Test
    public void singlePairTest() {
        Set<String> input = new HashSet<>();
        input.add("PLNUSD");
        String output = builder.constructSelectQuery(input);
        Assert.assertEquals("select id, Rate, Ask, Bid from yahoo.finance.xchange where pair in (\"PLNUSD\")", output);
    }

    @Test
    public void multiplePairsTest() {
        Set<String> input = new HashSet<>();
        input.add("PLNUSD");
        input.add("PLNEUR");
        String output = builder.constructSelectQuery(input);
        Assert.assertTrue(output.startsWith("select id, Rate, Ask, Bid from yahoo.finance.xchange where pair in ("));
        Assert.assertTrue(output.contains("PLNUSD"));    //\"PLNUSD\", \"PLNEUR\")", output);
        Assert.assertTrue(output.contains("PLNEUR"));
        Assert.assertTrue(output.endsWith(")"));
    }
}
