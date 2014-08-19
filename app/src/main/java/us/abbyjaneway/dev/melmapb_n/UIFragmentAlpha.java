package us.abbyjaneway.dev.melmapb_n;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by abbyjaneway on 8/19/14.
 */
public class UIFragmentAlpha extends Fragment{
    static private AutoCompleteTextView etSearch;
    private Button pbGo;
    private ViewGroup container;
    private Spinner savedPlaces;
    private Context context;
    ArrayList<String> placeNames;
    ArrayList<SavedPlace> placesArray;
    private boolean savedPlaceSelected;
    SavedPlace selectedPlace;
    private MapsActivity mainA;
    SQLiteOpenHelper pOH;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getActivity().getApplicationContext();
        this.container = container;
        mainA = MapsActivity.getInstance();
        return inflater.inflate(R.layout.uifrag_alpha, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        etSearch = (AutoCompleteTextView) getActivity().findViewById(R.id.etSearch);
        etSearch.setAdapter(new PlacesAutoCompleteAdapter(context, R.layout.list_item));
        savedPlaces = (Spinner) getActivity().findViewById(R.id.spinnerSavedPlaces);
        pbGo = (Button) getActivity().findViewById(R.id.pbGo);
        pbGo.setEnabled(false);
        pOH = new SQLOH(context);
        SQLiteDatabase placesDB = pOH.getReadableDatabase();
        String[] FROM = { "*" };
        Cursor c = placesDB.query(SQLOH.TABLE_NAME, FROM, null, null, null, null, null);
        placesArray = new ArrayList<SavedPlace>();
        placeNames = new ArrayList<String>();
        placeNames.add("Choose a Favorite...");
        if (c.moveToFirst()) {
            while(c.moveToNext()) {
                SavedPlace sp = new SavedPlace(c.getString(1), c.getFloat(2), c.getFloat(3));
                placeNames.add(sp.getName());
                placesArray.add(sp);
            } }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, placeNames);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        savedPlaces.setAdapter(adapter);
        c.close();

        etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
                String str = (String) av.getItemAtPosition(pos);
                etSearch.setText(str);
                pbGo.setEnabled(true);
            }
        });


        pbGo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (savedPlaceSelected) {
                    mainA.setDestination(selectedPlace.getLatLng());
                } else {
                    // get user input, send to place search method to get coords
                    FocusHelper.releaseFocus(etSearch);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(container.getWindowToken(), 0);
                    String input = etSearch.getText().toString();
                    try {
                        mainA.searchResults(input);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        savedPlaces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                if (pos != 0) {
                    System.out.println("in onItemSelected");
                    etSearch.setAdapter(null); //don't request autofill results from saved place name
                    selectedPlace = placesArray.get(pos - 1);
                    savedPlaceSelected = true;
                    String placeName = selectedPlace.getName();
                    etSearch.setText(placeName);
                    mainA.setDestinationString(placeName);
                    pbGo.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                etSearch.setText("");
                selectedPlace = null;
                savedPlaceSelected = false;
            }
        });
    }

    @Override
    public void onStop() {
        if (pOH != null) {
            pOH.close();
        }
        super.onStop();
    }
}

class FocusHelper {
    protected static void releaseFocus(View view) {
        ViewParent parent = view.getParent();
        ViewGroup group;
        View child;
        while (parent != null) {
            if (parent instanceof ViewGroup) {
                group = (ViewGroup) parent;
                for (int i = 0; i < group.getChildCount(); i++) {
                    child = group.getChildAt(i);
                    if(child != view && child.isFocusable())
                        child.requestFocus();
                }
            }
            parent = parent.getParent();
        }
    }
}
