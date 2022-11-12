package com.lbj.guiguim.model;

import android.content.Context;

import com.lbj.guiguim.model.dao.UserAccountDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 数据模型层全局类
 * 统一处理模型层与控制层的交互，所有的数据要经过模型层才能给到Activity
 */
public class Model {
    private static Model model = new Model();
    private Context mContext;
    //线程池
    private ExecutorService executor = Executors.newCachedThreadPool();
    private UserAccountDao userAccountDao;

    //使用单例模式
    private Model(){

    }

    public static Model getInstance(){
        return model;
    }

    //初始化方法
    public void init(Context context){
        mContext = context;

        //创建用户账号数据库操作类对象
        userAccountDao = new UserAccountDao(context);
    }

    public ExecutorService getGlobalThreadPool(){
        return executor;
    }

    public void loginSuccess() {
    }

    //获取用户账号数据库的操作类对象
    public UserAccountDao getUserAccountDao(){
        return userAccountDao;
    }
}
