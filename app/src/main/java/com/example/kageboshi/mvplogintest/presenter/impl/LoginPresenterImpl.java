package com.example.kageboshi.mvplogintest.presenter.impl;

import com.example.kageboshi.mvplogintest.model.LoginModel;
import com.example.kageboshi.mvplogintest.model.entity.LoginResponseModel;
import com.example.kageboshi.mvplogintest.model.impl.LoginModelImpl;
import com.example.kageboshi.mvplogintest.presenter.LoginPresenter;
import com.example.kageboshi.mvplogintest.presenter.OnCompleteListener;
import com.example.kageboshi.mvplogintest.ui.BaseView;
import com.example.kageboshi.mvplogintest.ui.LoginView;

public class LoginPresenterImpl implements LoginPresenter,OnCompleteListener{

    private LoginModel loginModel;
    private final BaseView loginView;

    public LoginPresenterImpl(BaseView view){
        this.loginView =view;
        loginModel=new LoginModelImpl();

    }
    @Override
    public void login(String name, String password,String imei) {
        loginModel.loginCheck(name,password,imei,this);
    }


    @Override
    public void onSuccess(LoginResponseModel responseModel) {
        String token = responseModel.getData().getToken();
        loginView.onHttpSuccess(token);
    }

    @Override
    public void onFailure() {
        loginView.onHttpFailure();
    }
}
