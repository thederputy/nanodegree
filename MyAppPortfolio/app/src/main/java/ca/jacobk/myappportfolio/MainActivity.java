package ca.jacobk.myappportfolio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /// Disabling for now, no need to have an options menu at this point
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void showToastForString(int stringId) {
        Toast.makeText(this, String.format(getString(R.string.toast_format), getString(stringId)), Toast.LENGTH_SHORT).show();
    }

    private void startActivityForPackage(int packageId, int toast)
    {
        Intent intent = getPackageManager().getLaunchIntentForPackage(getString(packageId));
        if (intent != null) {
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(intent);
        } else {
            showToastForString(toast);
        }
    }

    public void buttonPopularMoviesOnClick(View v) {
        startActivityForPackage(R.string.pkg_popular_movies, R.string.toast_popular_movies);
    }

    public void buttonScoresOnClick(View v) {
        showToastForString(R.string.toast_scores);
    }

    public void buttonLibraryOnClick(View v) {
        showToastForString(R.string.toast_library);
    }

    public void buttonBuildOnClick(View v) {
        showToastForString(R.string.toast_build);
    }

    public void buttonReaderOnClick(View v) {
        showToastForString(R.string.toast_reader);
    }

    public void buttonCapstoneOnClick(View v) {
        showToastForString(R.string.toast_capstone);
    }
}
