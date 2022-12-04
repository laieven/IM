package com.lbj.guiguim.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lbj.guiguim.R;
import com.lbj.guiguim.controller.activity.LoginActivity;
import com.lbj.guiguim.model.Model;

/**
 * 设置页面
 */
public class SettingFragment extends Fragment {

    private Button bt_setting_logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_setting, null);

        initView(view);
        return view;
    }

    private void initView(View view) {
        bt_setting_logout = view.findViewById(R.id.bt_setting_logout);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        bt_setting_logout.setText("退出登录(" + EMClient.getInstance().getCurrentUser() + ")");

        //退出逻辑
        bt_setting_logout.setOnClickListener(view -> {
            //去环信服务器进行退出（新起线程）
            Model.getInstance().getGlobalThreadPool().execute(() -> {
                //登录环信服务器
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        //退出之后关闭DBHelper
                        Model.getInstance().getDbManager().close();
                        //成功退出，更新UI，返回登录界面
                        getActivity().runOnUiThread(() -> {
                            Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();
                        });
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);

                        //关闭当前页面
                        getActivity().finish();
                    }

                    @Override
                    public void onError(int code, String error) {
                        //失败退出
                        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "退出失败", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                        //正在退出
                    }
                });
            });
        });
    }
}
