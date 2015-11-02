package ca.jacobk.popularmovies;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob on 2015-09-12.
 *
 * Based off of sample here: https://github.com/square/picasso/blob/master/picasso-sample/src/main/java/com/example/picasso/SampleGridViewAdapter.java
 */
final class GridViewAdapter extends BaseAdapter {
    private final Context context;
    private final List<MovieData> data = new ArrayList<>();

    private LayoutInflater inflater;
    private final int imageWidth;
    private final int imageHeight;
    private final float baseWidth = 400;

    @SuppressWarnings("deprecation")
    public GridViewAdapter(Context context) {
        this.context = context;

        inflater = LayoutInflater.from(context);

        // Ensure we get a different ordering of images on each run.
//        Collections.addAll(data, Data.URLS);
//        Collections.shuffle(data);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        int screenWidth;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }
        else
        {
            screenWidth = display.getWidth();
        }

        /// int here makes imageWidth 409, which makes 5 columns on the Nexus 9
        float numColumns = Math.round((screenWidth / baseWidth));
        imageWidth = (int)Math.floor(screenWidth / numColumns);
        imageHeight = (int)(imageWidth * 1.5);
    }

    public int getColumnWidth() {
        return imageWidth;
    }

    public void clear() {
        data.clear();
    }

    public void addAll(MovieData[] data) {
        for (MovieData movie : data) {
            this.data.add(movie);
        }

        notifyDataSetChanged();
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_movie_poster, parent, false);
        }

        /// Get the MovieData object for the current position.
        MovieData movieData = getItem(position);
        String url = MovieData.PosterURL + movieData.posterPath;

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context)
                .load(url)
//                .fit()
                .resize(imageWidth, imageHeight)
//                .centerInside()
                .centerCrop()
                .tag(context)
                .into((ImageView) convertView);
        //                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.error)

        return convertView;
    }

    @Override public int getCount() {
        return data.size();
    }

    @Override public MovieData getItem(int position) {
        return data.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }
}