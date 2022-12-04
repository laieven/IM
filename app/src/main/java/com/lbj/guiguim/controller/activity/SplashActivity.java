package com.lbj.guiguim.controller.activity;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.hyphenate.chat.EMClient;
import com.lbj.guiguim.R;
import com.lbj.guiguim.model.Model;
import com.lbj.guiguim.model.bean.UserInfo;

import java.util.Objects;

/**
 * 欢迎界面
 * 1. 实现逻辑：延迟两秒(handler发送延时消息)进入登录页面(loginActivity)
 * 2. 之前没登陆过，进入登录页面，登录过直接进入主页面
 */
@SuppressWarnings("all")
public class SplashActivity extends Activity {

    private static final String TAG = "SplashActivity";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (isFinishing()){
                return;//当前activity已经退出，直接不做处理handler的消息
            }

            //主页面还是登录页面
            toMainOrLogin();
        }
    };

    //请求环信服务器，另起一个线程（耗时操作）
    private void toMainOrLogin() {
        //可以使用线程池进行优化
//        new Thread(){
//            @Override
//            public void run() {
//                //判断当前账号是否登录过
//                if (EMClient.getInstance().isLoggedInBefore()){
//                    //获取当前用户登录信息
//
//
//                    //跳转至主界面
//                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//                    startActivity(intent);
//                } else {
//                    //跳转至登录页面
//                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                    startActivity(intent);
//
//                }
//
//                //结束当前页面（return的时候，需要返回值欢迎界面）
//                finish();
//            }
//        }.start();


        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //判断当前账号是否登录过
                if (EMClient.getInstance().isLoggedInBefore()){
                    //获取当前用户登录信息
                    UserInfo userInfo = Model.getInstance().getUserAccountDao().getAllAccountByHuanxinId(EMClient.getInstance().getCurrentUser());
                    Log.i(TAG, "userInfo: " + userInfo);
                    if (Objects.isNull(userInfo)){
                        //跳转至登录
                        Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else {
                        //登录成功后
                        Model.getInstance().loginSuccess(userInfo);

                        //跳转至主界面
                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                    }


                } else {
                    //跳转至登录页面
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                //结束当前页面（return的时候，需要返回值欢迎界面）
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //设置顶部状态栏为透明
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        //延时消息
        handler.sendMessageDelayed(Message.obtain(),2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}