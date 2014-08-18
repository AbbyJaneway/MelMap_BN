package us.abbyjaneway.dev.melmapb_n;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.net.MalformedURLException;

/**
 * Created by abbyjaneway on 8/18/14.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String KEY_PREF_SPEAK = "pref_speak";
    public static final String KEY_PREF_BUSES = "pref_buses";
    private boolean startBuses;
    private Button goBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settingsfrag, container, false);
        startBuses = false;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        goBack = (Button) getActivity().findViewById(R.id.pbSettingsReturn);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.remove(getFragmentManager().findFragmentByTag("settings"));
                ft.commit();
            }
        });
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(KEY_PREF_SPEAK)) {
            Boolean value = sharedPreferences.getBoolean(key, false);
            //enable/disable spoken directions
            if (value == true) {
                MapsActivity.getInstance().turnTTSOn();
            } else if (value == false) {
                MapsActivity.getInstance().turnTTSOff();
            }
        }
        if(key.equals(KEY_PREF_BUSES)) {
            Boolean value = sharedPreferences.getBoolean(key, false);
            System.out.println(value);
            //if turned off
            if (value == false) {
                MapsActivity.currentBusTimer.cancel();
                MapsActivity.currentBusArray = null;
                MapsActivity.liveBusesOn = false;
            } //if turned on
            else if (value == true) {
                startBuses = true;
                MapsActivity.liveBusesOn = true;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        MapsActivity.settingsFragIsDisplayed = false;
        System.out.println("in onStop");
        if (startBuses) {
            try {
                MapsActivity.getInstance().getBusData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
