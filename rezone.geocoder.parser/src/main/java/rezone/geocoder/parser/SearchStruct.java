package rezone.geocoder.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awatkins on 15-10-22.
 */
public class SearchStruct {
    public Geo[] _geos;
    public String[] numbers;
    public String[] street;
    public String street_suffix;
    public String[] regions;
    public String zip;
    public String state_code;

    public SearchStruct(Geo[] geos) {
        _geos = geos;

        List<String> numbersList = new ArrayList<>();
        List<String> streetList  = new ArrayList<>();
        List<String> regionsList = new ArrayList<>();

        //todo: fill in struct elements from geos
        for (Geo currGeo : _geos) {
            if (!empty(currGeo.unit)) {
                String[] parts = currGeo.unit.split("\\s");
                if ((parts.length == 2) && (parts[1].matches("\\d+")))
                    addToList(numbersList, parts[1]);
                else if ((parts.length > 0) && (parts[0].matches("\\d+")))
                    addToList(numbersList, parts[0]);
            }

            addToList(numbersList, currGeo.street_no);
            addToList(streetList, currGeo.street_direction);
            addToList(streetList, currGeo.street);
            addToList(streetList, currGeo.street_post_direction);
            addToList(regionsList, currGeo.neighborhood);
            addToList(regionsList, currGeo.city);

        }

        numbers = (null != numbersList) ? numbersList.toArray(new String[numbersList.size()]) : new String[0];
        street = (null != streetList) ? streetList.toArray(new String[streetList.size()]) : new String[0];
        regions = (null != regionsList) ? regionsList.toArray(new String[regionsList.size()]) : new String[0];

        street_suffix = geos[0].street_suffix;
        zip = geos[0].zip;
        state_code = geos[0].state;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SearchStruct))
            return false;

        if (obj == this)
            return true;

        SearchStruct o = (SearchStruct) obj;
        boolean same = true;

        same = same && arraysAreEqual(numbers, o.numbers);
        same = same && arraysAreEqual(street, o.street);
        same = same && arraysAreEqual(regions, o.regions);

        same = same && ((null == street_suffix) || (street_suffix.equalsIgnoreCase(o.street_suffix)));
        same = same && ((null == zip) || (zip.equalsIgnoreCase(o.zip)));
        same = same && ((null == state_code) || (state_code.equalsIgnoreCase(o.state_code)));

        return same;
    }

    private boolean empty(String s) {
        return (null == s) || (s.isEmpty());
    }


    private void addToList(List<String> list, String s) {
        if (!empty(s))
            list.add(s);
    }

    private boolean arraysAreEqual(String[] a, String[] b) {
        if ((null == a) && (null == b))
            return true;

        if ((null == a) || (null == b))
            return false;

        if (a.length != b.length)
            return false;

        for (int i = 0; i < a.length; i++)
            if (!a[i].equalsIgnoreCase(b[i]))
                return false;

        return true;
    }
}
