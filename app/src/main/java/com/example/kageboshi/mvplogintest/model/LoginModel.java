package com.example.kageboshi.mvplogintest.model;

import com.example.kageboshi.mvplogintest.presenter.OnCompleteListener;

public interface LoginModel {
    void loginCheck(String name, String password, String imei,OnCompleteListener listener);
}
