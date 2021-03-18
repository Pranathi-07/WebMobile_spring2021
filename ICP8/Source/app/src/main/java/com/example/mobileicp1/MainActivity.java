package com.example.mobileicp1;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button LoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        LoginBtn = findViewById(R.id.login_btn);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userNameCtrl = findViewById(R.id.usernameInput);
                EditText passwordCtrl = findViewById(R.id.passwordInput);
                TextView errorTextView = findViewById(R.id.errorText);
                String username = userNameCtrl.getText().toString();
                String password = passwordCtrl.getText().toString();
                boolean validUserFlag = false;
                boolean userNameErrorFlag = false;
                boolean pwdErrorFlag = false;
                if(username.isEmpty()) {
                    userNameErrorFlag = true;
                }
                if (password.isEmpty()) {
                    pwdErrorFlag = true;
                }
                if(!username.isEmpty() && !password.isEmpty()) {
                    if(username.equals("user") && password.equals("user")) {
                        validUserFlag = true;
                    }
                }
                if(userNameErrorFlag) {
                    errorTextView.setText("Please enter username");
                } else if(pwdErrorFlag) {
                    errorTextView.setText("Please enter password");
                } else {
                    if(validUserFlag) {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        errorTextView.setText("Invalid login credentials");
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}