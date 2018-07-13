package com.apero.mvvmviewmodel.Interface;

import android.arch.lifecycle.LiveData;

import com.apero.mvvmviewmodel.model.News;

public interface ObserverInterface {

    void refreshDataandCallAPI(News news);

}
