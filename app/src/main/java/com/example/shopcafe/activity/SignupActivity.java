package com.example.shopcafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopcafe.R;
import com.example.shopcafe.database.DatabaseHelper;
import com.example.shopcafe.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private FirebaseAuth mAuth;

    EditText username, email, password, repassword;
    Button signupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        // Khởi tạo Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signupbtn = findViewById(R.id.signupbtn);

        signupbtn.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String mail = email.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String repass = repassword.getText().toString().trim();

            // Kiểm tra input rỗng
            if (user.isEmpty() || mail.isEmpty() || pass.isEmpty() || repass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra định dạng email
            if (!isValidEmail(mail)) {
                Toast.makeText(this, "Địa chỉ email không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra mật khẩu khớp
            if (!pass.equals(repass)) {
                Toast.makeText(this, "Mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra độ dài mật khẩu
            if (pass.length() < 6) {
                Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Đăng ký với Firebase
            registerWithFirebase(mail, pass);
        });
    }

    // Kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Đăng ký tài khoản với Firebase Authentication
    private void registerWithFirebase(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();

                            if (firebaseUser != null) {
                                String uid = firebaseUser.getUid();

                                // ✨ Tạo user mới trong SQLite với Firebase UID
                                DatabaseHelper dbHelper = new DatabaseHelper(SignupActivity.this);
                                UserModel newUser = new UserModel(
                                        0,              // Auto increment
                                        uid,            // Firebase UID
                                        "",             // Name - để trống, user sẽ điền sau
                                        0,              // Age
                                        "",             // Gender
                                        email,          // Email từ Firebase
                                        "",             // Phone
                                        "",             // Address
                                        "",             // Bank name
                                        "",             // Bank account number
                                        "",             // Bank account name
                                        username.getText().toString().trim(), // Username
                                        null            // Avatar URL
                                );

                                long result = dbHelper.addUser(newUser);

                                if (result > 0) {
                                    Log.d(TAG, "User added to SQLite with ID: " + result);
                                } else {
                                    Log.e(TAG, "Failed to add user to SQLite");
                                }
                            }

                            // Đăng xuất ngay sau khi đăng ký
                            mAuth.signOut();

                            Toast.makeText(SignupActivity.this,
                                    "Đăng ký thành công! Vui lòng đăng nhập.",
                                    Toast.LENGTH_SHORT).show();

                            // Chuyển về LoginActivity
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("password", password);
                            startActivity(intent);
                            finish();
                        } else {
                            // Xử lý lỗi đăng ký...
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            String errorMessage = "Đăng ký thất bại!";

                            if (task.getException() != null) {
                                String error = task.getException().getMessage();
                                if (error != null) {
                                    if (error.contains("already in use")) {
                                        errorMessage = "Email này đã được sử dụng!";
                                    } else if (error.contains("weak password")) {
                                        errorMessage = "Mật khẩu quá yếu!";
                                    } else if (error.contains("malformed")) {
                                        errorMessage = "Email không hợp lệ!";
                                    }
                                }
                            }

                            Toast.makeText(SignupActivity.this, errorMessage,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}