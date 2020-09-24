package com.example.hudie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        window.statusBarColor = getColor(R.color.royalblue);
        window.navigationBarColor = getColor(R.color.royalblue);
        supportActionBar?.hide();

        val username = findViewById<EditText>(R.id.register_user_name);
        val password = findViewById<EditText>(R.id.register_password);
        val email = findViewById<EditText>(R.id.register_email);
        val fullname = findViewById<EditText>(R.id.register_fullname);
        val btnRegister = findViewById<Button>(R.id.btn_register);



        val btngotologin = findViewById<Button>(R.id.goto_login)
        btngotologin.setOnClickListener{
            val intent : Intent = Intent(this, LoginActivity::class.java);
            startActivity(intent);
            finish();
        }
    }
}