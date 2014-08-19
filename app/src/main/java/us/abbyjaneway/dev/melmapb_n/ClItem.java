package us.abbyjaneway.dev.melmapb_n;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by abbyjaneway on 8/19/14.
 */
public class ClItem implements ClusterItem {
    private final LatLng mPosition;

    public ClItem(LatLng pos) {
        mPosition = pos;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}
