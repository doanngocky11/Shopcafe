package com.example.shopcafe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shopcafe.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shopcafe.db";
    private static final int DATABASE_VERSION = 2; // ✨ TĂNG VERSION

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIREBASE_UID = "firebase_uid"; // ✨ MỚI
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone_number";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_BANK_NAME = "bank_name";
    private static final String COLUMN_BANK_ACCOUNT_NUMBER = "bank_account_number";
    private static final String COLUMN_BANK_ACCOUNT_NAME = "bank_account_name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_AVATAR_URL = "avatar_url";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FIREBASE_UID + " TEXT UNIQUE," // ✨ UNIQUE để tránh trùng
                + COLUMN_NAME + " TEXT,"
                + COLUMN_AGE + " INTEGER,"
                + COLUMN_GENDER + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_ADDRESS + " TEXT,"
                + COLUMN_BANK_NAME + " TEXT,"
                + COLUMN_BANK_ACCOUNT_NUMBER + " TEXT,"
                + COLUMN_BANK_ACCOUNT_NAME + " TEXT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_AVATAR_URL + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Thêm cột firebase_uid nếu upgrade từ version cũ
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " +
                    COLUMN_FIREBASE_UID + " TEXT");
        }
    }

    // ✨ Thêm user mới
    public long addUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_FIREBASE_UID, user.getFirebaseUid());
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_AGE, user.getAge());
        values.put(COLUMN_GENDER, user.getGender());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PHONE, user.getPhoneNumber());
        values.put(COLUMN_ADDRESS, user.getAddress());
        values.put(COLUMN_BANK_NAME, user.getBankName());
        values.put(COLUMN_BANK_ACCOUNT_NUMBER, user.getBankAccountNumber());
        values.put(COLUMN_BANK_ACCOUNT_NAME, user.getBankAccountName());
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_AVATAR_URL, user.getAvatarUrl());

        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    // ✨ Cập nhật user
    public int updateUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_FIREBASE_UID, user.getFirebaseUid());
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_AGE, user.getAge());
        values.put(COLUMN_GENDER, user.getGender());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PHONE, user.getPhoneNumber());
        values.put(COLUMN_ADDRESS, user.getAddress());
        values.put(COLUMN_BANK_NAME, user.getBankName());
        values.put(COLUMN_BANK_ACCOUNT_NUMBER, user.getBankAccountNumber());
        values.put(COLUMN_BANK_ACCOUNT_NAME, user.getBankAccountName());
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_AVATAR_URL, user.getAvatarUrl());

        int rowsAffected = db.update(TABLE_USERS, values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
        return rowsAffected;
    }

    // ✨ MỚI: Lấy user theo Firebase UID
    public UserModel getUserByFirebaseUid(String firebaseUid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null,
                COLUMN_FIREBASE_UID + " = ?",
                new String[]{firebaseUid},
                null, null, null);

        UserModel user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = cursorToUser(cursor);
            cursor.close();
        }
        db.close();
        return user;
    }

    // ✨ MỚI: Kiểm tra user đã tồn tại chưa
    public boolean isUserExists(String firebaseUid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID},
                COLUMN_FIREBASE_UID + " = ?",
                new String[]{firebaseUid},
                null, null, null);
        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return exists;
    }

    // Xóa user
    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // Lấy 1 user theo ID
    public UserModel getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);

        UserModel user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = cursorToUser(cursor);
            cursor.close();
        }
        db.close();
        return user;
    }

    // Lấy tất cả users
    public List<UserModel> getAllUsers() {
        List<UserModel> userList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                UserModel user = cursorToUser(cursor);
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    // ✨ Cập nhật: Chuyển Cursor thành UserModel
    private UserModel cursorToUser(Cursor cursor) {
        int firebaseUidIndex = cursor.getColumnIndex(COLUMN_FIREBASE_UID);
        String firebaseUid = (firebaseUidIndex >= 0) ? cursor.getString(firebaseUidIndex) : null;

        return new UserModel(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                firebaseUid,
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BANK_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BANK_ACCOUNT_NUMBER)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BANK_ACCOUNT_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR_URL))
        );
    }

    // Đếm số lượng users
    public int getUserCount() {
        String countQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
}