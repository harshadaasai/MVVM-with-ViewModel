package com.apero.mvvmviewmodel.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.apero.mvvmviewmodel.Interface.ObserverInterface;
import com.apero.mvvmviewmodel.model.News;
import com.apero.mvvmviewmodel.repository.APIRepository;

public class ProductViewModel  extends AndroidViewModel {
    private MutableLiveData<News> newsLiveData;
    private static final String TAG = "ProductViewModel";



    public ProductViewModel(@NonNull Application application, ObserverInterface observerInterface) {
        super(application);
        // a differnt source can be passed, here i am passing techcrunch



    }

    public void callAPI(ProductViewModel viewModel)
    {
        APIRepository.getInstance("").getNews(viewModel);

    }

    public MutableLiveData<News> getObservableNews() {
        if(newsLiveData == null)
        {
            newsLiveData = new MutableLiveData<>();
            newsLiveData.setValue(new News());

        }

        return newsLiveData;
    }


    /**
     * A creator is used to inject the project ID into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        ObserverInterface observerInterface;


        public Factory(@NonNull Application application, ObserverInterface observerInterface) {
            this.application = application;
            this.observerInterface = observerInterface;

        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked

            return (T) new ProductViewModel(application, observerInterface);
        }
    }
}
