package com.apero.mvvmviewmodel.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.apero.mvvmviewmodel.LoginViewInterface;
import com.apero.mvvmviewmodel.model.Login_Request;
import com.apero.mvvmviewmodel.repository.APIRepository;

import static com.apero.mvvmviewmodel.AppConstants.BASIC_URL;

public class LoginViewModel extends AndroidViewModel
{
    APIRepository newsRepository;
    static LoginViewInterface loginView;
    public ObservableInt isLoading;
    private static final String TAG = "LoginViewModel";

    public LoginViewModel(@NonNull Application application) {
        super(application);
        newsRepository =  APIRepository.getInstance("");
        isLoading = new ObservableInt();
        isLoading.set(View.GONE);
    }

    /**
     * A creator is used to inject the project ID into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        public Factory(@NonNull Application application, LoginViewInterface loginViewInterface) {
            this.application = application;
            loginView = loginViewInterface;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new LoginViewModel(application);
        }
    }

    public boolean isEmailAndPasswordValid(String email, String password) {
        // validate email and password
        if (email.equals("")) {
            return false;
        }

        if (password.equals("")) {
            return false;
        }
        return true;
    }

    public void login(String email, String password)
    {
        isLoading.set(View.VISIBLE);
        BASIC_URL = "http://dev.api.viintro.com/";
        Login_Request login_request = new Login_Request(3, "Ti2xuao0OzSi95WYw117mw1HDTta8XOfcChswRnQ",email, password,"","");
        newsRepository.getLoginResponse(login_request, loginView, isLoading);
    }

    public void onServerLoginClick()
    {
        loginView.login();
    }



}
