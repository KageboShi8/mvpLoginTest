package com.example.kageboshi.mvplogintest.presenter;

import com.example.kageboshi.mvplogintest.model.entity.ContactResponseModel;

public interface OnContactCompleteListener {
    void onSuccess(ContactResponseModel contactResponseModel);

    void onFailure();
}
