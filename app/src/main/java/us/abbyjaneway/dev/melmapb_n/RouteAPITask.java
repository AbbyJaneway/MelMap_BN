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
 * Created by abbyjaneway on 8/19/14.
 */
public class RouteAPITask extends AsyncTask<URL, Integer, Long> {
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
            JSONArray resultsJsonArray = new JSONArray(jsonResults.toString());
            for (int i = 0; i < resultsJsonArray.length(); i++) {
                JSONObject route = resultsJsonArray.getJSONObject(i);
                String id = route.getString("id");
                String name = route.getString("name");
                String abbr = route.getString("short_name");
                String color = route.getString("color");
                String path = route.getString("path");
                MapsActivity.currentRoutes.add(new Route(id, name, abbr, color, path));
            }
        }
        catch (JSONException e) {
            System.out.println("Cannot process JSON results");
            e.printStackTrace();
        }
        return null;
    }
    protected void onPostExecute(Long result) {
        MapsActivity.getInstance().busQuery();
    }
}
