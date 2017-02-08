package sai.com.mymovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import sai.com.mymovies.utiities.NetworkUtilities;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG=MainActivity.class.getSimpleName();
    public static final String API_KEY="";
    public static final String YOUTUBE_API_KEY="AIzaSyDfV1jmS4HbVjpYdNBCh_0AJ66RTmhl8G8";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        NetworkUtilities.ScheduleMoviesJobService(this, "popular");
        NetworkUtilities.ScheduleMoviesJobService(this, "upcoming");
        NetworkUtilities.ScheduleMoviesJobService(this, "top_rated");
        NetworkUtilities.ScheduleMoviesJobService(this, "now_playing");
        MyPagerAdapter myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager());
        setSupportActionBar(toolbar);
        viewPager.setAdapter(myPagerAdapter);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();

        return super.onCreateOptionsMenu(menu);
    }


    public  class MyPagerAdapter extends FragmentStatePagerAdapter {
        private  int NUM_ITEMS = 4;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return HomepageFragment.newInstance(0, "Popular");
                case 1: // Fragment # 1 - This will show FirstFragment
                    return HomepageFragment.newInstance(0, "TopRated");
                case 2: // Fragment # 2 - This will show FirstFragment
                    return HomepageFragment.newInstance(0, "NowPlaying");
                case 3: // Fragment # 3 - This will show FirstFragment
                    return HomepageFragment.newInstance(0, "Upcoming");

                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return  "Popular";
                case 1: // Fragment # 1 - This will show FirstFragment
                    return "TopRated";
                case 2: // Fragment # 2 - This will show FirstFragment
                    return "NowPlaying";
                case 3: // Fragment # 3 - This will show FirstFragment
                    return "Upcoming";

                default:
                    return null;
            }

        }

    }
}
