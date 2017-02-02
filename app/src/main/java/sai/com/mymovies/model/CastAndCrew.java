package sai.com.mymovies.model;

import java.util.ArrayList;

/**
 * Created by krrish on 28/01/2017.
 */

public class CastAndCrew {
    ArrayList<cast> cast;
    ArrayList<crew> crew;
    public ArrayList<CastAndCrew.crew> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<CastAndCrew.crew> crew) {
        this.crew = crew;
    }



    public ArrayList<cast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<cast> castAndCrew) {
        this.cast = castAndCrew;
    }

    public static class cast {
        private String name;
        private String character;
        private String profile_path;

        @Override
        public String toString() {
            return "cast{" +
                    "name='" + name + '\'' +
                    ", character='" + character + '\'' +
                    ", profile_path='" + profile_path + '\'' +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }
    }

    public static class crew {
        private String name;
        private String department;
        private String profile_path;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }
    }
}
