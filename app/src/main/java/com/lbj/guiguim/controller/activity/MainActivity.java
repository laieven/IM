package com.lbj.guiguim.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.lbj.guiguim.R;
import com.lbj.guiguim.controller.fragment.ChatFragment;
import com.lbj.guiguim.controller.fragment.ContactListFragment;
import com.lbj.guiguim.controller.fragment.SettingFragment;

import java.util.PrimitiveIterator;

public class MainActivity extends FragmentActivity {

    private RadioGroup rg_main;
    private ChatFragment chatFragment;
    private ContactListFragment contactListFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();

        initListener();
    }

    private void initListener() {
        //RadioGroup的监听
        rg_main.setOnCheckedChangeListener((radioGroup, i) -> {
            Fragment fragment = null;
            switch (i){
                //选择进入某个页面
                case R.id.rb_main_chat:
                    fragment = chatFragment;
                    break;
                case R.id.rb_main_contact:
                    fragment = contactListFragment;
                    break;
                case R.id.rb_main_setting:
                    fragment = settingFragment;
                    break;
            }
            switchFragment(fragment);
        });
        //默认进入会话
        rg_main.check(R.id.rb_main_chat);
    }

    //实现切换
    private void switchFragment(Fragment fragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().replace(R.id.fl_main, fragment).commit();
    }

    private void initData() {
        chatFragment = new ChatFragment();
        contactListFragment = new ContactListFragment();
        settingFragment = new SettingFragment();
    }

    private void initView() {
        rg_main = findViewById(R.id.rg_main);

    }
}