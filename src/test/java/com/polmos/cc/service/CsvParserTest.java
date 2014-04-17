package com.polmos.cc.service;

import com.polmos.cc.constants.Constants;
import javax.json.JsonObject;
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
public class CsvParserTest {

    private static final String example = "Kursy walut\\n\\nAktualizacja 17.04.2014 godz. 18:43:41\\n\\nKraj;Nazwa waluty;Kod waluty;Skup;Sprzeda�;Spread;Zmiana\\nEUGiW;Euro;1 EUR;4,1720;4,1975;0,0255;spadek\\nUSA;Dolar ameryka�ski;1 USD;3,0201;3,0380;0,0179;spadek\\nSzwajcaria;Frank szwajcarski;1 CHF;3,4230;3,4442;0,0212;spadek\\nWielka Brytania;Funt brytyjski;1 GBP;5,0694;5,1009;0,0315;spadek\\nAustralia;Dolar australijski;1 AUD;2,8127;2,8358;0,0231;spadek\\nKanada;Dolar kanadyjski;1 CAD;2,7429;2,7653;0,0224;wzrost\\nCzechy;Korona czeska;1 CZK;0,1516;0,1531;0,0015;spadek\\nDania;Korona du�ska;1 DKK;0,5582;0,5628;0,0046;spadek\\nW�gry;Forint w�gierski;100 HUF;1,3578;1,3706;0,0128;spadek\\nJaponia;Jen japo�ski;100 JPY;2,9459;2,9699;0,0240;spadek\\nNorwegia;Korona norweska;1 NOK;0,5035;0,5077;0,0042;spadek\\nRosja;Rubel rosyjski;1 RUB;0,0845;0,0857;0,0012;spadek\\nSzwecja;Korona szwedzka;1 SEK;0,4567;0,4606;0,0039;wzrost\\n";
    
    @InjectMocks
    private CsvParser csvParser = new CsvParserImpl();
    
    @Test
    public void parseNullCsvTest() {
        JsonObject output = csvParser.parseRawText(null);
        Assert.assertNull(output);
    }
    
    @Test
    public void parseEmptyCsvTest() {
        JsonObject output = csvParser.parseRawText("");
        Assert.assertNull(output);
    }
    
    //@Test
    public void parseCsvTest() {
         JsonObject output = csvParser.parseRawText(example);
         Assert.assertNotNull(output); //17.04.2014 godz. 18:43:41
         Assert.assertEquals("2014-04-17T18:43:41+0200", output.getString(Constants.CREATION_TIME_PROPERTY));
         
    }
}
