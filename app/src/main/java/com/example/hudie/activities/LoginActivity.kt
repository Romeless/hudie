package com.example.hudie.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.hudie.R

/**
 * A Login Form Example in Kotlin Android
 */

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.statusBarColor = getColor(R.color.royalblue);
        window.navigationBarColor = getColor(R.color.royalblue);
        supportActionBar?.hide();

        // get reference to all views
        val etUsername = findViewById<EditText>(R.id.et_user_name)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnGoToRegister = findViewById<Button>(R.id.goto_register);
        val btnSubmit = findViewById<Button>(R.id.btn_submit)




        // set on-click listener
        btnSubmit.setOnClickListener {
            val username = etUsername.text;
            val password = etPassword.text;
            Toast.makeText(this@LoginActivity, username, Toast.LENGTH_LONG).show()

            // your code to validate the user_name and password combination
            // and verify the same


        }

        btnGoToRegister.setOnClickListener{
            val intent : Intent = Intent(this, Register::class.java);
            startActivity(intent);
            finish();
        }
    }
}