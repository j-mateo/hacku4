package com.mateoj.hacku4;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by jose on 2/4/16.
 */
@ParseClassName("Building")
public class Building extends ParseObject {
    public static final String KEY_LOCATION ="LatLong";

    public String getName() {
        return getString("Name");
    }

    @Override
    public String toString() {
        return getString("Name");
    }

    public ParseGeoPoint getLocation() {
        return getParseGeoPoint("LatLong");
    }
}
