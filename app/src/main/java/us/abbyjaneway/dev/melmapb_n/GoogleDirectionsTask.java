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
public class GoogleDirectionsTask extends AsyncTask<URL, Integer, Long>{
    private String tripDist;

    @Override
    protected Long doInBackground(URL... urls) {
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
            System.out.println("Error processing Directions API URL");
            return null;
        } catch (IOException e) {
            System.out.println("Error connecting to Directions API");
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
            JSONArray resultsJsonArray = jsonObj.getJSONArray("routes");
            JSONArray legs = resultsJsonArray.getJSONObject(0).getJSONArray("legs");
            tripDist = legs.getJSONObject(0).getJSONObject("distance").getString("text");
            JSONArray steps = legs.getJSONObject(0).getJSONArray("steps");

            // Extract the directions from the results
            for (int i = 0; i < steps.length(); i++) {
                JSONObject routeStep = steps.getJSONObject(i);
                JSONObject polyline = routeStep.getJSONObject("polyline");
                JSONObject duration = routeStep.getJSONObject("duration");
                JSONObject distance = routeStep.getJSONObject("distance");
                JSONObject start = routeStep.getJSONObject("start_location");
                JSONObject end = routeStep.getJSONObject("end_location");
                String htmlDirections = routeStep.getString("html_instructions");
                MapsActivity.directions.add(new RouteStep(i, start.getDouble("lat"), start.getDouble("lng"), end.getDouble("lat"), end.getDouble("lng"), polyline.getString("points"), duration.getString("text"), htmlDirections, distance.getString("text")));
            }
        } catch (JSONException e) {
            System.out.println("Cannot process JSON results");
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Long result) {
        MapsActivity.getInstance().loadRouteInfo(tripDist);
    }
}
