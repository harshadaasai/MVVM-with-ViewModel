package com.apero.mvvmviewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.apero.mvvmviewmodel.databinding.ActivityLoginBinding;
import com.apero.mvvmviewmodel.lifecycle.SomeObserver;
import com.apero.mvvmviewmodel.model.Login_Response;
import com.apero.mvvmviewmodel.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements LoginViewInterface {

    LoginActivity mainActivity = this;
    ActivityLoginBinding activityMainBinding;
    LoginViewModel viewModel;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        LoginViewModel.Factory factory = new LoginViewModel.Factory(mainActivity.getApplication(), this);

        viewModel = ViewModelProviders.of(this, factory)
                .get(LoginViewModel.class);

        activityMainBinding.setLoginViewModel(viewModel);

        getLifecycle().addObserver(new SomeObserver());
    }


    @Override
    public void login() {
        String email = activityMainBinding.edtEmail.getText().toString();
        String password = activityMainBinding.edtPassword.getText().toString();
        if (viewModel.isEmailAndPasswordValid(email, password)) {
            hideKeyboard();
            viewModel.login(email, password);
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void openMainActivity(Login_Response response) {
        Log.e(TAG, "openMainActivity: "+response.getFullname());
        Intent i = new Intent(LoginActivity.this, ProductActivity.class);
        i.putExtra("bearertoken",response.getAccess_token());
        startActivity(i);
    }
}
