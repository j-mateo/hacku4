package com.mateoj.hacku4.events;

import com.google.android.gms.location.Geofence;

import java.util.List;

/**
 * Created by jose on 2/4/16.
 */
public class ExitFence {
    private List<Geofence> geofences;

    public ExitFence(List<Geofence> geofences)
    {
        this.geofences = geofences;
    }

    public List<Geofence> getGeofences() {
        return geofences;
    }
}
