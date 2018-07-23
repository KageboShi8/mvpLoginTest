package com.example.kageboshi.mvplogintest.model.impl;

import android.util.Log;
import android.view.View;

import com.example.kageboshi.mvplogintest.http.RetrofitFactory;
import com.example.kageboshi.mvplogintest.model.ContactModel;
import com.example.kageboshi.mvplogintest.model.entity.ContactResponseModel;
import com.example.kageboshi.mvplogintest.presenter.OnContactCompleteListener;
import com.example.kageboshi.mvplogintest.util.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactModelImpl implements ContactModel {
    @Override
    public void getContact(String token, final OnContactCompleteListener listener) {
        RetrofitFactory.getInstance().getContacts(token, "0", "0", 5)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContactResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ContactResponseModel contactResponseModel) {
                        Log.e("TAG", "SUCCESS");
                        listener.onSuccess(contactResponseModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "ERROR");
                        listener.onFailure();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
