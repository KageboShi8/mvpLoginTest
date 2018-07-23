package com.example.kageboshi.mvplogintest.ui;

import com.example.kageboshi.mvplogintest.model.entity.ContactResponseModel;


//Deprecated
public interface ContactDownloadView<T> {
    void onDownloadSuccess(T reseponse);

    void onDownloadFailure();
}
