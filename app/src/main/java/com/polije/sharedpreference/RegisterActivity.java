package com.polije.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.content.Intent;

public class RegisterActivity extends AppCompatActivity {
    private EditText mUsernameView, mPasswordView, mConfirmPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsernameView = findViewById(R.id.et_emailSignup);
        mPasswordView = findViewById(R.id.et_passwordSignup);
        mConfirmPasswordView = findViewById(R.id.et_passwordSignup2);

        mConfirmPasswordView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                register();
                return true;
            }
            return false;
        });

        Button signupButton = findViewById(R.id.button_signupSignup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        mUsernameView.setError(null);
        mPasswordView.setError(null);
        mConfirmPasswordView.setError(null);
        View focusView = null;
        boolean cancel = false;

        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();

        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError("This field is required");
            focusView = mUsernameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("This field is required");
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            mConfirmPasswordView.setError("This field is required");
            focusView = mConfirmPasswordView;
            cancel = true;
        } else if (!confirmPassword.equals(password)) {
            mConfirmPasswordView.setError("Passwords do not match");
            focusView = mConfirmPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            // Save the username and password to SharedPreferences or your preferred storage
            Preferences.setRegisteredUser(getBaseContext(), username);
            Preferences.setRegisteredPass(getBaseContext(), password);

            // Set the registered status
            Preferences.setLoggedInStatus(getBaseContext(), true);

            // Proceed to the main activity
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
            finish();
        }
    }
}
