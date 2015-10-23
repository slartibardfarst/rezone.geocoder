package rezone.geocoder.parser;

/**
 * Created by awatkins on 15-10-22.
 */
public class SearchStruct {
    Geo[] _geos;
    String[] numbers;
    String[] street;
    String street_suffix;
    String[] regions;
    String zip;
    String state_code;

    public SearchStruct(Geo[] geos)
    {
        _geos = geos;

        //todo: fill in struct elements from geos
    }
}
