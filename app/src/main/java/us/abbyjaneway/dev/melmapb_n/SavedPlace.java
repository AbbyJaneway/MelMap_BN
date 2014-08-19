package us.abbyjaneway.dev.melmapb_n;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by abbyjaneway on 8/19/14.
 */
public class SavedPlace {
    private String name;
    private double latitude;
    private double longitude;
    private LatLng latLng;

    public SavedPlace(String name, float lat, float lng) {
        this.name = name;
        this.latitude = (double) lat;
        this.longitude = (double) lng;
        this.latLng = new LatLng(this.latitude, this.longitude);
    }

    protected String getName() {
        return this.name;
    }

    protected LatLng getLatLng() {
        return this.latLng;
    }
}
