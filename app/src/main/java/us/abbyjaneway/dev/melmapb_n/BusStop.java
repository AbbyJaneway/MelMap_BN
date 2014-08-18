package us.abbyjaneway.dev.melmapb_n;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by abbyjaneway on 8/18/14.
 */
public class BusStop extends Object {
    private float lat;
    private float lng;
    private String route;
    private String stopID;
    private String stopName;
    private int dStopID;
    private int eta;

    public BusStop(float lat, float lng, String route, String stopID, String stopName, int dStopID) {
        this.lat = lat;
        this.lng = lng;
        this.route = route;
        this.stopID = stopID;
        this.dStopID = dStopID;
        String avenue = stopName.replaceAll(" Ave$| Ave ", " Avenue ");
        String driveNotDoctor = avenue.replaceAll(" Dr$| Dr ", " Drive ");
        this.stopName = driveNotDoctor.replaceAll(" St$| St ", " Street ");
    }

    public BusStop(LatLng ll, int dStopID, String stopName) {
        this.lat = (float) ll.latitude;
        this.lng = (float) ll.longitude;
        this.dStopID = dStopID;
        this.stopName = stopName;
    }

    protected float getLat() {
        return lat;
    }

    protected float getLng() {
        return lng;
    }

    protected LatLng getLatLng() {
        return new LatLng(lat, lng);
    }

    protected String getRoute() {
        return route;
    }

    protected String getStopID() {
        return stopID;
    }

    protected String getStopName() {
        return stopName;
    }

    protected int getDStopID() {
        return dStopID;
    }

    protected void setEta(int eta) {
        this.eta = eta;
    }

    protected int getEta() {
        return eta;
    }
}
