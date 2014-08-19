package us.abbyjaneway.dev.melmapb_n;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by abbyjaneway on 8/19/14.
 */
public class GooglePlaceTask extends AsyncTask<URL, Integer, Long> {

    LatLng searchResult;

    @Override
    protected Long doInBackground(URL...urls) {

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
            System.out.println("Error connecting to Places Search API");
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray results = jsonObj.getJSONArray("results");

            // Extract the Place lat/lng from the results
            JSONObject geometry = results.getJSONObject(0).getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            searchResult = new LatLng(Double.valueOf(location.get("lat").toString()), Double.valueOf(location.get("lng").toString()));
        } catch (JSONException e) {
            System.out.println("Cannot process JSON results");
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(Long result) {
        MapsActivity.getInstance().setDestination(searchResult);
    }
}
