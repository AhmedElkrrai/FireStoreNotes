package com.example.firestoredemo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import com.example.firestoredemo.R;
import com.example.firestoredemo.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        binding.textViewSignup.setOnClickListener(view -> startActivity(new Intent(this, SignUpActivity.class)));
        binding.buttonLogin.setOnClickListener(view -> {
            userLogin();
            binding.buttonLogin.setClickable(false);
        });
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
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

    private void userLogin() {
        String email = Objects.requireNonNull(binding.editTextEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.editTextPassword.getText()).toString().trim();

        if (assertData(email, password)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseAuth.getInstance().updateCurrentUser(task.getResult().getUser());
                    startActivity(new Intent(MainActivity.this, NotesActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, NotesActivity.class));
        }
    }
}