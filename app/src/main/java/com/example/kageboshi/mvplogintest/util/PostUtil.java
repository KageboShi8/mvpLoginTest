package com.example.kageboshi.mvplogintest.util;

import android.util.Log;

import com.example.kageboshi.mvplogintest.model.entity.LoginRequestModel;
import com.google.gson.Gson;

public class PostUtil {
    public static String initPostInfo(String name, String password, String imei) {
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        LoginRequestModel.DataBean dataBean = new LoginRequestModel.DataBean();

        dataBean.setUsername(name);
        dataBean.setPassword(password);
        //     Log.e("imei", imei);
        dataBean.setIem(imei);

        loginRequestModel.setVer(0);
        loginRequestModel.setTyp(0);
        loginRequestModel.setData(dataBean);

        Gson gson = new Gson();
        String json = gson.toJson(loginRequestModel);

        Log.e("gson", json);

        return json;
    }
}
