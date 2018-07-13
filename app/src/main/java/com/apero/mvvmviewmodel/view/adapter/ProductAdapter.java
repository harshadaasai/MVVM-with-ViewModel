package com.apero.mvvmviewmodel.view.adapter;


import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.apero.mvvmviewmodel.R;
import com.apero.mvvmviewmodel.databinding.NewsListItemBinding;
import com.apero.mvvmviewmodel.model.Followers;
import com.apero.mvvmviewmodel.view.callback.OnClickCallback;


import java.util.ArrayList;
import java.util.List;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.ArticleViewHolder> {

    List<Followers> mfollowersList;
    List<Followers> totalfollowersList;
    private static final String TAG = "ProductAdapter";
    int position = 0;

    public ProductAdapter(List<Followers> followers) {
        if(this.mfollowersList == null)
        {
            this.mfollowersList = new ArrayList<>();
        }

        this.mfollowersList.addAll(followers);
    }

    public void setProjectList(final List<Followers> followersList) {


        Log.e(TAG, "setProjectList: "+followersList );
        if (totalfollowersList == null) {
            position = 0;
            totalfollowersList = followersList;
        }
        else
        {
            position = totalfollowersList.size();
            totalfollowersList.addAll(followersList);

        }

        Log.e(TAG, "setProjectList: position "+position );

        if (this.mfollowersList == null) {
            this.mfollowersList = followersList;
            Log.e("when null", "setProjectList: "+totalfollowersList.size() );
            notifyItemRangeInserted(position, totalfollowersList.size());

        } else {
            Log.e("when not null", "setProjectList: "+totalfollowersList.size() );

            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    Log.e(TAG, "getOldListSize: "+followersList.size() );
                    return followersList.size();
                }

                @Override
                public int getNewListSize() {
                    Log.e(TAG, "getNewListSize: "+followersList.size() );
                    return followersList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    Log.e(TAG, "oldItemPosition: "+oldItemPosition);
                    Log.e(TAG, "areItemsTheSame: "+newItemPosition );
                    return followersList.get(oldItemPosition).getFullname() ==
                            followersList.get(newItemPosition).getFullname();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Followers newArticle = followersList.get(newItemPosition);
                    Followers oldArticle = followersList.get(oldItemPosition);
                    return newArticle.getFullname().equals(oldArticle.getFullname())
                            && oldArticle.getUser_type().equals(newArticle.getUser_type());
                }
            });



            result.dispatchUpdatesTo(this);


//            this.followersList = followersList;
//            notifyItemRangeInserted(position, totalfollowersList.size());

        }
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewsListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.news_list_item,
                        parent, false);

        binding.setCallback(new OnClickCallback());

        return new ArticleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.binding.setArticle(mfollowersList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mfollowersList == null ? 0 : mfollowersList.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {

        final NewsListItemBinding binding;

        public ArticleViewHolder(NewsListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void updateEmployeeListItems(List<Followers> followers, RecyclerView recyclerView) {
//        final FollowersDiffCallback diffCallback = new FollowersDiffCallback(this.mfollowersList, followers);
//        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
//        Log.e(TAG, "getOldSize: "+diffCallback.getOldListSize() );
//        Log.e(TAG, "getNewSize: "+diffCallback.getNewListSize() );
//
////        this.mfollowersList.clear();
//        this.mfollowersList.addAll(followers);
////        diffResult.dispatchUpdatesTo(this);


        Log.e(TAG, "oldpos: "+mfollowersList.size() );
        int oldpos = mfollowersList.size();
        this.mfollowersList.addAll(followers);
        Log.e(TAG, "newpos: "+mfollowersList.size() );
        notifyDataSetChanged();
        recyclerView.scrollToPosition(oldpos);

    }
}
