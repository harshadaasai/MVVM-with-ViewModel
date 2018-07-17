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

//        Log.e(TAG, "oldpos: "+mfollowersList.size() );
        int oldpos = mfollowersList.size();
        this.mfollowersList.addAll(followers);
//        Log.e(TAG, "newpos: "+mfollowersList.size() );
        notifyDataSetChanged();
        recyclerView.scrollToPosition(oldpos);

    }
}
