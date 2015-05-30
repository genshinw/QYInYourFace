package com.lzxxteam.qyinyourface.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.lzxxteam.qyinyourface.R;

public class LoginAty extends ActionBarActivity {

    private View login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_login);
        getSupportActionBar().hide();

        login = findViewById(R.id.id_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAty.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
