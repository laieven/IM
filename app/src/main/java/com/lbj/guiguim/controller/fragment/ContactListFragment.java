package com.lbj.guiguim.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;


import com.hyphenate.easeui.modules.contact.EaseContactListFragment;
import com.hyphenate.easeui.widget.EaseSearchTextView;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.lbj.guiguim.R;
import com.lbj.guiguim.controller.activity.AddContactActivity;

public class ContactListFragment extends EaseContactListFragment {
    private EaseSearchTextView tvSearch;
    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        //添加搜索布局和标题栏
        addSearchView();

        //添加头布局
        addHeader();
    }

    private void addHeader() {

    }

    private void addSearchView() {
        //添加搜索会话布局
        View searchView = LayoutInflater.from(mContext).inflate(R.layout.contract_search, null);
        llRoot.addView(searchView, 0);
        tvSearch = searchView.findViewById(R.id.tv_search);
        tvSearch.setHint("搜索");
        //获取控件
        EaseTitleBar titleBar = findViewById(R.id.fragment_title_bar);
        //添加右侧图标
        titleBar.setRightImageResource(R.drawable.em_contact_menu_add);
        //设置右侧菜单图标点击事件(跳转到添加联系人界面)
        titleBar.setOnRightClickListener(view -> {
            Intent intent = new Intent(getActivity(), AddContactActivity.class);
            startActivity(intent);
        });
    }
}
