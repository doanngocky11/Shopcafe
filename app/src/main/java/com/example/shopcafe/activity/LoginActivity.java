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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;

    EditText usernameInput, passwordInput;
    Button loginBtn, signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_account);

        // Khởi tạo Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginBtn = findViewById(R.id.login_btn);
        signupBtn = findViewById(R.id.signup_btn);

        // Nhận dữ liệu từ SignupActivity (nếu có)
        Intent intent = getIntent();
        if (intent != null) {
            String email = intent.getStringExtra("email");
            String password = intent.getStringExtra("password");
            if (email != null && password != null) {
                usernameInput.setText(email);
                passwordInput.setText(password);
            }
        }

        // Xử lý sự kiện nút Login
        loginBtn.setOnClickListener(v -> {
            String email = usernameInput.getText().toString().trim();
            String pass = passwordInput.getText().toString().trim();

            // Kiểm tra input rỗng
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra định dạng email
            if (!isValidEmail(email)) {
                Toast.makeText(this, "Địa chỉ email không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Đăng nhập với Firebase
            loginWithFirebase(email, pass);
        });

        // Nút Sign Up
        signupBtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent1);
        });
    }

    // Kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Đăng nhập với Firebase Authentication
    private void loginWithFirebase(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Đăng nhập thành công
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!",
                                    Toast.LENGTH_SHORT).show();

                            // Chuyển sang MainActivity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Đăng nhập thất bại
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // KHÔNG tự động chuyển sang MainActivity nữa
        // Để user phải đăng nhập thủ công
    }
}