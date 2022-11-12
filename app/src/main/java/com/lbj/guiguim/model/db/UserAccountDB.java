package com.lbj.guiguim.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.lbj.guiguim.model.dao.UserAccountTable;

public class UserAccountDB extends SQLiteOpenHelper {
    //构造方法
    public UserAccountDB(@Nullable Context context) {
        super(context, "account.db", null, 1);
    }

    //数据库创建时调用
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sql语句
        sqLiteDatabase.execSQL(UserAccountTable.CREATE_TAB);
    }

    //数据库更新时调用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
