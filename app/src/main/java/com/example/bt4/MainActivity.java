package com.example.bt4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bt4.data.Account;
import com.example.bt4.database.AppDatabase;
import com.example.bt4.database.helper.AccountHelper;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText username;
    private EditText password;

    private AccountHelper accountHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountHelper = new AccountHelper(this);

        username = findViewById(R.id.Username);
        password = findViewById(R.id.Password);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = username.getText().toString().trim();
                String passwordText = password.getText().toString().trim();

                if (usernameText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username and Password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Account account = accountHelper.checkCredential(usernameText, passwordText);
                    if (account != null) {
                        Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                } finally {
                    username.getText().clear();
                    password.getText().clear();
                }
            }
        });
    }
}