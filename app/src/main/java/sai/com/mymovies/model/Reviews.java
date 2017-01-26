package sai.com.mymovies.model;

import java.util.ArrayList;

/**
 * Created by krrish on 2/12/2016.
 */

public class Reviews {
    ArrayList<Reviews.results> results;

    public ArrayList<Reviews.results> getResults() {
        return results;
    }

    public static class results{
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
