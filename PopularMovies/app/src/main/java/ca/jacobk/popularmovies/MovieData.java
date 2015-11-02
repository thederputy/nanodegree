package ca.jacobk.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob on 2015-10-31.
 */
public class MovieData implements Parcelable {
    boolean adult;
    String backdropPath;
    List<Integer> genreIds;
    int id;
    String originalLanguage;
    String originalTitle;
    String overview;
    String releaseDate;
    public String posterPath;
    float popularity;
    String title;
    boolean video;
    float voteAverage;
    int voteCount;

    public static final String PosterURL = "http://image.tmdb.org/t/p/w342";

    MovieData(JSONObject json) throws JSONException {
        this.adult = json.getBoolean("adult");
        this.backdropPath = json.getString("backdrop_path");

        JSONArray genres = json.getJSONArray("genre_ids");
        this.genreIds = new ArrayList<>();
        int length = genres.length();
        for (int i = 0; i < length; i++) {
            this.genreIds.add(genres.getInt(i));
        }

        this.id = json.getInt("id");
        this.originalLanguage = json.getString("original_language");
        this.originalTitle = json.getString("original_title");
        this.overview = json.getString("overview");
        this.releaseDate = json.getString("release_date");
        this.posterPath = json.getString("poster_path");
        this.popularity = (float)json.getDouble("popularity");
        this.title = json.getString("title");
        this.video = json.getBoolean("video");
        this.voteAverage = (float)json.getDouble("vote_average");
        this.voteCount = json.getInt("vote_count");
    }

    MovieData(Parcel in) {
        boolean[] bools = new boolean[2];
        in.readBooleanArray(bools);
        this.adult = bools[0];
        this.video = bools[1];
        this.backdropPath = in.readString();
        this.genreIds = new ArrayList<>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
        this.id = in.readInt();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.popularity = in.readFloat();
        this.title = in.readString();
        this.voteAverage = in.readFloat();
        this.voteCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBooleanArray(new boolean[]{adult, video});
        dest.writeString(backdropPath);
        dest.writeList(genreIds);
        dest.writeInt(id);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(posterPath);
        dest.writeFloat(popularity);
        dest.writeString(title);
        dest.writeFloat(voteAverage);
        dest.writeInt(voteCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
}
