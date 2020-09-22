package com.example.hudie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

/**
 * A Login Form Example in Kotlin Android
 */

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // get reference to all views
        var etUsername = findViewById<EditText>(R.id.et_user_name)
        var etPassword = findViewById<EditText>(R.id.et_password)
        var btnReset = findViewById<Button>(R.id.btn_reset)
        var btnSubmit = findViewById<Button>(R.id.btn_submit)

        btnReset.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            etUsername.setText("")
            etPassword.setText("")
        }

        // set on-click listener
        btnSubmit.setOnClickListener {
            val username = etUsername.text;
            val password = etPassword.text;
            Toast.makeText(this@LoginActivity, username, Toast.LENGTH_LONG).show()

            // your code to validate the user_name and password combination
            // and verify the same
        }
    }
}