package com.example.firestoredemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.firestoredemo.R;
import com.example.firestoredemo.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        binding.buttonSignUp.setOnClickListener(view -> {
            registerUser();
            binding.buttonSignUp.setClickable(false);
        });
        binding.textViewLogin.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
        binding.backButton.setOnClickListener(view -> onBackPressed());
    }

    private boolean assertData(String email, String password) {
        if (email.isEmpty()) {
            binding.editTextEmail.setError("Email is required");
            binding.editTextEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.setError("Please enter a valid email");
            binding.editTextEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            binding.editTextPassword.setError("Password is required");
            binding.editTextPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            binding.editTextPassword.setError("Minimum length of password should be 6");
            binding.editTextPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void registerUser() {
        String email = Objects.requireNonNull(binding.editTextEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.editTextPassword.getText()).toString().trim();

        if (assertData(email, password)) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(SignUpActivity.this, NotesActivity.class));
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(SignUpActivity.this, NotesActivity.class));
        }
    }
}
