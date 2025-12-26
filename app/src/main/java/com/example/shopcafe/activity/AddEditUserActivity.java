package com.example.shopcafe.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.shopcafe.R;
import com.example.shopcafe.database.DatabaseHelper;
import com.example.shopcafe.model.UserModel;
import com.google.android.material.textfield.TextInputEditText;

public class AddEditUserActivity extends AppCompatActivity {

    private TextInputEditText edtName, edtUsername, edtAge, edtEmail, edtPhone, edtAddress;
    private AutoCompleteTextView edtGender;
    private TextInputEditText edtBankName, edtBankAccountNumber, edtBankAccountName;
    private Button btnSave, btnCancel;

    private DatabaseHelper dbHelper;
    private UserModel currentUser;
    private boolean isEditMode = false;
    private String firebaseUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_user);

        dbHelper = new DatabaseHelper(this);

        initViews();
        setupGenderDropdown();
        checkMode();
        setupListeners();
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtName = findViewById(R.id.edtName);
        edtUsername = findViewById(R.id.edtUsername);
        edtAge = findViewById(R.id.edtAge);
        edtGender = findViewById(R.id.edtGender);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);

        edtBankName = findViewById(R.id.edtBankName);
        edtBankAccountNumber = findViewById(R.id.edtBankAccountNumber);
        edtBankAccountName = findViewById(R.id.edtBankAccountName);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void setupGenderDropdown() {
        String[] genders = {"Nam", "Nữ", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                genders
        );
        edtGender.setAdapter(adapter);
    }

    private void checkMode() {
        firebaseUid = getIntent().getStringExtra("firebase_uid");
        currentUser = (UserModel) getIntent().getSerializableExtra("user");

        if (currentUser != null) {
            isEditMode = true;
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Chỉnh sửa thông tin");
            }
            fillUserData();
        } else {
            isEditMode = false;
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Thêm thông tin");
            }
        }
    }

    private void fillUserData() {
        edtName.setText(currentUser.getName());
        edtUsername.setText(currentUser.getUsername());
        edtAge.setText(String.valueOf(currentUser.getAge()));
        edtGender.setText(currentUser.getGender(), false);
        edtEmail.setText(currentUser.getEmail());
        edtPhone.setText(currentUser.getPhoneNumber());
        edtAddress.setText(currentUser.getAddress());

        edtBankName.setText(currentUser.getBankName());
        edtBankAccountNumber.setText(currentUser.getBankAccountNumber());
        edtBankAccountName.setText(currentUser.getBankAccountName());
    }

    private void setupListeners() {
        btnSave.setOnClickListener(v -> saveUser());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveUser() {
        if (!validateInputs()) return;

        String name = edtName.getText().toString().trim();
        String username = edtUsername.getText().toString().trim();
        int age = Integer.parseInt(edtAge.getText().toString().trim());
        String gender = edtGender.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();

        String bankName = edtBankName.getText().toString().trim();
        String bankAccountNumber = edtBankAccountNumber.getText().toString().trim();
        String bankAccountName = edtBankAccountName.getText().toString().trim();

        UserModel user;

        if (isEditMode) {
            // ===== UPDATE =====
            user = currentUser;
            user.setName(name);
            user.setUsername(username);
            user.setAge(age);
            user.setGender(gender);
            user.setEmail(email);
            user.setPhoneNumber(phone);
            user.setAddress(address);
            user.setBankName(bankName);
            user.setBankAccountNumber(bankAccountNumber);
            user.setBankAccountName(bankAccountName);

            int result = dbHelper.updateUser(user);
            if (result > 0) {
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
            }

        } else {
            // ===== INSERT / UPDATE THEO FIREBASE UID =====
            UserModel existingUser = dbHelper.getUserByFirebaseUid(firebaseUid);

            user = new UserModel(
                    0,
                    firebaseUid,
                    name,
                    age,
                    gender,
                    email,
                    phone,
                    address,
                    bankName,
                    bankAccountNumber,
                    bankAccountName,
                    username,
                    null
            );

            if (existingUser != null) {
                user.setId(existingUser.getId());
                int result = dbHelper.updateUser(user);
                if (result > 0) {
                    Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            } else {
                long id = dbHelper.addUser(user);
                if (id > 0) {
                    Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean validateInputs() {
        if (TextUtils.isEmpty(edtName.getText())) {
            edtName.setError("Vui lòng nhập họ tên");
            edtName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtAge.getText())) {
            edtAge.setError("Vui lòng nhập tuổi");
            edtAge.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtGender.getText())) {
            edtGender.setError("Vui lòng chọn giới tính");
            edtGender.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtEmail.getText())) {
            edtEmail.setError("Vui lòng nhập email");
            edtEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtPhone.getText())) {
            edtPhone.setError("Vui lòng nhập số điện thoại");
            edtPhone.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
