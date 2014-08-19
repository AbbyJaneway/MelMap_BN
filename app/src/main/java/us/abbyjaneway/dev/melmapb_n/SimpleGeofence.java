package us.abbyjaneway.dev.melmapb_n;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by abbyjaneway on 8/19/14.
 */
public class SimpleGeofence {

    private final String gId;
    private final double latitude;
    private final double longitude;
    private final float radius;
    private long expiration;
    private int transitionType;
    private LatLng latLng;

    public SimpleGeofence(String geofenceId, double lat, double lng, float rad, long expiry, int transition) {
        this.gId = geofenceId;
        this.latitude = lat;
        this.longitude = lng;
        this.radius = rad;
        this.expiration = expiry;
        this.transitionType = transition;
        this.latLng = new LatLng(lat, lng);
    }

    /* getters */
    protected String getgId() {
        return gId;
    }

    protected double getLatitude() {
        return latitude;
    }

    protected double getLongitude() {
        return longitude;
    }

    protected LatLng getLatLng() {
        return latLng;
    }

    protected float getRadius() {
        return radius;
    }

    protected long getExpiration() {
        return expiration;
    }

    protected int getTransitionType() {
        return transitionType;
    }

    protected Geofence toGeofence() {
        //create Geofence object for Google Location Services
        return new Geofence.Builder().setRequestId(gId).setTransitionTypes(transitionType).setCircularRegion(latitude, longitude, radius).setExpirationDuration(expiration).build();
    }
}
