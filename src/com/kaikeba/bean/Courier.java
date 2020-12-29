package com.kaikeba.bean;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Author: Àîè÷ºÀ
 * @Description:
 * @Date Created in 2020-12-12 21:47
 */
public class Courier {
    private int id;
    private String username;
    private String userPhone;
    private String IdentityCard;
    private String password;
        private int DeliveryNumber;
    private Timestamp RegistrationTime;
    private Timestamp LastLoginTime;

    public Courier() {
    }

    public Courier(int id, String username, String userPhone, String identityCard, String password, int deliveryNumber, Timestamp registrationTime, Timestamp lastLoginTime) {
        this.id = id;
        this.username = username;
        this.userPhone = userPhone;
        IdentityCard = identityCard;
        this.password = password;
        DeliveryNumber = deliveryNumber;
        RegistrationTime = registrationTime;
        LastLoginTime = lastLoginTime;
    }


    public Courier(String username, String userPhone, String identityCard, String password, int deliveryNumber) {
        this.username = username;
        this.userPhone = userPhone;
        IdentityCard = identityCard;
        this.password = password;
        DeliveryNumber = deliveryNumber;
    }

    public Courier(String username, String userPhone, String identityCard, String password) {
        this.username = username;
        this.userPhone = userPhone;
        IdentityCard = identityCard;
        this.password = password;
    }

    public Courier(String username, String userPhone, String identityCard, String password, int deliveryNumber, Timestamp lastLoginTime) {
        this.username = username;
        this.userPhone = userPhone;
        IdentityCard = identityCard;
        this.password = password;
        DeliveryNumber = deliveryNumber;
        LastLoginTime = lastLoginTime;
    }

    public Courier(String username, String userPhone, String identityCard, String password, Timestamp lastLoginTime) {
        this.username = username;
        this.userPhone = userPhone;
        IdentityCard = identityCard;
        this.password = password;
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
        Courier courier = (Courier) o;
        return id == courier.id &&
                DeliveryNumber == courier.DeliveryNumber &&
                Objects.equals(username, courier.username) &&
                Objects.equals(userPhone, courier.userPhone) &&
                Objects.equals(IdentityCard, courier.IdentityCard) &&
                Objects.equals(password, courier.password) &&
                Objects.equals(RegistrationTime, courier.RegistrationTime) &&
                Objects.equals(LastLoginTime, courier.LastLoginTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, userPhone, IdentityCard, password, DeliveryNumber, RegistrationTime, LastLoginTime);
    }

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", IdentityCard='" + IdentityCard + '\'' +
                ", password='" + password + '\'' +
                ", DeliveryNumber=" + DeliveryNumber +
                ", RegistrationTime=" + RegistrationTime +
                ", LastLoginTime=" + LastLoginTime +
                '}';
    }
}
