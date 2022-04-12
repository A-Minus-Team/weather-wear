package com.example.parsagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText etUsername;
    private EditText etPassword;

    //private TextView goback;

    private static final String TAG = "SignupActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.tvUsername);
        etPassword = findViewById(R.id.tvPassword);
        btnRegister = findViewById(R.id.btnRegister);

        //goback = findViewById(R.id.goback);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick register button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                registerUser(username, password);
            }
        });
    }

    private void registerUser(String username, String password) {
        Log.i(TAG, "Attempting login user: " + username);
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error with login", e);
                    return;
                }
                goLoginActivity();
                Toast.makeText(SignupActivity.this, "Signed up!", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void goLoginActivity(){
        Intent newActivity = new Intent(this, LoginActivity.class);
        startActivity(newActivity);
        finish();
    }

}
