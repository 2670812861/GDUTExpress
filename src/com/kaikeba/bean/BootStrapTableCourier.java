package com.kaikeba.bean;

import java.sql.Timestamp;

/**
 * @Author: Àîè÷ºÀ
 * @Description:
 * @Date Created in 2020-12-11 10:34
 */
public class BootStrapTableCourier {
    private int id;
    private String username;
    private String userPhone;
    private String IdentityCard;
    private String password;
    private int DeliveryNumber;
    private String RegistrationTime;
    private String LastLoginTime;

    public BootStrapTableCourier() {
    }

    public BootStrapTableCourier(int id, String username, String userPhone, String identityCard, String password, int deliveryNumber, String registrationTime, String lastLoginTime) {
        this.id = id;
        this.username = username;
        this.userPhone = userPhone;
        IdentityCard = identityCard;
        this.password = password;
        DeliveryNumber = deliveryNumber;
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

    public String getIdentityCard() {
        return IdentityCard;
    }

    public void setIdentityCard(String identityCard) {
        IdentityCard = identityCard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDeliveryNumber() {
        return DeliveryNumber;
    }

    public void setDeliveryNumber(int deliveryNumber) {
        DeliveryNumber = deliveryNumber;
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
