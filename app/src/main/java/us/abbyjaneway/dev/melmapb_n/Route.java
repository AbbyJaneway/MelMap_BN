package us.abbyjaneway.dev.melmapb_n;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abbyjaneway on 8/18/14.
 */
public class Route extends Object{
    private String name;
    private String dID;
    private String abbr; //abbreviation
    private int color;
    private int frequency;
    private List<LatLng> polyline;

    public Route() {}

    public Route(String routeName) {
        if (routeName.contains("Green")) {
            name = "Green";
            dID = "229";
            frequency = 30;
        } else if (routeName.contains("Red")) {
            name = "Red";
            dID = "234";
            frequency = 60;
        } else if (routeName.contains("Purple")) {
            name = "Purple";
            dID = "236";
            frequency = 60;
        } else if (routeName.contains("Pink")) {
            name = "Pink";
            dID = "238";
            frequency = 60;
        } else if (routeName.contains("Blue")) {
            name = "Blue";
            dID = "240";
            frequency = 60;
        } else if (routeName.contains("Brown")) {
            name = "Brown";
            dID = "242";
            frequency = 60;
        } else if (routeName.contains("Yellow")) {
            name = "Yellow";
            dID = "246";
            frequency = 60;
        } else if (routeName.contains("Orange")) {
            name = "Orange";
            dID = "247";
            frequency = 60;
        } else if (routeName.contains("Lime")) {
            name = "Lime";
            dID = "249";
            frequency = 30;
        } else if (routeName.contains("Teal")) {
            name = "Teal";
            dID = "267";
            frequency = 60;
        } else if (routeName.contains("Aqua")) {
            name = "Aqua";
            dID = "252";
            frequency = 60;
        } else if (routeName.contains("Towers")) {
            name = "Redbird Express Blue";
            dID = "254";
            frequency = 10;
        } else if (routeName.contains("College Station")) {
            name = "Redbird Express Red";
            dID = "260";
            frequency = 30;
        } else if (routeName.contains("Heartland")) {
            name = "Heartland Express";
            dID = "211";
            frequency = 30;
        } else name = routeName;
    }
    public Route(String dID, String route, String abbreviation, String hexColor, String polylineToken) {
        this.abbr = abbreviation;
        if (route.contains("Green")) {
            name = "Green";
            dID = "229";
            frequency = 30;
        } else if (route.contains("Red")) {
            name = "Red";
            dID = "234";
            frequency = 60;
        } else if (route.contains("Purple")) {
            name = "Purple";
            dID = "236";
            frequency = 60;
        } else if (route.contains("Pink")) {
            name = "Pink";
            dID = "238";
            frequency = 60;
        } else if (route.contains("Blue")) {
            name = "Blue";
            dID = "240";
            frequency = 60;
        } else if (route.contains("Brown")) {
            name = "Brown";
            dID = "242";
            frequency = 60;
        } else if (route.contains("Yellow")) {
            name = "Yellow";
            dID = "246";
            frequency = 60;
        } else if (route.contains("Orange")) {
            name = "Orange";
            dID = "247";
            frequency = 60;
        } else if (route.contains("Lime")) {
            name = "Lime";
            dID = "249";
            frequency = 30;
        } else if (route.contains("Teal")) {
            name = "Teal";
            dID = "267";
            frequency = 60;
        } else if (route.contains("Aqua")) {
            name = "Aqua";
            dID = "252";
            frequency = 60;
        } else if (route.contains("Towers")) {
            name = "Redbird Express Blue";
            dID = "254";
            frequency = 10;
        } else if (route.contains("College Station")) {
            name = "Redbird Express Red";
            dID = "260";
            frequency = 30;
        } else if (route.contains("Heartland")) {
            name = "Heartland Express";
            dID = "211";
            frequency = 30;
        } else name = route;

        //color = Integer.parseInt(hexColor, 16) & 0xFF;
        color = Color.parseColor("#" + hexColor);
        String delims = "[,]+";
        String polylineCoords = polylineToken.substring(1, polylineToken.length()-1);
        polyline = new ArrayList<LatLng>();
        String[] coords = polylineCoords.split(delims);
        for (int i = 0; i < coords.length - 1; i++) {
            LatLng point = new LatLng(Float.valueOf(coords[i]), Float.valueOf(coords[i + 1]));
            polyline.add(point);
            i++;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    protected String getAbbr() { return abbr; }

    protected int getColor() {
        return color;
    }

    protected String getRoute() {
        return name;
    }

    protected List<LatLng> getPolyline() {
        return polyline;
    }

    protected String getdID() {
        return dID;
    }

    protected int getFrequency() {
        return frequency;
    }


    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(o == this) {
            return true;
        }

        if(o instanceof Route) {
            return this.name.equals(((Route)o).name);
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getDescription() {
        return "Route id " + dID + " name " + name + "(" + abbr + ")";
    }
}
