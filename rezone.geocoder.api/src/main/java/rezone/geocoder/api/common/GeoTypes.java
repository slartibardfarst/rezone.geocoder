package rezone.geocoder.common;

/**
 * Created by ryong on 10/26/2015.
 */
public enum GeoTypes {
    Neighborhood("Neighborhood"),
    City("City"),
    County("County"),
    Zip("Zip"),
    Address("Address"),
    Street("Street");

    private String name;

    GeoTypes(String name)
    {
        this.name = name;
    }

        @Override
    public String toString()
    {
        return name;
    }
}
