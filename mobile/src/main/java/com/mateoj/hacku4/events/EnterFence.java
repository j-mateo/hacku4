package com.mateoj.hacku4.events;

import com.google.android.gms.location.Geofence;

import java.util.List;

/**
 * Created by jose on 2/4/16.
 */
public class EnterFence {
    private List<Geofence> geofences;

    public EnterFence(List<Geofence> geofences)
    {
        this.geofences = geofences;
    }

    public List<Geofence> getGeofences() {
        return geofences;
    }
}
