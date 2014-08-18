package us.abbyjaneway.dev.melmapb_n;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by abbyjaneway on 8/18/14.
 */
public class UIFragmentBeta extends Fragment {
    private String destination;
    private Button pbTime;
    private ImageButton car;
    private ImageButton foot;
    private ImageButton bus;
    private TextView tvDestination;
    private ViewGroup container;
    private MapsActivity mainA;
    private static UIFragmentBeta instance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.container = container;
        instance = this;
        mainA = MapsActivity.getInstance();
        return inflater.inflate(R.layout.uifrag_beta, container, false);
    }

    protected static UIFragmentBeta getInstance() {
        return instance;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        destination = mainA.getDestinationString();
        pbTime = (Button) getActivity().findViewById(R.id.pbTime);
        car = (ImageButton) getActivity().findViewById(R.id.ibCar);
        foot = (ImageButton) getActivity().findViewById(R.id.ibWalk);
        bus = (ImageButton) getActivity().findViewById(R.id.ibBus);
        tvDestination = (TextView) getActivity().findViewById(R.id.tvDestination);
        tvDestination.setText(destination);
        //mainA.setGfStorage();
        final Calendar cal = Calendar.getInstance();
        //tell main activity to store current time for use in time picker
        mainA.setTravelTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainA.setModeOfTravel(1);
                mainA.planRoute();
            }
        });

        foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainA.setModeOfTravel(2);
                mainA.planRoute();
            }
        });

        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainA.setModeOfTravel(3);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(container.getWindowToken(), 0);
                //get current routes from API
                MapsActivity.currentRoutes = new ArrayList<Route>();
                StringBuilder sb = new StringBuilder(MapsActivity.DOUBLEMAP_BASE + "routes");
                try {
                    URL url = new URL(sb.toString());
                    new RouteAPITask().execute(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity().getApplicationContext(), "Calculating route...", Toast.LENGTH_LONG).show();
            }
        });

        pbTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view);
            }
        });
    }

    protected void setTimeText(String timeText) {
        pbTime.setText(timeText);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment timeFragment = new TimePickerFragment();
        timeFragment.show(getFragmentManager(), "timePicker");
    }
}
