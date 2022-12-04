package com.lbj.guiguim.controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lbj.guiguim.R;
import com.lbj.guiguim.model.Model;
import com.lbj.guiguim.model.bean.UserInfo;

/**
 * 添加联系人界面
 */
public class AddContactActivity extends Activity {
    private TextView tv_add_find;
    private EditText et_add_name;
    private TextView tv_add_name;
    private RelativeLayout rl_add;
    private Button bt_add_add;
    private UserInfo userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        //初始化view
        initView();
        initListener();
    }

    private void initListener() {
        //添加按钮的点击事件处理
        bt_add_add.setOnClickListener(v -> add());

        //查找按钮的点击事件处理
        tv_add_find.setOnClickListener(v -> find());
    }

    private void find() {
        //获取输入的用户名称
        String name = et_add_name.getText().toString();

        //校验输入的名称
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "输入用户名为空", Toast.LENGTH_SHORT).show();
            return;
        }

        //去服务器判断当前用户是否存在
        Model.getInstance().getGlobalThreadPool().execute(() -> {
            userInfo = new UserInfo(name);

            //更新UI界面
            runOnUiThread(() -> {
                rl_add.setVisibility(View.VISIBLE);
                tv_add_name.setText(userInfo.getName());
            });
        });
    }

    private void add() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //环信服务器添加好友
                try {
                    EMClient.getInstance().contactManager().addContact(userInfo.getName(),"添加好友");
                    //更新UI界面
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this, "发送添加好友邀请成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this, "发送添加好友邀请失败" + e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }

    private void initView() {
        tv_add_find = findViewById(R.id.tv_add_find);
        et_add_name = findViewById(R.id.et_add_name);
        tv_add_name = findViewById(R.id.tv_add_name);
        bt_add_add = findViewById(R.id.bt_add_add);
        rl_add = findViewById(R.id.rl_add);
    }
}
