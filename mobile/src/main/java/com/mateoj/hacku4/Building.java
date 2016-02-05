package com.mateoj.hacku4;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by jose on 2/4/16.
 */
@ParseClassName("Building")
public class Building extends ParseObject {
    @Override
    public String toString() {
        return getString("Name");
    }

    public ParseGeoPoint getLocation() {
        return getParseGeoPoint("LatLong");
    }
}
