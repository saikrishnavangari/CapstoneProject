package sai.com.mymovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by krrish on 24/01/2017.
 */

public class Movie {
    ArrayList<results> results;

    public ArrayList<results> getResults() {
        return results;
    }

    public void setMovies(ArrayList<results> results) {
        this.results = results;
    }

    public static class results implements Parcelable {
        public static final Creator<results> CREATOR = new Creator<results>() {
            @Override
            public results createFromParcel(Parcel in) {
                return new results(in);
            }

            @Override
            public results[] newArray(int size) {
                return new results[size];
            }
        };
        private String poster_path;
        private String overview;
        private String release_date;
        private int id;
        private String original_title;
        private String original_language;
        private String title;
        private String backdrop_path;
        private double popularity;
        private int vote_count;
        private double vote_average;

        protected results(Parcel in) {
            poster_path = in.readString();
            overview = in.readString();
            release_date = in.readString();
            id = in.readInt();
            original_title = in.readString();
            original_language = in.readString();
            title = in.readString();
            backdrop_path = in.readString();
            popularity = in.readDouble();
            vote_count = in.readInt();
            vote_average = in.readDouble();
        }

        public results() {
        }

        public static Creator<Movie.results> getCREATOR() {
            return CREATOR;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(poster_path);
            dest.writeString(overview);
            dest.writeString(release_date);
            dest.writeInt(id);
            dest.writeString(original_title);
            dest.writeString(original_language);
            dest.writeString(title);
            dest.writeString(backdrop_path);
            dest.writeDouble(popularity);
            dest.writeInt(vote_count);
            dest.writeDouble(vote_average);
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public double getVote_average() {
            return vote_average;
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }

        @Override
        public String toString() {
            return "results{" +
                    "poster_path='" + poster_path + '\'' +
                    ", overview='" + overview + '\'' +
                    ", release_date='" + release_date + '\'' +
                    ", id=" + id +
                    ", original_title='" + original_title + '\'' +
                    ", original_language='" + original_language + '\'' +
                    ", title='" + title + '\'' +
                    ", backdrop_path='" + backdrop_path + '\'' +
                    ", popularity=" + popularity +
                    ", vote_count=" + vote_count +
                    ", vote_average=" + vote_average +
                    '}';
        }
    }
}
