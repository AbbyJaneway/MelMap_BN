package us.abbyjaneway.dev.melmapb_n;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by abbyjaneway on 8/19/14.
 */
public class PlaceholderFrag extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.blankfrag, container, false);
    }
}
