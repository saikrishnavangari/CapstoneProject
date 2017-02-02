package sai.com.mymovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import sai.com.mymovies.model.CastAndCrew;

/**
 * Created by krrish on 28/01/2017.
 */

public class HorizontalCastAdapter extends RecyclerView.Adapter<HorizontalCastAdapter.ViewHolder> {
    List<CastAndCrew.cast> mDataList;
    Context mContext;
    public HorizontalCastAdapter(List<CastAndCrew.cast> mCast, Context context) {
        this.mDataList = mCast;
        mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_imagescrollview_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CastAndCrew.cast castObject= mDataList.get(position);
        holder.name.setText(castObject.getName());
        Picasso.with(mContext)
                .load(MainActivity.IMAGE_BASE_URL + "w185/" + castObject.getProfile_path())
                .placeholder(R.drawable.ic_person_black_48dp)
                .error(R.drawable.ic_person_black_48dp)
                .into(holder.image_scrollview);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_tv)
        TextView name;
        @BindView(R.id.image_scrollview)
        ImageView image_scrollview;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
