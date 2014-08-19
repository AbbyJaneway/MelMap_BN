package us.abbyjaneway.dev.melmapb_n;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

/**
 * Created by abbyjaneway on 8/19/14.
 */
public class ReceiveTransitionsIntentService extends IntentService{
    public ReceiveTransitionsIntentService() {
        super("ReceiveTransitionsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //create local broadcast intent
        Intent broadcastIntent = new Intent();
        broadcastIntent.addCategory(MapsActivity.CATEGORY_LOCATION_SERVICES);

        if (LocationClient.hasError(intent)) {
            int errorCode = LocationClient.getErrorCode(intent);
            System.out.println("ReceiveTransitionsIntentService, Location services error: " + Integer.toString(errorCode));
        } else {
            int transitionType = LocationClient.getGeofenceTransition(intent);
            if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {
                //we are only interested in this type
                broadcastIntent.setAction(MapsActivity.ACTION_GEOFENCE_TRANSITION);
                LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);

//                List<Geofence> triggerList = LocationClient.getTriggeringGeofences(intent);
//                String[] triggerIds = new String[triggerList.size()];
//
//                for(int i = 0; i < triggerIds.length; i++) {
//                    triggerIds[i] = triggerList.get(i).getRequestId();
//                    System.out.println(triggerIds[i].toString());
//                }
//
//                FragmentManager fm = MainActivity.getInstance().getFragmentManager();
//                Fragment sdf = fm.findFragmentByTag("SingleDirection");
//                if (sdf != null) {
//                SingleDirectionFrag.getInstance().swipeRight();
//                }
//                else {
//                    System.out.println("Error retrieving directions fragment");

            }
        }
    }
}
