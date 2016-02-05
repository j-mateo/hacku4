package com.mateoj.hacku4;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.mateoj.hacku4.events.EnterFence;
import com.mateoj.hacku4.events.ExitFence;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by jose on 2/4/16.
 */
public class GeofenceTransitionsIntentService extends IntentService {
    public static final String TAG = GeofenceTransitionsIntentService.class.getSimpleName();

    public GeofenceTransitionsIntentService() {
        super("geofenceservice");
    }

    private void onEnterGeofences(List<Geofence> geofences) {
        ParseQuery.getQuery(Building.class)
                .getInBackground(geofences.get(0).getRequestId(), new GetCallback<Building>() {
                    @Override
                    public void done(final Building object, ParseException e) {
                        ParseQuery.getQuery(Event.class)
                                .whereEqualTo("Location", object)
                                .orderByAscending("Time")
//                                .whereLessThan("EndTime", new Date())
                                .findInBackground(new FindCallback<Event>() {
                                    @Override
                                    public void done(List<Event> objects, ParseException e) {
                                        Log.d(TAG, object.toString());
                                        if (objects.size() > 0)
                                            notifyUser(objects.get(0));
                                    }
                                });
                    }
                });
    }

    private void notifyUser(Event event) {

        Notification notification1 = new NotificationCompat.Builder(this)
                .setContentTitle("New Notification")
                .setContentText("event " + event.getName())
                .setSmallIcon(R.mipmap.ic_launcher).build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(1234, notification1);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Toast.makeText(this, "Geofence intent activated", Toast.LENGTH_SHORT).show();
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = "" + geofencingEvent.getErrorCode();
            Log.e(TAG, errorMessage);
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
//        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
//                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
        List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            EventBus.getDefault().post(new EnterFence(triggeringGeofences));
            onEnterGeofences(triggeringGeofences);
            // Get the transition details as a String.
            Log.d(TAG, "" + geofenceTransition);
//            String geofenceTransitionDetails = getGeofenceTransitionDetails(
//                    this,
//                    geofenceTransition,
//                    triggeringGeofences
//            );

            // Send notification and log the transition details.
//            sendNotification(geofenceTransitionDetails);
            Log.i(TAG, "" + geofenceTransition);
        } else if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            EventBus.getDefault().post(new ExitFence(triggeringGeofences));

        } else {
            // Log the error.
            Log.e(TAG, "geofence error");
        }
    }
}