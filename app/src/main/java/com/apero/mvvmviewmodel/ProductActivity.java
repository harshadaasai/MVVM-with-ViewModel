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
import com.apero.mvvmviewmodel.model.Followers;
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
    ProductActivity productActivity = this;
    private int count = 0;
    public ProductViewModel.Factory factory;
    public ProductViewModel viewModel;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "came here: ");
        context = this;


        Log.e(TAG, "onCreate: "+productAdapter);
        activityProductBinding = DataBindingUtil.setContentView(this, R.layout.activity_product);
        activityProductBinding.setIsLoading(true);

        productAdapter = new ProductAdapter(new ArrayList<Followers>());
        activityProductBinding.recyclerView.setAdapter(productAdapter);
        activityProductBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        accesstoken = getIntent().getExtras().getString("bearertoken");
        Log.e(TAG, "onCreate: "+accesstoken );

        factory = new ProductViewModel.Factory(
                this.getApplication(), this);

        viewModel = ViewModelProviders.of(this, factory)
                .get(ProductViewModel.class);

        activityProductBinding.setIsLoading(true);

        // for shake of
        getLifecycle().addObserver(new SomeObserver());


        //Observer is A simple callback that can receive from LiveData.
        viewModel.getObservableProject().observe(this, nameObserver);
        Log.e(TAG, "class type "+viewModel.getObservableProject().getClass());


    }

    // Create the observer which updates the UI.
    final Observer<News> nameObserver = new Observer<News>() {
        @Override
        public void onChanged(@Nullable final News news) {
            if (news != null) {
                activityProductBinding.setIsLoading(false);
                refreshDataandCallAPI(news);
            }
        }


    };

    @Override
    public void refreshDataandCallAPI(News news) {
        productAdapter.updateEmployeeListItems(news.getFollowers(), activityProductBinding.recyclerView);
        count++;
        Log.e(TAG, "onChanged: "+count);
        if(count <= 3)
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
                viewModel.callAPI(productActivity);

            }
        }.start();
    }
}
