package com.example.unipath1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText user, password;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(this);

        Button loginBtn = findViewById(R.id.loginButton);

        user = findViewById(R.id.user);
        password = findViewById(R.id.password);

        loginBtn.setOnClickListener(v -> {
            String _user, _password;
            _user = user.getText().toString();
            _password  = password.getText().toString();
            if (_user.isEmpty() || _password.isEmpty()) {
                Toast.makeText(getApplicationContext(),"No Empty username or Password are allowed", Toast.LENGTH_SHORT).show();
                return;
            }
            if (dataBaseHelper.checkUserData(_user, _password) != -1)
                openHomeScreen(dataBaseHelper.checkUserData(_user, _password), _user);
            else {
                Toast.makeText(getApplicationContext(),"Username or Password Incorrect !" ,Toast.LENGTH_LONG).show();
            }
        });

    }
    public void openHomeScreen(int semester_id, String _user) {
        Intent intent = new Intent(this, HomeScreen.class);
        intent.putExtra("semester_id", semester_id);
        intent.putExtra("student_id", _user);
        startActivity(intent);
    }
}