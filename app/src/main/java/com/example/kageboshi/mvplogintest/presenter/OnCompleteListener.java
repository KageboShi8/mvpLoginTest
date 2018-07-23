package com.example.kageboshi.mvplogintest.presenter;

import android.util.Log;

import com.example.kageboshi.mvplogintest.model.entity.LoginRequestModel;
import com.example.kageboshi.mvplogintest.model.entity.LoginResponseModel;

public interface OnCompleteListener<T> {
    void onSuccess(LoginResponseModel responseModel);

    void onFailure();
}
