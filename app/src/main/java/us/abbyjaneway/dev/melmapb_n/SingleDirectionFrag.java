package us.abbyjaneway.dev.melmapb_n;

import android.os.Bundle;
import android.app.Fragment;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;

/**
 * Created by abbyjaneway on 8/18/14.
 */
public class SingleDirectionFrag extends Fragment{
    private static TextView tv;
    private static int currentStep;
    private static MapsActivity ma;
    private static SingleDirectionFrag instance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.single_direction_frag, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        instance = this;
        ma = MapsActivity.getInstance();
        if (ma.modeOfTravel == 3) {
            currentStep = 1; //bus directions should show step end instead of step beginning
        } else {
            currentStep = 0;
        }
        tv = (TextView) getActivity().findViewById(R.id.tvSingle);
        tv.setText(MapsActivity.insArray.get(currentStep));
        if (MapsActivity.ttsReady) {
            int result = MapsActivity.tts.speak(tv.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            System.out.println("tts result: " + result);
        }
    }

    static SingleDirectionFrag getInstance() {
        return instance;
    }

    protected static void swipeLeft() {
        if (currentStep > 0) {
            currentStep--;
            String instructions = MapsActivity.insArray.get(currentStep);
            tv.setText(instructions);
            updateMap();
            if (MapsActivity.ttsReady) {
                MapsActivity.tts.speak(instructions, TextToSpeech.QUEUE_ADD, null);
            }
        } else {
            //do nothing
        }
    }

    protected static void swipeRight() {
        currentStep++;
        if (currentStep < MapsActivity.insArray.size() - 1) {
            String instructions = MapsActivity.insArray.get(currentStep);
            updateMap();
            tv.setText(instructions);
            if (MapsActivity.ttsReady) {
                MapsActivity.tts.speak(instructions, TextToSpeech.QUEUE_ADD, null);
            }
        }
        else if(currentStep == MapsActivity.insArray.size()) {
            tv.setText("Arrive at destination");
            if (MapsActivity.ttsReady) {
                MapsActivity.tts.speak("Arrive at destination", TextToSpeech.QUEUE_ADD, null);
            }
        } else {
            //do nothing
        }
    }

    private static void updateMap() {
        System.out.println("Updating map");
        ma.mMap.animateCamera(CameraUpdateFactory.newLatLng(ma.getTravelCoords(currentStep)));
    }
}
