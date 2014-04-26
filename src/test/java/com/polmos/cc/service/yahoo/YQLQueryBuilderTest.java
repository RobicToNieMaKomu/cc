package com.polmos.cc.service.yahoo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        String output = builder.constructSelectQuery(Collections.<String>emptyList());
        Assert.assertEquals("", output);
    }

    @Test
    public void singlePairTest() {
        List<String> input = new ArrayList<>();
        input.add("PLNUSD");
        String output = builder.constructSelectQuery(input);
        Assert.assertEquals("select * from yahoo.finance.xchange where pair in (\"PLNUSD\")", output);
    }

    @Test
    public void multiplePairsTest() {
         List<String> input = new ArrayList<>();
        input.add("PLNUSD");
        input.add("PLNEUR");
        String output = builder.constructSelectQuery(input);
        Assert.assertEquals("select * from yahoo.finance.xchange where pair in (\"PLNUSD\", \"PLNEUR\")", output);
    }
}
