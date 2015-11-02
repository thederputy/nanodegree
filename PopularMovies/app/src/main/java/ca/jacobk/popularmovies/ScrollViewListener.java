package ca.jacobk.popularmovies;

import android.content.Context;
import android.widget.AbsListView;

import com.squareup.picasso.Picasso;

/**
 * Created by Jacob on 2015-09-12.
 *
 * Based off of sample: https://github.com/square/picasso/blob/master/picasso-sample/src/main/java/com/example/picasso/SampleScrollListener.java
 */
public class ScrollViewListener implements AbsListView.OnScrollListener {
    private final Context context;

    public ScrollViewListener(Context context) {
        this.context = context;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        final Picasso picasso = Picasso.with(context);
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            picasso.resumeTag(context);
        } else {
            picasso.pauseTag(context);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        // Do nothing.
    }
}
