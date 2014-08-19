package us.abbyjaneway.dev.melmapb_n;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by abbyjaneway on 8/19/14.
 */
public class SaveDialogFragment extends DialogFragment{
    private MapsActivity mainA;
    private SQLiteDatabase placesDB;
    private SaveDialogFragment self;
    private ViewGroup container;

    static SaveDialogFragment newInstance() {
        return new SaveDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.save_dialogfrag, container, false);
        this.container = container;
        mainA = MapsActivity.getInstance();
        self = this;
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RadioGroup rg = (RadioGroup) getActivity().findViewById(R.id.rgPlaces);
        final EditText placeToSave = (EditText) getActivity().findViewById(R.id.etPlaceToSave);
        Button save = (Button) getActivity().findViewById(R.id.pbSavePlace);
        SQLiteOpenHelper pOH = new SQLOH(getActivity().getApplicationContext());
        placesDB = pOH.getWritableDatabase();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(placeToSave.getText().toString().length() == 0) {
                    //handle this
                } else {
                    String name = placeToSave.getText().toString();
                    switch (rg.getCheckedRadioButtonId()) {
                        case (R.id.rbStartPoint):
                            addPlace(name, mainA.getStartPoint());
                            break;
                        case (R.id.rbCurrentLoc):
                            //get most recent device location
                            addPlace(name, mainA.getCurrentLoc());
                            break;
                        case (R.id.rbDestination):
                            addPlace(name, mainA.getDestination());
                            break;
                        default:
                            break;
                    }
                }
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(container.getWindowToken(), 0);
                //remove fragment
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.remove(self);
                ft.commit();
            }
        });



    }

    private void addPlace(String name, LatLng coords) {
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Lat", coords.latitude);
        values.put("Lng", coords.longitude);
        placesDB.insert(SQLOH.TABLE_NAME, null, values);
        System.out.println("insert called");
    }
}
