package com.lbj.guiguim.model.db;

import android.content.Context;

import com.lbj.guiguim.model.dao.ContactTableDao;
import com.lbj.guiguim.model.dao.InviteTableDao;

/**
 * 联系人和邀请信息表的操作类的管理类
 */
public class DBManager {
    private final DBHelper dbHelper;

    private final ContactTableDao contactTableDao;

    private final InviteTableDao inviteTableDao;

    public DBManager(Context context, String name){
        dbHelper = new DBHelper(context, name);

        contactTableDao = new ContactTableDao(dbHelper);

        inviteTableDao = new InviteTableDao(dbHelper);
    }

    //提供接口
    public ContactTableDao getContactTableDao(){
        return contactTableDao;
    }

    public InviteTableDao getInviteTableDao(){
        return inviteTableDao;
    }

    //关闭数据库
    public void close(){
        dbHelper.close();
    }
}
