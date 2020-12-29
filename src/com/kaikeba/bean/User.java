package com.kaikeba.bean;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Author: Àîè÷ºÀ
 * @Description:
 * @Date Created in 2020-12-12 21:47
 */
public class User {
    private int id;
    private String username;
    private String userPhone;
    private String password;
    private Timestamp RegistrationTime;
    private Timestamp LastLoginTime;
    private boolean user;

    public User() {
    }

    public User(int id, String username, String userPhone, String password, Timestamp registrationTime, Timestamp lastLoginTime) {
        this.id = id;
        this.username = username;
        this.userPhone = userPhone;
        this.password = password;
        RegistrationTime = registrationTime;
        LastLoginTime = lastLoginTime;
    }

    public User(String username, String userPhone, String password, Timestamp lastLoginTime) {
        this.username = username;
        this.userPhone = userPhone;
        this.password = password;
        LastLoginTime = lastLoginTime;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
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

    public Timestamp getRegistrationTime() {
        return RegistrationTime;
    }

    public void setRegistrationTime(Timestamp registrationTime) {
        RegistrationTime = registrationTime;
    }

    public Timestamp getLastLoginTime() {
        return LastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        LastLoginTime = lastLoginTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(username, user.username) &&
                Objects.equals(userPhone, user.userPhone) &&
                Objects.equals(password, user.password) &&
                Objects.equals(RegistrationTime, user.RegistrationTime) &&
                Objects.equals(LastLoginTime, user.LastLoginTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, userPhone, password, RegistrationTime, LastLoginTime);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", password='" + password + '\'' +
                ", RegistrationTime=" + RegistrationTime +
                ", LastLoginTime=" + LastLoginTime +
                '}';
    }
}
