package com.polije.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText mViewUSer, mViewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mViewUSer=findViewById(R.id.et_emailSignin);
        mViewPassword=findViewById(R.id.et_passwordSignin);
        mViewPassword.setOnEditorActionListener((v, actionId, event)->{
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL){
                razia();
                return true;
            }
            return false;
        });

        findViewById(R.id.button_signinSignin).setOnClickListener((v)->{
            razia();
        });
        findViewById(R.id.button_signupSignin).setOnClickListener((v)->{
            startActivity(new Intent(getBaseContext(),RegisterActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Preferences.getLoggedInStatus(getBaseContext())){
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            finish();
        }
    }

    private void razia() {

        mViewUSer.setError(null);
        mViewPassword.setError(null);
        View fokus = null;
        boolean cancel = false;

        String user = mViewUSer.getText().toString();
        String password = mViewPassword.getText().toString();

        if (TextUtils.isEmpty(user)){
            mViewUSer.setError("This field is required");
            fokus = mViewUSer;
            cancel = true;
        } else if (!cekUser(user)) {
            mViewUSer.setError("This username is not found");
            fokus = mViewUSer;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)){
            mViewPassword.setError("This field is required");
            fokus = mViewPassword;
            cancel = true;
        } else if (!cekPassword(password)) {
            mViewPassword.setError("This password is incorrect");
            fokus = mViewPassword;
            cancel = true;
        }

        if (cancel) fokus.requestFocus();
        else masuk();

    }

    private void masuk() {
        String enteredUsername = mViewUSer.getText().toString(); // Ambil username yang diinput
        Preferences.setLoggedInUser(getBaseContext(), enteredUsername); // Simpan username ke SharedPreferences
        Preferences.setLoggedInStatus(getBaseContext(), true);

        Intent mainActivityIntent = new Intent(getBaseContext(), MainActivity.class);
        mainActivityIntent.putExtra("USERNAME_EXTRA", enteredUsername); // Kirim username ke MainActivity
        startActivity(mainActivityIntent);
        finish();
    }


    private boolean cekPassword(String password) {
        return password.equals(Preferences.getRegisteredPass(getBaseContext()));
    }

    private boolean cekUser(String user) {
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }
}