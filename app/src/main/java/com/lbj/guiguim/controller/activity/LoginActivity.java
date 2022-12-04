package com.lbj.guiguim.controller.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lbj.guiguim.R;
import com.lbj.guiguim.model.Model;
import com.lbj.guiguim.model.bean.UserInfo;

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
        //输入用户名和密码进行获取和校验
        String name = mLoginName.getText().toString();
        String pwd = mLoginPwd.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)){
            Toast.makeText(this, "输入用户名和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        //登录逻辑（环信服务器--联网操作起线程）
        Model.getInstance().getGlobalThreadPool().execute(() -> {
            EMClient.getInstance().login(name, pwd, new EMCallBack() {
                @Override
                public void onSuccess() {
                    //成功处理（模型层数据处理）
                    Model.getInstance().loginSuccess(new UserInfo(name));

                    //保存用户账号信息到本地数据库
                    Model.getInstance().getUserAccountDao().addAccount(new UserInfo(name));

                    //更新UI在主线程
                    runOnUiThread(() -> {
                        //提示登录成功
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                        //跳转到主页面
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                        startActivity(intent);

                        //销毁登录页面
                        finish();
                    });
                }

                @Override
                public void onError(int code, String error) {
                //提示登录失败
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "登录失败"+ error, Toast.LENGTH_SHORT).show());

                }

                @Override
                public void onProgress(int progress, String status) {
                    //登陆过程中的处理

                }
            });
        });
    }

    //注册
    private void regist() {

        //输入用户名和密码进行获取和校验
        String name = mLoginName.getText().toString();
        String pwd = mLoginPwd.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)){
            Toast.makeText(this, "输入用户名和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
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