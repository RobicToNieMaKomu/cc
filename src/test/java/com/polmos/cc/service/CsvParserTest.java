package com.polmos.cc.service;

import com.polmos.cc.service.alior.AliorCsvParserImpl;
import com.polmos.cc.service.alior.AliorCsvParser;
import com.polmos.cc.constants.Constants;
import java.io.IOException;
import java.math.BigDecimal;
import javax.json.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author RobicToNieMaKomu
 */
@RunWith(MockitoJUnitRunner.class)
public class CsvParserTest {

    @Spy
    private TimeUtils timeUtils = new TimeUtilsImpl();
    
    @InjectMocks
    private AliorCsvParser csvParser = new AliorCsvParserImpl();
    
    @Test
    public void parseNullCsvTest() throws IOException {
        JsonObject output = csvParser.parseRawText(null);
        Assert.assertTrue(output.isEmpty());
    }
    
    @Test
    public void parseEmptyCsvTest() throws IOException {
        JsonObject output = csvParser.parseRawText("");
        Assert.assertTrue(output.isEmpty());
    }
    
    //@Test
    public void parseCsvTest() throws IOException {
         JsonObject output = csvParser.parseRawText(createExample());
         Assert.assertNotNull(output); //17.04.2014 godz. 18:43:41
         Assert.assertEquals("2014-05-17T20:41:36+0200", output.getString(Constants.CREATION_TIME_PROPERTY));
         assertRow(output.getJsonObject("1 EUR"), new BigDecimal(4.1699f), new BigDecimal(4.1954f), new BigDecimal(0.0255f));
         assertRow(output.getJsonObject("1 USD"), new BigDecimal(3.0180f), new BigDecimal(3.0358f), new BigDecimal(0.0178f));
         assertRow(output.getJsonObject("1 CHF"), new BigDecimal(3.4180f), new BigDecimal(3.4392f), new BigDecimal(0.0212f));
         assertRow(output.getJsonObject("1 GBP"), new BigDecimal(5.0679f), new BigDecimal(5.0996f), new BigDecimal(0.0317f));
         assertRow(output.getJsonObject("1 AUD"), new BigDecimal(2.8106f), new BigDecimal(2.8336f), new BigDecimal(0.0230f));
         assertRow(output.getJsonObject("1 CAD"), new BigDecimal(2.7375f), new BigDecimal(2.7597f), new BigDecimal(0.0222f));
         assertRow(output.getJsonObject("1 CZK"), new BigDecimal(0.1516f), new BigDecimal(0.1530f), new BigDecimal(0.0014f));
         assertRow(output.getJsonObject("1 DKK"), new BigDecimal(0.5579f), new BigDecimal(0.5625f), new BigDecimal(0.0046f));
         assertRow(output.getJsonObject("100 HUF"), new BigDecimal(1.3578f), new BigDecimal(1.3708f), new BigDecimal(0.0130f));
         assertRow(output.getJsonObject("100 JPY"), new BigDecimal(2.9437f), new BigDecimal(2.9683f), new BigDecimal(0.0246f));
         assertRow(output.getJsonObject("1 NOK"), new BigDecimal(0.5037f), new BigDecimal(0.5080f), new BigDecimal(0.0043f));
         assertRow(output.getJsonObject("1 RUB"), new BigDecimal(0.0845f), new BigDecimal(0.0857f), new BigDecimal(0.0012f));
         assertRow(output.getJsonObject("1 SEK"), new BigDecimal(0.4566f), new BigDecimal(0.4605f), new BigDecimal(0.0039f));
    }
    
    private void assertRow(JsonObject object, BigDecimal buy, BigDecimal sell, BigDecimal spread) {
        Assert.assertEquals(object.size(), 3);
        Assert.assertEquals(object.getJsonNumber(Constants.SELL_PROPERTY).bigDecimalValue(), sell);
        Assert.assertEquals(object.getJsonNumber(Constants.BUY_PROPERTY).bigDecimalValue(), buy);
        Assert.assertEquals(object.getJsonNumber(Constants.SPREAD_PROPERTY).bigDecimalValue(), spread);
    }
    
    private String createExample() {
        StringBuilder sb = new StringBuilder();
        sb.append("Kursy walut\r\n");
        sb.append("\r\n");
        sb.append("Aktualizacja 17.04.2014 godz. 20:41:36\r\n");
        sb.append("Kraj;Nazwa waluty;Kod waluty;Skup;Sprzeda�;Spread;Zmiana\r\n");
        sb.append("EUGiW;Euro;1 EUR;4,1699;4,1954;0,0255;spadek\r\n");
        sb.append("USA;Dolar ameryka�ski;1 USD;3,0180;3,0358;0,0178;wzrost\r\n");
        sb.append("Szwajcaria;Frank szwajcarski;1 CHF;3,4180;3,4392;0,0212;spadek\r\n");
        sb.append("Wielka Brytania;Funt brytyjski;1 GBP;5,0679;5,0996;0,0317;wzrost\r\n");
        sb.append("Australia;Dolar australijski;1 AUD;2,8106;2,8336;0,0230;spadek\r\n");
        sb.append("Kanada;Dolar kanadyjski;1 CAD;2,7375;2,7597;0,0222;spadek\r\n");
        sb.append("Czechy;Korona czeska;1 CZK;0,1516;0,1530;0,0014;spadek\r\n");
        sb.append("Dania;Korona du�ska;1 DKK;0,5579;0,5625;0,0046;spadek\r\n");
        sb.append("W�gry;Forint w�gierski;100 HUF;1,3578;1,3708;0,0130;spadek\r\n");
        sb.append("Japonia;Jen japo�ski;100 JPY;2,9437;2,9683;0,0246;spadek\r\n");
        sb.append("Norwegia;Korona norweska;1 NOK;0,5037;0,5080;0,0043;spadek\r\n");
        sb.append("Rosja;Rubel rosyjski;1 RUB;0,0845;0,0857;0,0012;spadek\r\n");
        sb.append("Szwecja;Korona szwedzka;1 SEK;0,4566;0,4605;0,0039;wzrost\r\n");
        return sb.toString();
    }
}
