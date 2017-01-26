package sai.com.mymovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG=MainActivity.class.getSimpleName();
    public static final String API_KEY="8496be0b2149805afa458ab8ec27560c";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MyPagerAdapter myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(myPagerAdapter);
        
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
            return "Page " + position;
        }

    }
}
