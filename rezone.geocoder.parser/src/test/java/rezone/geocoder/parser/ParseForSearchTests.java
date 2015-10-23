package rezone.geocoder.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


@RunWith(JUnitParamsRunner.class)
public class ParseForSearchTests {
    private static QueryParser _parser;
    private static Gson _gson;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        _parser = new QueryParser();
        _gson = new GsonBuilder().create();
    }


    @Test
    @Parameters({
            "7212 SUN COVE CR;Las Vegas;NV;89128       | { numbers: ['7212']. street: ['Sun Cove']. street_suffix: 'Cr'. regions: ['Las Vegas']. state_code: 'NV'. zip: '89128' }"
    })
    public void parseFullAddress(String input, String  expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        SearchStruct expected = _gson.fromJson(expectedAsJson, SearchStruct.class);

        ParseDebug dbg = new ParseDebug();
        SearchStruct actual = new SearchStruct(_parser.parse(input, dbg));

        assertNotNull("Parser response is null", actual);
        assertTrue(input, actual.equals(expected));
    }


}
