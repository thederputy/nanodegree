package ca.jacobk.myappportfolio;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonPopularMoviesOnClick(View v) {
        Resources res = getResources();
        Toast.makeText(this, String.format(res.getString(R.string.toast_format), getString(R.string.toast_popular_movies)), Toast.LENGTH_SHORT).show();
    }

    public void buttonScoresOnClick(View v) {
        Resources res = getResources();
        Toast.makeText(this, String.format(res.getString(R.string.toast_format), getString(R.string.toast_scores)), Toast.LENGTH_SHORT).show();
    }

    public void buttonLibraryOnClick(View v) {
        Resources res = getResources();
        Toast.makeText(this, String.format(res.getString(R.string.toast_format), getString(R.string.toast_library)), Toast.LENGTH_SHORT).show();
    }

    public void buttonBuildOnClick(View v) {
        Resources res = getResources();
        Toast.makeText(this, String.format(res.getString(R.string.toast_format), getString(R.string.toast_build)), Toast.LENGTH_SHORT).show();
    }

    public void buttonReaderOnClick(View v) {
        Resources res = getResources();
        Toast.makeText(this, String.format(res.getString(R.string.toast_format), getString(R.string.toast_reader)), Toast.LENGTH_SHORT).show();
    }

    public void buttonCapstoneOnClick(View v) {
        Resources res = getResources();
        Toast.makeText(this, String.format(res.getString(R.string.toast_format), getString(R.string.toast_capstone)), Toast.LENGTH_SHORT).show();
    }
}
