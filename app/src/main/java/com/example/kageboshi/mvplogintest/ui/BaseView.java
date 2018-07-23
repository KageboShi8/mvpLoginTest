package com.example.kageboshi.mvplogintest.ui;

public interface BaseView<T> {
    void onHttpSuccess(T response);

    void onHttpFailure();
}
