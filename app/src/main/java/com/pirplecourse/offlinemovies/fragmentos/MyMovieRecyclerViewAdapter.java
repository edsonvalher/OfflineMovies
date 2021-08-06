package com.pirplecourse.offlinemovies.fragmentos;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pirplecourse.offlinemovies.R;
import com.pirplecourse.offlinemovies.data.local.entity.MovieEntity;
import com.pirplecourse.offlinemovies.data.remote.ApiConstantes;
import com.pirplecourse.offlinemovies.fragmentos.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMovieRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder> {

    private List<MovieEntity> mValues;
    Context ctx;

    public MyMovieRecyclerViewAdapter(Context context,List<MovieEntity> items) {
        mValues = items;
        ctx = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //aqui se setean los controles
        Glide.with(ctx)
                .load(ApiConstantes.IMAGE_API_PREFIX + holder.mItem.getPosterPath())
                .into(holder.imageViewCover);

    }

    @Override
    public int getItemCount() {
        if(mValues != null){
            return mValues.size();
        }else{
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //aqui se instancian los controles
        public final View mView;
        public final ImageView imageViewCover;
        public MovieEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageViewCover = (ImageView) view.findViewById(R.id.imageViewCover);
        }

        @Override
        public String toString() {
            return super.toString() ;
        }
    }
    public void setData(List<MovieEntity> movies){
        this.mValues = movies;
        notifyDataSetChanged();
    }
}