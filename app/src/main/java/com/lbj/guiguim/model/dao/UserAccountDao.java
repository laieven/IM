package com.lbj.guiguim.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lbj.guiguim.model.bean.UserInfo;
import com.lbj.guiguim.model.db.UserAccountDB;

/**
 * 用户账号数据库的类
 */
public class UserAccountDao {

    private final UserAccountDB mHelper;

    public UserAccountDao(Context context) {
        mHelper = new UserAccountDB(context);
    }


    //添加用户到数据库
    public void addAccount(UserInfo info){
        //获取数据库对象
        SQLiteDatabase database = mHelper.getReadableDatabase();

        //执行添加操作
        ContentValues values = new ContentValues();
        values.put(UserAccountTable.COL_HUANXINID, info.getHuanxinId());
        values.put(UserAccountTable.COL_NAME, info.getName());
        values.put(UserAccountTable.COL_NICK, info.getNick());
        values.put(UserAccountTable.COL_PHOTO, info.getPhoto());
        database.replace(UserAccountTable.TABLE_NAME, null, values);
    }


    //根据环信Id获取所有用户信息
    public UserInfo getAllAccountByHuanxinId(String huanxinId){
        //执行数据库对象
        SQLiteDatabase database = mHelper.getReadableDatabase();

        //执行查询语句
        String sql = "select * from " + UserAccountTable.TABLE_NAME + " where " + UserAccountTable.COL_HUANXINID + "=?";
        Cursor cursor = database.rawQuery(sql, new String[]{huanxinId});
        UserInfo userInfo = null;
        if (cursor.moveToNext()){
            userInfo = new UserInfo();
            userInfo.setHuanxinId(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_HUANXINID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NICK)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_PHOTO)));
        }

        //关闭资源
        cursor.close();
        //返回资源
        return userInfo;
    }


}
