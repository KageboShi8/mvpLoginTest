package com.example.kageboshi.mvplogintest.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kageboshi.mvplogintest.R;
import com.example.kageboshi.mvplogintest.adapter.ContactAdapter;
import com.example.kageboshi.mvplogintest.model.entity.ContactResponseModel;
import com.example.kageboshi.mvplogintest.presenter.ContactPresenter;
import com.example.kageboshi.mvplogintest.presenter.impl.ContactPresenterImpl;
import com.example.kageboshi.mvplogintest.ui.BaseView;
import com.example.kageboshi.mvplogintest.ui.ContactDownloadView;
import com.example.kageboshi.mvplogintest.util.Constants;
import com.example.kageboshi.mvplogintest.util.ContactsUtils;
import com.example.kageboshi.mvplogintest.util.ToastUtil;

import java.util.List;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener, BaseView {

    private String token;
    private Toolbar toolbarContact;
    private RecyclerView recyclerContacts;
    private Button buttonDownload;
    private Button buttonClear;
    private TextView textTitle;
    private List<ContactResponseModel.DataBean.ContactsBean> contactsList;
    private ContactPresenter contactPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Intent intent = getIntent();
        token = intent.getStringExtra(Constants.TOKEN_KEY);

        initView();
        toolbarSetting();
        permissionCheck();
    }

    private void permissionCheck() {
        if (checkSelfPermission(Manifest.permission_group.CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS}, Constants.CONTACTS_REQUEST_CODE);
            return;
        }
    }


    private void toolbarSetting() {
        toolbarContact.setTitle("");
        setSupportActionBar(toolbarContact);
        textTitle = ((TextView) toolbarContact.findViewById(R.id.textTitle));
        textTitle.setText(getResources().getText(R.string.contacts_Activity));
    }


    private void initView() {
        toolbarContact = ((Toolbar) findViewById(R.id.toolbar_contact));
        recyclerContacts = ((RecyclerView) findViewById(R.id.recycler_contacts));
        buttonDownload = ((Button) findViewById(R.id.download));
        buttonClear = ((Button) findViewById(R.id.clear));
        buttonClear.setOnClickListener(this);
        buttonDownload.setOnClickListener(this);

        contactPresenter = new ContactPresenterImpl(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.download:
                if (null != token) {
                    contactPresenter.downloadContact(token);
                }
                break;
            case R.id.clear:
                ContactsUtils.deleteAll(getApplicationContext());
                recyclerContacts.setVisibility(View.INVISIBLE);
                Log.e("aaa", "clear over");
                break;
        }

    }

//    @Override
//    public void onDownloadSuccess(ContactResponseModel contactResponseModel) {
//        contactsList=contactResponseModel.getData().getContacts();
//        Log.e("aaa",contactsList.size()+"");
//        if (null!=contactsList&&contactsList.size()>0){
//            writeintoPhone();
//            recyclerContacts.setVisibility(View.VISIBLE);
//            showContacts();
//            ToastUtil.show(getApplicationContext(), R.string.contacts_downloaded);
//        }
//    }

    private void showContacts() {
        recyclerContacts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ContactAdapter adapter = new ContactAdapter(getApplicationContext(), contactsList);
        recyclerContacts.setAdapter(adapter);
    }

    private void writeintoPhone() {
        for (int i = 0; i < contactsList.size(); i++) {
            String name = contactsList.get(i).getName();
            String phone = contactsList.get(i).getPhone();
            ContactsUtils.addContact(getApplicationContext(), name, phone);
        }
    }

//    @Override
//    public void onDownloadSuccess(Object reseponse) {
//        if (null!=reseponse){
//            contactsList= ((ContactResponseModel) reseponse).getData().getContacts();
//            if (null!=contactsList&&contactsList.size()>0){
//            writeintoPhone();
//            recyclerContacts.setVisibility(View.VISIBLE);
//            showContacts();
//            ToastUtil.show(getApplicationContext(), R.string.contacts_downloaded);
//        }
//        }else {
//            Log.e("response","CONTACT IS NULL");
//        }
//
//
//
//    }
//
//    @Override
//    public void onDownloadFailure() {
//        ToastUtil.show(getApplicationContext(), getResources().getString(R.string.download_failure));
//    }

    @Override
    public void onHttpSuccess(Object response) {
        if (null != response) {
            contactsList = ((ContactResponseModel) response).getData().getContacts();
            if (null != contactsList && contactsList.size() > 0) {
                writeintoPhone();
                recyclerContacts.setVisibility(View.VISIBLE);
                showContacts();
                ToastUtil.show(getApplicationContext(), R.string.contacts_downloaded);
            }
        } else {
            Log.e("response", "CONTACT IS NULL");
        }

    }

    @Override
    public void onHttpFailure() {
        ToastUtil.show(getApplicationContext(), getResources().getString(R.string.download_failure));
    }
}
