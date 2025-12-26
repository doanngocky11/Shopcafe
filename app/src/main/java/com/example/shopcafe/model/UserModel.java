package com.example.shopcafe.model;

import java.io.Serializable;

public class UserModel implements Serializable {
    private int id;
    private String firebaseUid;  // ✨ THÊM MỚI
    private String name;
    private int age;
    private String gender;
    private String email;
    private String phoneNumber;
    private String address;
    private String bankName;
    private String bankAccountNumber;
    private String bankAccountName;
    private String username;
    private String avatarUrl;

    // Constructor đầy đủ
    public UserModel(int id, String firebaseUid, String name, int age, String gender,
                     String email, String phoneNumber, String address,
                     String bankName, String bankAccountNumber, String bankAccountName,
                     String username, String avatarUrl) {
        this.id = id;
        this.firebaseUid = firebaseUid;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.bankAccountName = bankAccountName;
        this.username = username;
        this.avatarUrl = avatarUrl;
    }

    // Constructor cũ (để tương thích) - có thể xóa sau
    public UserModel(int id, String name, int age, String gender,
                     String email, String phoneNumber, String address,
                     String bankName, String bankAccountNumber, String bankAccountName,
                     String username, String avatarUrl) {
        this(id, null, name, age, gender, email, phoneNumber, address,
                bankName, bankAccountNumber, bankAccountName, username, avatarUrl);
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirebaseUid() { return firebaseUid; }
    public void setFirebaseUid(String firebaseUid) { this.firebaseUid = firebaseUid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getBankAccountNumber() { return bankAccountNumber; }
    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountName() { return bankAccountName; }
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}