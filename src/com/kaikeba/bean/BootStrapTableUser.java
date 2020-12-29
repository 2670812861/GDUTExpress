package com.kaikeba.bean;

/**
 * @Author: Àîè÷ºÀ
 * @Description:
 * @Date Created in 2020-12-11 10:34
 */
public class BootStrapTableUser {
    private int id;
    private String username;
    private String userPhone;
    private String password;
    private String RegistrationTime;
    private String LastLoginTime;

    public BootStrapTableUser() {
    }

    public BootStrapTableUser(int id, String username, String userPhone, String password, String registrationTime, String lastLoginTime) {
        this.id = id;
        this.username = username;
        this.userPhone = userPhone;
        this.password = password;
        RegistrationTime = registrationTime;
        LastLoginTime = lastLoginTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegistrationTime() {
        return RegistrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        RegistrationTime = registrationTime;
    }

    public String getLastLoginTime() {
        return LastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        LastLoginTime = lastLoginTime;
    }
}
