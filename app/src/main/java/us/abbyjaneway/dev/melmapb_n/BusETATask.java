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
public class BusETATask extends AsyncTask<URL, Integer, Long> {
    private MapsActivity mainA;

    @Override
    protected Long doInBackground(URL... urls) {
        mainA = MapsActivity.getInstance();
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
            System.out.println("Error processing Doublemap URL");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.out.println("Error connecting to Doublemap API");
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        try {
            // Create a JSON object hierarchy from the results
            JSONObject results = new JSONObject(jsonResults.toString());
            JSONObject resultsObject = results.getJSONObject("etas");
            if (parseJSON(resultsObject, mainA.getOn)) {
                //now get eta for getOff
                conn = null;
                jsonResults = new StringBuilder();
                try {
                    conn = (HttpURLConnection) urls[1].openConnection();
                    InputStreamReader in = new InputStreamReader(conn.getInputStream());

                    int read;
                    char[] buff = new char[1024];
                    while ((read = in.read(buff)) != -1) {
                        jsonResults.append(buff, 0, read);
                    }
                } catch (MalformedURLException e) {
                    System.out.println("Error processing Doublemap URL");
                    return null;
                } catch (IOException e) {
                    System.out.println("Error connecting to Doublemap API");
                    e.printStackTrace();
                    return null;
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
                try {
                    // Create a JSON object hierarchy from the results
                    JSONObject results2 = new JSONObject(jsonResults.toString());
                    if (parseJSON(results2, mainA.getOff)){
                        return null; //end task
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            System.out.println("Cannot process JSON results");
            e.printStackTrace();
        }
        return null;
    }

    private boolean parseJSON(JSONObject results, BusStop stop) {
        try {
            System.out.println(results.toString());
            JSONObject stopObject = results.getJSONObject(String.valueOf(stop.getDStopID()));
            JSONArray etasArray = stopObject.getJSONArray("etas");
            String routeId = mainA.route.getdID();
            for (int i = 0; i < etasArray.length(); i++) {
                System.out.println("Comparing " + etasArray.getJSONObject(i).getString("route") + " to " + routeId);
                if (etasArray.getJSONObject(i).getString("route").equals(routeId)) {

                    stop.setEta(etasArray.getJSONObject(i).getInt("avg"));
                    System.out.println("ETA set: " + stop.getEta());
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    protected void onPostExecute(Long result) {
        MapsActivity.getInstance().loadBusDirections();
    }
}
