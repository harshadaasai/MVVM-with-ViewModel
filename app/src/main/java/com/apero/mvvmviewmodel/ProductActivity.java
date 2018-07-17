package com.apero.mvvmviewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.apero.mvvmviewmodel.Interface.ObserverInterface;
import com.apero.mvvmviewmodel.databinding.ActivityProductBinding;
import com.apero.mvvmviewmodel.lifecycle.SomeObserver;
import com.apero.mvvmviewmodel.model.News;
import com.apero.mvvmviewmodel.view.adapter.ProductAdapter;
import com.apero.mvvmviewmodel.viewmodel.ProductViewModel;

import java.util.ArrayList;

/**
 * Created by hasai on 27/06/18.
 */

public class ProductActivity extends AppCompatActivity implements ObserverInterface {

    Context context;
    ActivityProductBinding activityProductBinding;
    public ProductAdapter productAdapter;
    private static final String TAG = "ProductActivity";
    public static String accesstoken;
    public static int count;
    public ProductViewModel.Factory factory;
    public ProductViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        context = this;

        activityProductBinding = DataBindingUtil.setContentView(this, R.layout.activity_product);

        accesstoken = getIntent().getExtras().getString("bearertoken");

        factory = new ProductViewModel.Factory(
                this.getApplication(), this);

        viewModel = ViewModelProviders.of(this, factory)
                .get(ProductViewModel.class);

        viewModel.getObservableNews().observe(this, nameObserver);
        ArrayList myfollowers = (ArrayList) viewModel.getObservableNews().getValue().getFollowers();
        productAdapter = new ProductAdapter(myfollowers);
        activityProductBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityProductBinding.recyclerView.setAdapter(productAdapter);
        Log.e(TAG, "onCreate: "+count );
        if(savedInstanceState == null) {
            count = 0;
            viewModel.callAPI(viewModel);
        }
        else
        {
            productAdapter.notifyDataSetChanged();
        }
        getLifecycle().addObserver(new SomeObserver());
    }

    // Create the observer which updates the UI.
    final Observer<News> nameObserver = new Observer<News>() {
        @Override
        public void onChanged(@Nullable final News news) {
            if (news != null) {
                productAdapter.updateEmployeeListItems(news.getFollowers(), activityProductBinding.recyclerView);
                refreshDataandCallAPI(news);
            }
        }


    };

    @Override
    public void refreshDataandCallAPI(News news) {

        count++;
        if(count <= 1)
        {
            timer();
        }
    }

    public void timer()
    {
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                viewModel.callAPI(viewModel);

            }
        }.start();
    }
}
