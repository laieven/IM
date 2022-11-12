package com.lbj.guiguim.controller.activity;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lbj.guiguim.R;
import com.lbj.guiguim.model.Model;

/**
 * 登录页面
 */
public class LoginActivity extends Activity {

    private EditText mLoginName;
    private EditText mLoginPwd;
    private Button mLoginRegister;
    private Button mLoginLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        initListener();
    }

    private void initListener() {
        mLoginRegister.setOnClickListener(view -> {
            regist();
        });

        mLoginLogin.setOnClickListener(view -> {
            login();
        });
    }

    //登录
    private void login() {

    }

    //注册
    private void regist() {

        //输入用户名和密码进行获取和校验
        String name = mLoginName.getText().toString();
        String pwd = mLoginPwd.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)){
            Toast.makeText(this, "输入用户名和密码不能为空", Toast.LENGTH_SHORT).show();
        }

        //环信服务器进行注册
        Model.getInstance().getGlobalThreadPool().execute(() -> {
            try {
                EMClient.getInstance().createAccount(name, pwd);
                //更新页面，当前是子线程无法直接更新UI
                runOnUiThread(() -> {
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                });
            } catch (HyphenateException e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
                });
                e.printStackTrace();
            }
        });
    }

    private void initView() {
        mLoginName = findViewById(R.id.et_login_name);
        mLoginPwd = findViewById(R.id.et_login_pwd);
        mLoginRegister = findViewById(R.id.bt_login_register);
        mLoginLogin = findViewById(R.id.bt_login_login);
    }
}