package com.lbj.guiguim.model.dao;

/**
 * 用户账号表
 */
public class UserAccountTable {
    public static final String TABLE_NAME= "table_account";
    public static final String COL_NAME = "name";
    public static final String COL_HUANXINID = "huanxinId";
    public static final String COL_NICK = "nick";
    public static final String COL_PHOTO = "photo";

    //建表语句
    public static final String CREATE_TAB ="create table "
            + TABLE_NAME + " ("
            + COL_HUANXINID + " text primary key,"
            + COL_NAME + " text,"
            + COL_NICK + " text,"
            + COL_PHOTO +" text);";


}
