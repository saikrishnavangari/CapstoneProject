package sai.com.mymovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sai.com.mymovies.MainActivity;
import sai.com.mymovies.R;
import sai.com.mymovies.model.Movie;

/**
 * Created by krrish on 29/01/2017.
 */

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder> {
    private final String LOG_TAG=SimilarMoviesAdapter.class.getSimpleName();
    List<Movie.results> mDataList;
    Context mContext;
    ListItemClickListener mOnClickListener;
    public SimilarMoviesAdapter(List<Movie.results> similarmovies, Context context) {
        this.mDataList = similarmovies;
        mContext=context;
        mOnClickListener= (ListItemClickListener) context;
    }
    public interface ListItemClickListener{

        public void onListItemClicked(Movie.results movieObject);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_imagescrollview_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimilarMoviesAdapter.ViewHolder holder, int position) {
        Movie.results movieObject= mDataList.get(position);
        holder.name.setText(movieObject.getOriginal_title());
        Picasso.with(mContext)
                .load(MainActivity.IMAGE_BASE_URL + "w185/" + movieObject.getPoster_path())
                .placeholder(R.drawable.ic_person_black_48dp)
                .error(R.drawable.ic_person_black_48dp)
                .into(holder.image_scrollview);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.name_tv)
        TextView name;
        @BindView(R.id.image_scrollview)
        ImageView image_scrollview;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(LOG_TAG,"item clicked");
               int position= getAdapterPosition();
            Movie.results movieObject=mDataList.get(position);
            mOnClickListener.onListItemClicked(movieObject);
        }
    }
}
