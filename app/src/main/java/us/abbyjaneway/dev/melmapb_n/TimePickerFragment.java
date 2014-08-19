package us.abbyjaneway.dev.melmapb_n;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

/**
 * Created by abbyjaneway on 8/19/14.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //use the current time as the default values for the picker
        int hour = MapsActivity.getInstance().getTravelTime()[0];
        int minute = MapsActivity.getInstance().getTravelTime()[1];

        //create a new instance of TPD and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
        MapsActivity.getInstance().setTravelTime(hourOfDay, minute, false);
        UIFragmentBeta.getInstance().setTimeText(MapsActivity.getInstance().getTravelTimeString());
    }
}
