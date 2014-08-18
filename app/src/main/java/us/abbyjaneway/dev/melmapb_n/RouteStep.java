package us.abbyjaneway.dev.melmapb_n;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by abbyjaneway on 8/18/14.
 */
public class RouteStep extends Object {
    protected int id;
    protected LatLng start;
    protected LatLng end;
    protected String polyline;
    protected String instructions;
    protected String duration;
    protected String distance;

    public RouteStep(int stepNum, double sLat, double sLng, double eLat, double eLng, String pLine, String dur, String ins, String dist) {
        id = stepNum;
        polyline = pLine;
        duration = dur;
        instructions = ins;
        distance = dist;
        start = new LatLng(sLat, sLng);
        end = new LatLng(eLat, eLng);
        // end = new LatLng(Math.round(eLat * 100000)/100000, Math.round(eLng * 100000)/100000); //round to correct for margin of error in gps user location
    }
}
