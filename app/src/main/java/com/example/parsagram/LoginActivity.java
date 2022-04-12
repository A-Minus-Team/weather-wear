package com.example.parsagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView textSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        etUsername = findViewById(R.id.tvUsername);
        etPassword = findViewById(R.id.tvPassword);
        btnLogin = findViewById(R.id.btnLogin);

        textSignup = findViewById(R.id.textSignup);

        String text = "Don't have an account? Sign up here";

        SpannableString ss = new SpannableString(text);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                goSignupActivity();
                Toast.makeText(LoginActivity.this, "Redirecting...", Toast.LENGTH_SHORT).show();

            }
        };

        ss.setSpan(clickableSpan, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSignup.setText(ss);
        textSignup.setMovementMethod(LinkMovementMethod.getInstance());


    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting login user: " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error with login", e);
                    return;
                }
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Logged in!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void goSignupActivity(){
        Intent newActivity = new Intent(this, SignupActivity.class);
        startActivity(newActivity);
        finish();
    }
}