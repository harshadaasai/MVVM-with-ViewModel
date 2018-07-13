package com.apero.mvvmviewmodel.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.apero.mvvmviewmodel.Interface.ObserverInterface;
import com.apero.mvvmviewmodel.model.News;
import com.apero.mvvmviewmodel.repository.APIRepository;

import static com.apero.mvvmviewmodel.AppConstants.BASIC_URL;

public class ProductViewModel  extends AndroidViewModel {
    private LiveData<News> newsLiveData;
    private static final String TAG = "ProductViewModel";


    public ObservableField<News> news = new ObservableField<>();

    public ProductViewModel(@NonNull Application application, ObserverInterface observerInterface) {
        super(application);
        // a differnt source can be passed, here i am passing techcrunch
        BASIC_URL = "http://dev.api.viintro.com/";

        callAPI(observerInterface);

    }

    public void callAPI(ObserverInterface observerInterface)
    {
        newsLiveData = APIRepository.getInstance("").getNews(observerInterface);

    }

    public LiveData<News> getObservableProject() {
        return newsLiveData;
    }

    public void setNews(News news) {
        this.news.set(news);
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
