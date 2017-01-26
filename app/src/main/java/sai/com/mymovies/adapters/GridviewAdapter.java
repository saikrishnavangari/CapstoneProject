package sai.com.mymovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import sai.com.mymovies.MainActivity;
import sai.com.mymovies.R;
import sai.com.mymovies.data.MovieFields;

/**
 * Created by krrish on 22/01/2017.
 */

public class GridviewAdapter extends CursorAdapter {
    private Context mContext;
    private Cursor mCursor;
    ImageView imageView;

    public GridviewAdapter(Context context,Cursor cursor) {
        super(context, cursor,0);
        mContext=context;
        mCursor=cursor;

    }

    public GridviewAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }
 /*
    @Override
    public int getCount() {
        return 40;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

 @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.gridview_listitem,parent,false);
            imageView= (ImageView) convertView.findViewById(R.id.imageView);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(R.mipmap.ic_launcher);
        return imageView;
    }*/

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.gridview_listitem,parent,false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        imageView= (ImageView) view.findViewById(R.id.imageView);
        Glide
                .with(context)
                .load(MainActivity.IMAGE_BASE_URL + "w185/" + cursor.getString(cursor.getColumnIndex(MovieFields.Column_posterPath)))
                .into(imageView);
        Log.d(cursor.getString(cursor.getColumnIndex(MovieFields.Column_movieType)),cursor.getString(cursor.getColumnIndex(MovieFields.Column_TITLE)));
    }
}
