package com.example.kageboshi.mvplogintest.model.impl;

import android.util.Log;

import com.example.kageboshi.mvplogintest.http.RetrofitFactory;
import com.example.kageboshi.mvplogintest.model.entity.LoginResponseModel;
import com.example.kageboshi.mvplogintest.presenter.OnCompleteListener;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.example.kageboshi.mvplogintest.util.PostUtil.initPostInfo;

public class LoginModelImpl implements com.example.kageboshi.mvplogintest.model.LoginModel {
    @Override
    public void loginCheck(String name, String password, String imei,final OnCompleteListener listener) {
        RetrofitFactory.getInstance()
                .postCall(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), initPostInfo(name,password,imei)))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<LoginResponseModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(LoginResponseModel loginResponseModel) {
                Log.e("Tag", "success");
                listener.onSuccess(loginResponseModel);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("Tag", "error");
                listener.onFailure();
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
