package com.example.kageboshi.mvplogintest.ui;

import com.example.kageboshi.mvplogintest.model.entity.LoginResponseModel;


//Deprecated
public interface LoginView<T> {
    void onLoginSuccess(T token);

    void onLoginFail();
}
