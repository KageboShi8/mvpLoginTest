package com.example.kageboshi.mvplogintest.presenter.impl;

import com.example.kageboshi.mvplogintest.model.ContactModel;
import com.example.kageboshi.mvplogintest.model.entity.ContactResponseModel;
import com.example.kageboshi.mvplogintest.model.impl.ContactModelImpl;
import com.example.kageboshi.mvplogintest.presenter.ContactPresenter;
import com.example.kageboshi.mvplogintest.presenter.OnContactCompleteListener;
import com.example.kageboshi.mvplogintest.ui.BaseView;
import com.example.kageboshi.mvplogintest.ui.ContactDownloadView;
import com.example.kageboshi.mvplogintest.ui.activity.ContactActivity;

public class ContactPresenterImpl implements ContactPresenter,OnContactCompleteListener {
    private BaseView contactDownloadView;
    private ContactModel contactModel;

    public ContactPresenterImpl(BaseView contactDownloadView) {
        this.contactDownloadView = contactDownloadView;
        contactModel=new ContactModelImpl();
    }

    @Override
    public void downloadContact(String token) {
        contactModel.getContact(token,this);
    }

    @Override
    public void onSuccess(ContactResponseModel contactResponseModel) {
        contactDownloadView.onHttpSuccess(contactResponseModel);
    }

    @Override
    public void onFailure() {
        contactDownloadView.onHttpFailure();
    }
}
