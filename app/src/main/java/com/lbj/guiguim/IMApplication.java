package com.lbj.guiguim;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseIM;
import com.lbj.guiguim.model.Model;

public class IMApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false);
        options.setAutoAcceptGroupInvitation(false);
        EaseIM.getInstance().init(this, options);

        //初始化数据模型层
        Model.getInstance().init(this);

        //初始化全局上下文对象
        mContext = this;
    }

    //获取全局的上下文对象
    public static Context getGlobalApplication(){
        return mContext;
    }


}
