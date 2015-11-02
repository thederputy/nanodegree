package ca.jacobk.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    GridViewAdapter mAdapter;
    SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_posters);
        gridView.setHorizontalSpacing(0);
        mAdapter = new GridViewAdapter(getContext());
        gridView.setAdapter(mAdapter);
        gridView.setColumnWidth(mAdapter.getColumnWidth());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieData movie = mAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, movie);
                startActivity(intent);
            }
        });

        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                refreshData();
            }
        };

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(prefListener);

        refreshData();

        return rootView;
    }

    public void refreshData() {
        FetchMovieDataTask task = new FetchMovieDataTask();
        task.execute();
    }

    public class FetchMovieDataTask extends AsyncTask<Void, Void, MovieData[]> {

        private MovieData[] getMovieDataFromJson(String jsonString) throws JSONException {
            JSONObject json = new JSONObject(jsonString);
            JSONArray results = json.getJSONArray("results");

            MovieData[] movieData = new MovieData[results.length()];
            for (int i = 0; i < results.length(); i++) {
                movieData[i] = new MovieData(results.getJSONObject(i));
            }

            return movieData;
        }

        @Override
        protected MovieData[] doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieResponseJsonStr = null;

            try {
                // Construct the URL for TMDB  query
                Resources r = getResources();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String sortBy = prefs.getString(r.getString(R.string.pref_key_sort), r.getString(R.string.tmdb_param_sort_by_popularity_desc));
                Uri builtUri = Uri.parse(r.getString(R.string.tmdb_discover_base_url)).buildUpon()
                        .appendQueryParameter(r.getString(R.string.tmdb_param_sort_by), sortBy)
                        .appendQueryParameter(r.getString(R.string.tmdb_param_api_key), r.getString(R.string.tmdb_API_key))
                        .build();

                URL url = new URL(builtUri.toString());

                // Create the request to TMDB, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    movieResponseJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    movieResponseJsonStr = null;
                }
                movieResponseJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the movie data, there's no point in attempting to parse it
                movieResponseJsonStr = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }

                /// Parse out the URLs to load images for
                if (movieResponseJsonStr != null) {
                    try {
                        return getMovieDataFromJson(movieResponseJsonStr);
                    } catch (JSONException jse) {
                      return null;
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(MovieData[] results) {
            if (results != null) {
                mAdapter.clear();
                mAdapter.addAll(results);
            }
        }
    }
}
