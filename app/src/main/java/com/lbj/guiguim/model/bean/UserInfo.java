package com.lbj.guiguim.model.bean;

/**
 * 用户信息
 */
public class UserInfo {
    private String name;//用户名
    private String huanxinId;//环信ID
    private String nick;//用户昵称
    private String photo;//头像

    public UserInfo() {
    }

    public UserInfo(String name) {
        this.name = name;
        this.huanxinId = name;
        this.nick = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHuanxinId() {
        return huanxinId;
    }

    public void setHuanxinId(String huanxinId) {
        this.huanxinId = huanxinId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", huanxinId='" + huanxinId + '\'' +
                ", nick='" + nick + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
