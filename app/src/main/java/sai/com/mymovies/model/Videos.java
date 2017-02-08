package sai.com.mymovies.model;

import java.util.ArrayList;

/**
 * Created by krrish on 3/12/2016.
 */

public class Videos {


    public ArrayList<Videos.results> results;

    public ArrayList<Videos.results> getResults() {
        return results;
    }

    public void setResults(ArrayList<results> results) {
        this.results = results;
    }

    public static class results {

        private String key;
        private String type;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
