package us.abbyjaneway.dev.melmapb_n;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by abbyjaneway on 8/19/14.
 */
public class SimpleGeofenceStorage {
    //pref keys
    public static final String KEY_LATITUDE = "us.abbyjaneway.dev.melmapb_n.KEY_LATITUDE";
    public static final String KEY_LONGITUDE = "us.abbyjaneway.dev.melmapb_n.KEY_LONGITUDE";
    public static final String KEY_RADIUS = "us.abbyjaneway.dev.melmapb_n.KEY_RADIUS";
    public static final String KEY_EXPIRATION = "us.abbyjaneway.dev.melmapb_n.KEY_EXPIRATION_DURATION";
    public static final String KEY_TRANSITION = "us.abbyjaneway.dev.melmapb_n.KEY_TRANSITION_TYPE";
    public static final String KEY_PREFIX = "us.abbyjaneway.dev.melmapb_n.GEOFENCE_KEY";
    //test values
    public static final long INVALID_LONG_VALUE = -9991;
    public static final float INVALID_FLOAT_VALUE = -999.0f;
    public static final int INVALID_INT_VALUE = -999;

    private final SharedPreferences mPrefs;
    //create SharedPreferences with private access
    protected SimpleGeofenceStorage(Context ctx) {
        mPrefs = ctx.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
    }

    //getter method returns stored geofences by their id or return null
    protected SimpleGeofence getGeofence(String id) {
        double lat = mPrefs.getFloat(getGeofenceFieldKey(id, KEY_LATITUDE), INVALID_FLOAT_VALUE);
        double lng = mPrefs.getFloat(getGeofenceFieldKey(id, KEY_LONGITUDE), INVALID_FLOAT_VALUE);
        float rad = mPrefs.getFloat(getGeofenceFieldKey(id, KEY_RADIUS), INVALID_FLOAT_VALUE);
        long expiry = mPrefs.getLong(getGeofenceFieldKey(id, KEY_EXPIRATION), INVALID_LONG_VALUE);
        int transition = mPrefs.getInt(getGeofenceFieldKey(id, KEY_TRANSITION), INVALID_INT_VALUE);

        if ( lat != INVALID_FLOAT_VALUE && lng != INVALID_FLOAT_VALUE && rad != INVALID_FLOAT_VALUE && expiry != INVALID_LONG_VALUE && transition != INVALID_INT_VALUE) {
            return new SimpleGeofence(id, lat, lng, rad, expiry, transition);
        } else {
            return null;
        }
    }

    protected void setGeofence(String id, SimpleGeofence geofence) {
        /*
             * Get a SharedPreferences editor instance. Among other
             * things, SharedPreferences ensures that updates are atomic
             * and non-concurrent
             */
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putFloat(getGeofenceFieldKey(id, KEY_LATITUDE), (float) geofence.getLatitude());
        editor.putFloat(getGeofenceFieldKey(id, KEY_LONGITUDE), (float) geofence.getLongitude());
        editor.putFloat(getGeofenceFieldKey(id, KEY_RADIUS), geofence.getRadius());
        editor.putLong(getGeofenceFieldKey(id, KEY_EXPIRATION), geofence.getExpiration());
        editor.putInt(getGeofenceFieldKey(id, KEY_TRANSITION), geofence.getTransitionType());
        editor.commit();
    }

    protected void clearGeofence(String id) {
        //removes geofence from storage
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove(getGeofenceFieldKey(id, KEY_LATITUDE));
        editor.remove(getGeofenceFieldKey(id, KEY_LONGITUDE));
        editor.remove(getGeofenceFieldKey(id, KEY_RADIUS));
        editor.remove(getGeofenceFieldKey(id, KEY_EXPIRATION));
        editor.remove(getGeofenceFieldKey(id, KEY_TRANSITION));
        editor.commit();
    }

    private String getGeofenceFieldKey(String id, String field) {
        return KEY_PREFIX + "_" + id + "_" + field;
    }
}
