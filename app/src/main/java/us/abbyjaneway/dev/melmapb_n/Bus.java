package us.abbyjaneway.dev.melmapb_n;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by abbyjaneway on 8/18/14.
 */
public class Bus extends Object {
    protected int id;
    protected double lat;
    protected double lon;
    protected LatLng location;
    private final LatLng mPosition;
    protected String route;
    protected int lastStop;
    protected BitmapDescriptor mapIcon;
    protected MarkerOptions mapMarker;

    public Bus(int dId, double latitude, double longitude, int dRoute,
               int dLastStop) {
        id = dId;
        lat = latitude;
        lon = longitude;
        location = new LatLng(Double.valueOf(lat), Double.valueOf(lon));
        mPosition = location;
        // convert Doublemap's route code to route String and assign map icon
        switch (dRoute) {
            case (211):
                route = "hx";
                mapIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_hx);
                break;
            case (229):
                route = "Green";
                mapIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_a);
                break;
            case (234):
                route = "Red";
                mapIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_b);
                break;
            case (236):
                route = "Purple";
                mapIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_c);
                break;
            case (238):
                route = "Pink";
                mapIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_d);
                break;
            case (240):
                route = "Blue";
                mapIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_e);
                break;
            case (242):
                route = "Brown";
                mapIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_f);
                break;
            case (246):
                route = "Yellow";
                mapIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_g);
                break;
            case (247):
                route = "Orange";
                mapIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_h);
                break;
            case (249):
                route = "Lime";
                mapIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_i);
                break;
            case (252):
                route = "Aqua";
                mapIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_k);
                break;
            case (254):
                route = "rxb";
                mapIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_rxb);
                break;
            case (260):
                route = "rxr";
                mapIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_rxr);
                break;
            case (267):
                route = "Teal";
                mapIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_j);
                break;
            case (276):
                route = "Brown";
                mapIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_f);
                break;
            default:
                mapIcon = BitmapDescriptorFactory.fromResource(R.drawable.busdefaulticon);
                break;
        }
        lastStop = Integer.valueOf(dLastStop);
        mapMarker = new MarkerOptions()
                .position(this.location)
                .title(String.valueOf(this.id))
                .snippet(
                        "Route " + this.route + ", last stopped at: "
                                + this.lastStop).alpha(0.8f).icon(this.mapIcon);
    }

    protected void updateLoc(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        this.location = new LatLng(Double.valueOf(lat), Double.valueOf(lon));
        this.mapMarker = new MarkerOptions()
                .position(this.location)
                .title(String.valueOf(this.id))
                .snippet(
                        "Route " + this.route + ", last stopped at: "
                                + this.lastStop).alpha(0.8f).icon(this.mapIcon);
    }


    public LatLng getPosition() {
        return mPosition;
    }
}
