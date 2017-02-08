package sai.com.mymovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sai.com.mymovies.MainActivity;
import sai.com.mymovies.R;
import sai.com.mymovies.model.Movie;

/**
 * Created by krrish on 6/02/2017.
 */

public class SearchMoviesAdapter extends ArrayAdapter {
    private ArrayList<Movie.results> searchContent;
    private Context mContext;
    private ImageView imageView;
    public SearchMoviesAdapter(Context context, ArrayList<Movie.results> objects) {
        super(context,0,objects);
        searchContent=objects;
        mContext=context;
    }


    @Override
    public int getCount() {
        return searchContent.size();
    }

    @Override
    public Movie.results getItem(int position) {
        return searchContent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return searchContent.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.gridview_listitem, parent, false);
        }
        imageView= (ImageView) convertView.findViewById(R.id.imageView);

        Picasso.with(mContext)
                .load(MainActivity.IMAGE_BASE_URL + "w185/" + getItem(position).getPoster_path())
                .placeholder(R.drawable.ic_movie_black_48dp)
                .error(R.drawable.ic_movie_black_48dp)
                .into(imageView);
        return convertView;
    }


}
