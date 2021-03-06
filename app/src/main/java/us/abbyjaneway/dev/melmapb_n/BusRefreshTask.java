package us.abbyjaneway.dev.melmapb_n;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by abbyjaneway on 8/18/14.
 */
public class BusRefreshTask extends AsyncTask<URL, Integer, Long> {
    @Override
    protected Long doInBackground(URL... urls) {
        //get data
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            conn = (HttpURLConnection) urls[0].openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            System.out.println("Error processing Places Search API URL");
            return null;
        } catch (IOException e) {
            System.out.println("Error connecting to Places Autocomplete API");
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        try {
            // Create a JSON object hierarchy from the results
            JSONArray resultsJsonArray = new JSONArray(jsonResults.toString());
            for(int i = 0; i < resultsJsonArray.length(); i++) {
                JSONObject bus = resultsJsonArray.getJSONObject(i);
                //iterate through bus array to check if this bus already exists
                for(int j = 0; j <MapsActivity.currentBusArray.size(); j++) {
                    Bus b = MapsActivity.currentBusArray.get(i);
                    if(bus.getInt("id") == MapsActivity.currentBusArray.get(i).id) {
                        b.updateLoc(bus.getDouble("lat"), bus.getDouble("lon"));
                        b.lastStop = bus.getInt("lastStop");
                    }
                    else { //add new bus
                        int dId = bus.getInt("id");
                        double lat = bus.getDouble("lat");
                        double lon = bus.getDouble("lon");
                        int dRoute = bus.getInt("route");
                        int lastStop = bus.getInt("lastStop");
                        Bus mBus = new Bus(dId, lat, lon, dRoute, lastStop);
                        MapsActivity.currentBusArray.add(mBus);
                    }
                }

            }
        }
        catch (JSONException e) {
            System.out.println("Cannot process JSON results");
            e.printStackTrace();
        }
        return null;
    }
    protected void onPostExecute(Long result) {
        MapsActivity.displayBuses();
    }
}
