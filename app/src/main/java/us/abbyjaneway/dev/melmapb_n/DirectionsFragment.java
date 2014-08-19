package us.abbyjaneway.dev.melmapb_n;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by abbyjaneway on 8/19/14.
 */
public class DirectionsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.directions_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.llDirections);
        Button pbBack = new Button(getActivity().getApplicationContext());
        pbBack.setText("Back");
        layout.addView(pbBack);
        for (String s : MapsActivity.insArray) {
            TextView tv = new TextView(MapsActivity.getInstance().getApplicationContext());
            tv.setPadding(3,3,1,3);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(18);
            tv.setText(s);
            layout.addView(tv);
        }
        pbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapsActivity.getInstance().hideDirectionsPane();
            }
        });
    }
}
