package com.polije.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView nama = findViewById(R.id.tv_namaMain);

        String loggedInUser = getIntent().getStringExtra("USERNAME_EXTRA");

        if (loggedInUser != null && !loggedInUser.isEmpty()) {
            nama.setText("Namaku adalah " + loggedInUser);
        } else {
            nama.setText("(Belum login)");
        }

        findViewById(R.id.button_logoutMain).setOnClickListener((v) -> {
            Preferences.clearLoggedInUser(getBaseContext());
            Preferences.setLoggedInStatus(getBaseContext(), false);
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
            finish();
        });
    }
}
