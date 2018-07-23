package com.example.kageboshi.mvplogintest.model;

import com.example.kageboshi.mvplogintest.presenter.OnContactCompleteListener;

public interface ContactModel {
    void getContact(String token, OnContactCompleteListener listener);
}
