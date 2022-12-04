package com.lbj.guiguim.model;

import android.content.Context;
import android.content.Intent;
import android.view.Display;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.lbj.guiguim.model.bean.InvitationInfo;
import com.lbj.guiguim.model.bean.UserInfo;
import com.lbj.guiguim.utils.Constant;
import com.lbj.guiguim.utils.SpUtils;

/**
 * 全局时间监听类
 */
public class EventListener {
    private Context mContext;
    private final LocalBroadcastManager mLBM;
    public EventListener(Context context) {
        mContext = context;
        mLBM = LocalBroadcastManager.getInstance(mContext);

        //注册一个联系人变化的监听
        EMClient.getInstance().contactManager().setContactListener(emContactListener);
    }
    private final EMContactListener emContactListener = new EMContactListener() {
        @Override
        public void onContactAdded(String hxId) {
            //联系人增加后执行的方法
            Model.getInstance().getDbManager().getContactTableDao().saveContact(new UserInfo(hxId), true);
            //发送联系人变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }

        @Override
        public void onContactDeleted(String hxId) {
            //联系人删除后执行的方法(不仅删除联系人表，也要删除邀请表)
            Model.getInstance().getDbManager().getContactTableDao().deleteContactByHxId(hxId);
            Model.getInstance().getDbManager().getInviteTableDao().removeInvitation(hxId);

            //发送广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }

        @Override
        public void onContactInvited(String hxId, String reason) {
            //接收到联系人的新邀请

            InvitationInfo invitationInfo = new InvitationInfo();
            invitationInfo.setReason(reason);
            invitationInfo.setUser(new UserInfo(hxId));
            invitationInfo.setStatus(InvitationInfo.InvitationStatus.NEW_INVITE);//新的邀请
            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

            //红点处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE,true);

            //发送邀请信息的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));

        }

        @Override
        public void onFriendRequestAccepted(String hxId) {
            //别人同意了邀请
            InvitationInfo invitationInfo = new InvitationInfo();
            invitationInfo.setUser(new UserInfo(hxId));
            invitationInfo.setStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);//别人同意了你的邀请

            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);
            //红点处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);
            //发送广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }

        @Override
        public void onFriendRequestDeclined(String hxId) {
            //别人拒绝了你的好友邀请
            //红点的处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE,true);

            //发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }
    };
}
