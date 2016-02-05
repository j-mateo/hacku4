package com.mateoj.hacku4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jose on 2/5/16.
 */
public class ParsePushReceiver extends ParsePushBroadcastReceiver {
    public static final String TAG = ParsePushReceiver.class.getName();
    public static final String KEY_EVENT_ID = "eventId";
    public static final String KEY_PUSH_TYPE = "pushType";
    public static final String KEY_SEARCHID = "searchId";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ALERT = "alert";
    public static final String PUSH_TYPE_SEARCH = "savedSearch";
    public static final String APPTENTIVE_MESSAGE = "New message from our support team";
    public static final String PREF_CONTEXT = "notifications";
    public static final String PREF_KEY_SEARCH_COUNT = "searchNotificationCount";
    public static final String PREF_KEY_APPTENTIVE_COUNT = "apptentiveNotificationCount";
    public static final int SEARCH_NOTIFICATION_ID = 234324;
    public static final int APPTENTIVE_NOTIFICATION_ID = 4567546;

//
//    @Override
//    protected Notification getNotification(Context context, Intent intent) {
//        Notification notification;
//        if (isSearchNotification(context, intent)) {
//            notification = getSearchNotification(context, intent);
//        } else if(isApptentiveNotification(context, intent)) {
//            notification = getApptentiveNotification(context, intent);
//        } else {
//            notification = super.getNotification(context, intent);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            notification.priority = Notification.PRIORITY_LOW;
//        }
//        return lollipopfy(context, notification);
//    }
//
//    private int getApptentiveNotificationCount(Context context) {
//        return getApptentivePreference(context).get();
//    }
//
//    private void setApptentiveNotificationCount(Context context) {
//        this.setApptentiveNotificationCount(context, 0);
//    }
//    private void setApptentiveNotificationCount(Context context, int count) {
//        getApptentivePreference(context).set(count);
//    }
//
//    private Notification getApptentiveNotification(Context context, Intent intent) {
//        int count = getApptentiveNotificationCount(context);
//        count++;
//        setApptentiveNotificationCount(context, count);
//
//        boolean shouldSummarize = (count > 1);
//        if (shouldSummarize) {
//            try {
//                JSONObject json = new JSONObject(intent.getExtras().getString(KEY_PUSH_DATA));
//                json.put("title", context.getString(R.string.app_name));
//                json.put("alert", APPTENTIVE_MESSAGE);
//                intent.putExtra(KEY_EVENT_ID, json.getString(KEY_EVENT_ID));
//                intent.putExtra(KEY_PUSH_DATA, json.toString());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        Notification notification = super.getNotification(context, intent);
//        if (shouldSummarize)
//            notification.number = count;
//
//        return lollipopfy(context, notification);
//    }
//
//    private Notification lollipopfy(Context context, Notification notification) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            notification.color = context.getResources().getColor(R.color.theme_default_primary);
//        }
//        return notification;
//    }
//
    @Override
    protected void onPushOpen(Context context, Intent intent) {
        Class activityClass = getActivity(context, intent);
        Intent launchIntent = new Intent(context, activityClass);
        launchIntent.putExtras(intent.getExtras());
//        if(Build.VERSION.SDK_INT >= 16) {
//            TaskStackBuilderHelper.startActivities(context, activityClass, launchIntent);
//        } else {
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        launchIntent.putExtra(DetailActivity.EXTRA_EVENT_ID, getEventId(context, intent));
        context.startActivity(launchIntent);
//        }
    }
    //        @Override
//        protected void onPushReceive(Context context, Intent intent) {
//
//            if (isSearchNotification(context, intent))
//                onSearchPushReceived(context, intent);
//            else if(isApptentiveNotification(context, intent))
//                onApptentivePushReceived(context, intent);
//            else
//                super.onPushReceive(context, intent);
//
//            EventBus.getDefault().post(this);
//    }
//
//    private String getNotificationAlert(Context context, Intent intent) {
//        try {
//            JSONObject jsonObject = new JSONObject(intent.getExtras().getString(KEY_PUSH_DATA));
//            if(jsonObject.has(KEY_ALERT))
//            {
//                return jsonObject.getString(KEY_ALERT);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return "Notification";
//    }
//
//    private void savePush(Context context, Intent intent) {
//        final DWSNotification notification = new DWSNotification(getNotificationAlert(context, intent),
//                intent, System.currentTimeMillis());
//        notification.findAll(new DBFindListener<DWSNotification>() {
//            @Override
//            public void done(List<DWSNotification> results) {
//                if (results.size() == 50) {
//                    Collections.reverse(results);
//                    results.get(49).delete();
//                }
//                notification.save();
//            }
//        }, DWSNotification.class);
//    }
//
//    private boolean isSearchNotification(Context context, Intent intent) {
//        try {
//            JSONObject jsonObject = new JSONObject(intent.getExtras().getString(KEY_PUSH_DATA));
//            if(jsonObject.has(KEY_PUSH_TYPE)
//                    && jsonObject.getString(KEY_PUSH_TYPE).equals(PUSH_TYPE_SEARCH))
//            {
//                return true;
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    private void onGenericPushReceived(Context context, Intent intent, int notificationId) {
//        JSONObject pushData = null;
//
//        try {
//            pushData = new JSONObject(intent.getStringExtra(KEY_PUSH_DATA));
//        } catch (JSONException var7) {
//            DWSLog.e(TAG, "Unexpected JSONException when receiving push data: " + var7);
//        }
//
//        String action = null;
//        if(pushData != null) {
//            action = pushData.optString("action", (String)null);
//        }
//
//        if(action != null) {
//            Bundle notification = intent.getExtras();
//            Intent broadcastIntent = new Intent();
//            broadcastIntent.putExtras(notification);
//            broadcastIntent.setAction(action);
//            broadcastIntent.setPackage(context.getPackageName());
//            context.sendBroadcast(broadcastIntent);
//        }
//
//        Notification notification = this.getNotification(context, intent);
//        if(notification != null) {
//            NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//            try {
//                nm.notify(notificationId, notification);
//            } catch (SecurityException var6) {
//                notification.defaults = 5;
//                nm.notify(notificationId, notification);
//            }
//        }
//    }
//
//    private void onSearchPushReceived(Context context, Intent intent) {
//        onGenericPushReceived(context, intent, SEARCH_NOTIFICATION_ID);
//    }
//
//    private void onApptentivePushReceived(Context context, Intent intent) {
//        onGenericPushReceived(context, intent, APPTENTIVE_NOTIFICATION_ID);
//    }
//
//    @Override
//    protected void onPushDismiss(Context context, Intent intent) {
//        super.onPushDismiss(context, intent);
//        if (isSearchNotification(context, intent)) {
//            setSearchNotificationCount(context);
//        } else if(isApptentiveNotification(context, intent)) {
//            setApptentiveNotificationCount(context);
//        }
//    }
//
//    private void setSearchNotificationCount(Context context) {
//        this.setSearchNotificationCount(context, 0);
//    }
//
//    private IntPreference getSearchPreference(Context context) {
//        return new IntPreference(
//                context.getSharedPreferences(PREF_CONTEXT, Context.MODE_PRIVATE), PREF_KEY_SEARCH_COUNT);
//    }
//    private IntPreference getApptentivePreference(Context context) {
//        return new IntPreference(
//                context.getSharedPreferences(PREF_CONTEXT, Context.MODE_PRIVATE), PREF_KEY_APPTENTIVE_COUNT);
//    }
//
//    private void setSearchNotificationCount(Context context, int count) {
//        getSearchPreference(context).set(count);
//    }
//
//    private int getSearchNotificationCount(Context context) {
//        return getSearchPreference(context).get();
//    }
//
//    private Notification getSearchNotification(Context context, Intent intent) {
//
//        int count = getSearchNotificationCount(context);
//        count++;
//        setSearchNotificationCount(context, count);
//
//        boolean shouldSummarize = (count > 1);
//        if (shouldSummarize) {
//            try {
//                JSONObject json = new JSONObject(intent.getExtras().getString(KEY_PUSH_DATA));
//                String title = String.format(context.getString(
//                        R.string.notification_title_search_summary), count);
//                json.put(KEY_TITLE, title);
//                json.put(KEY_ALERT, context.getString(R.string.notification_alert_search_summary));
//                intent.putExtra(KEY_PUSH_DATA, json.toString());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        Notification notification = super.getNotification(context, intent);
//        if (shouldSummarize)
//            notification.number = count;
//
//
//        return lollipopfy(context, notification);
//    }
//
    public String getEventId(Context context, Intent intent) {
        try {
            JSONObject json = new JSONObject(intent.getExtras().getString(KEY_PUSH_DATA));
            if (json.has(KEY_EVENT_ID)) {
                return json.getString(KEY_EVENT_ID);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
//
//    public static Class<? extends Activity> getIndividualActivity(Context context, Intent intent) {
//        try {
//            JSONObject jsonObject = new JSONObject(intent.getStringExtra(KEY_PUSH_DATA));
//            if (jsonObject.has(KEY_PUSH_TYPE) && jsonObject.getString(KEY_PUSH_TYPE).equals(PUSH_TYPE_SEARCH)) {
//                intent.putExtra(VehicleSearchActivity.EXTRA_SEARCH_ID, jsonObject.getString(KEY_SEARCHID));
//                return VehicleSearchActivity.class;
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return MainActivity.class;
//    }

    @Override
    public Class<? extends Activity> getActivity(Context context, Intent intent) {
//        try {
//            JSONObject jsonObject = new JSONObject(intent.getStringExtra(KEY_PUSH_DATA));
//            if (jsonObject.has(KEY_PUSH_TYPE) && jsonObject.getString(KEY_PUSH_TYPE).equals(PUSH_TYPE_SEARCH)) {
//                if (getSearchNotificationCount(context) > 1) {
//                    return NotificationActivity.class;
//                }
//                intent.putExtra(VehicleSearchActivity.EXTRA_SEARCH_ID, jsonObject.getString(KEY_SEARCHID));
//                return VehicleSearchActivity.class;
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return MainActivity.class;
        return DetailActivity.class;
    }
}
