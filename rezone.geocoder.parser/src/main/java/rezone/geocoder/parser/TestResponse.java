package rezone.geocoder.parser;

/**
 * Created by awatkins on 3/3/2016.
 */
public class TestResponse {
    public TestResponse() {
        parserExistsOnStartup = false;
        parserConstructionMicros = 0;
        parserConstructionMillis = 0;
    }
    public Boolean parserExistsOnStartup;
    public long parserConstructionMicros;
    public long parserConstructionMillis;
    public String stringParam;
}
