package us.abbyjaneway.dev.melmapb_n;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by abbyjaneway on 8/19/14.
 */
public class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable{
    ArrayList<String> resultList = null;


    public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            HttpURLConnection conn = null;
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                StringBuilder jsonResults = new StringBuilder();

                if (constraint != null && constraint.length() > 3) {
                    if (resultList != null) {
                        resultList.clear();
                    }
                    // Retrieve the autocomplete results.
                    try {
                        //MainActivity.autocomplete(constraint.toString());
                        StringBuilder sb = new StringBuilder(MapsActivity.PLACES_API_BASE
                                + MapsActivity.TYPE_AUTOCOMPLETE + MapsActivity.OUT_JSON);
                        sb.append("?sensor=true&key=" + MapsActivity.API_KEY);
                        sb.append("&components=country:us&location=" + MapsActivity.BN.latitude + "," + MapsActivity.BN.longitude);
                        sb.append("&radius=50");
                        sb.append("&input=" + URLEncoder.encode(constraint.toString(), "utf8"));
                        System.out.println("input = " + constraint.toString());
                        URL url = new URL(sb.toString());
                        conn = (HttpURLConnection) url.openConnection();
                        InputStreamReader in = new InputStreamReader(conn.getInputStream());

                        // Load the results into a StringBuilder
                        int read;
                        char[] buff = new char[1024];
                        while ((read = in.read(buff)) != -1) {
                            jsonResults.append(buff, 0, read);
                        }

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return null;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        System.out.println("Error processing URL");
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error connecting to API");
                        return null;
                    }finally {
                        if (conn != null) {
                            conn.disconnect();
                        }
                    }
                    try {
                        // Create a JSON object hierarchy from the results
                        JSONObject jsonObj = new JSONObject(jsonResults.toString());
                        JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

                        // Extract the Place descriptions from the results
                        resultList = new ArrayList<String>(predsJsonArray.length());
                        for (int i = 0; i < predsJsonArray.length(); i++) {
                            String description = predsJsonArray.getJSONObject(i).getString("description");
                            if (description.length() > 16) {
                                description = description.substring(0, description.length() - 15); //cut out "United States"
                            }
                            System.out.println(description);
                            resultList.add(description);
                        }
                    } catch (JSONException e) {
                        System.out.println("Cannot process JSON results");
                        e.printStackTrace();
                    }

                    // Assign the data to the FilterResults

                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }
}
