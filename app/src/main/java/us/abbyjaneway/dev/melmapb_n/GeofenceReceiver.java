package us.abbyjaneway.dev.melmapb_n;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created by abbyjaneway on 8/19/14.
 */
public class GeofenceReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals(action, MapsActivity.ACTION_GEOFENCE_ERROR)) {
            //handle geofence error
            System.out.println("GeofenceReceiver received error code");
        } else if(TextUtils.equals(action, MapsActivity.ACTION_GEOFENCES_ADDED) || TextUtils.equals(action, MapsActivity.ACTION_GEOFENCES_REMOVED)) {
            //handle geofence status
        } else if (TextUtils.equals(action, MapsActivity.ACTION_GEOFENCE_TRANSITION)) {
            //handle geofence transition
            FragmentManager fm = MapsActivity.getInstance().getFragmentManager();
            Fragment sdf = fm.findFragmentByTag("SingleDirection");
            if (sdf != null) {
                SingleDirectionFrag.swipeRight();
            }
            else {
                System.out.println("Error retrieving directions fragment");
            }
        } else {
            //handle error
        }
    }
}
