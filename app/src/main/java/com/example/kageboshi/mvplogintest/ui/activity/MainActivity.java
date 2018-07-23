package com.example.kageboshi.mvplogintest.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kageboshi.mvplogintest.R;
import com.example.kageboshi.mvplogintest.presenter.LoginPresenter;
import com.example.kageboshi.mvplogintest.presenter.impl.LoginPresenterImpl;
import com.example.kageboshi.mvplogintest.ui.BaseView;
import com.example.kageboshi.mvplogintest.ui.LoginView;
import com.example.kageboshi.mvplogintest.ui.activity.ContactActivity;
import com.example.kageboshi.mvplogintest.util.Constants;
import com.example.kageboshi.mvplogintest.util.ToastUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BaseView {
    private Toolbar toolbarMain;
    private TextView textUser;
    private TextView textPassword;
    private EditText editUser;
    private EditText editPassword;
    private Button buttonLogin;
    private TextView textTitle;
    private String userInfo;
    private String passwordInfo;
    private String imei;
    private LoginPresenter loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        toolbarSetting();
        permissionCheck();

    }

    private void permissionCheck() {
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, Constants.PHONE_STATE_REQUEST_CODE);
            return;
        }
    }


    private void toolbarSetting() {
        toolbarMain.setTitle("");
        setSupportActionBar(toolbarMain);
        textTitle = ((TextView) toolbarMain.findViewById(R.id.textTitle));
        textTitle.setText(getResources().getText(R.string.login));
        toolbarMain.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_version_number:
                        PackageManager packageManager = getPackageManager();
                        try {
                            String versionName = packageManager.getPackageInfo(getPackageName(), 0).versionName;
                            ToastUtil.show(getApplicationContext(), R.string.version_number_is + versionName);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }

                        break;
                    case R.id.menu_update_address:

                        break;
                    case R.id.menu_update:
                        ToastUtil.show(getApplicationContext(), R.string.no_update);
                        break;
                }

                return false;
            }
        });
    }

    private void bindView() {
        toolbarMain = ((Toolbar) findViewById(R.id.toolbar_main));
        textUser = ((TextView) findViewById(R.id.user));
        textPassword = ((TextView) findViewById(R.id.password));
        editUser = ((EditText) findViewById(R.id.user_edit));
        editPassword = ((EditText) findViewById(R.id.password_edit));
        buttonLogin = ((Button) findViewById(R.id.login));
        buttonLogin.setOnClickListener(this);
        loginPresenter = new LoginPresenterImpl(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onClick(View view) {
        getLoginInfo();
        checkLoginInfo();
        if (!userInfo.equals("") && !passwordInfo.equals("") && null != imei) {
            loginPresenter.login(userInfo, passwordInfo, imei);
        }
    }

    private void checkLoginInfo() {
        if (userInfo.equals("")) {
            ToastUtil.show(this, R.string.name_illegal);
            return;
        }
        if (passwordInfo.equals("")) {
            ToastUtil.show(this, R.string.password_illegal);
            return;
        }
        if (null == imei) {
            Log.e("TAG", "imei is empty");
            return;
        }
    }

    @SuppressLint("MissingPermission")
    private void getLoginInfo() {
        userInfo = editUser.getText().toString();
        passwordInfo = editPassword.getText().toString();
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        imei = telephonyManager.getDeviceId();
        Log.e("TAG", "imei=" + imei);
    }


//    @Override
//    public void onLoginSuccess(Object token) {
//        ToastUtil.show(getApplicationContext(), "login success");
//        if (null != token) {
//            Intent intent = new Intent(this, ContactActivity.class);
//            intent.putExtra(Constants.TOKEN_KEY, ((String) token));
//            startActivity(intent);
//        }
//    }

//    @Override
//    public void onLoginFail() {
//        ToastUtil.show(getApplicationContext(), "login fail");
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.PHONE_STATE_REQUEST_CODE) {
            if (grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ToastUtil.show(this, R.string.permission_denied);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onHttpSuccess(Object response) {
        ToastUtil.show(getApplicationContext(), "login success");
        if (null != response) {
            Intent intent = new Intent(this, ContactActivity.class);
            intent.putExtra(Constants.TOKEN_KEY, ((String) response));
            startActivity(intent);
        }
    }

    @Override
    public void onHttpFailure() {
        ToastUtil.show(getApplicationContext(), "login fail");
    }
}
