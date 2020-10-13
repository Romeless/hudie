package com.example.hudie.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hudie.R
import com.example.hudie.api.RetrofitClient
import com.example.hudie.models.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        window.statusBarColor = getColor(R.color.royalblue)
        window.navigationBarColor = getColor(R.color.royalblue)
        supportActionBar?.hide()

        val username = findViewById<EditText>(R.id.register_user_name)
        val password = findViewById<EditText>(R.id.register_password)
        val email = findViewById<EditText>(R.id.register_email)
        val fullname = findViewById<EditText>(R.id.register_fullname)

        val btnRegister = findViewById<Button>(R.id.btn_register);
        btnRegister.setOnClickListener{

            val username_value = username.text.toString().trim()
            val password_value = password.text.toString().trim()
            val email_value = email.text.toString().trim()
            val fullname_value = fullname.text.toString().trim()

            if(username_value.isEmpty()) {
                username.error = "Username required"
                username.requestFocus()
                return@setOnClickListener
            }

            if(password_value.isEmpty()) {
                password.error = "Password required"
                password.requestFocus()
                return@setOnClickListener
            }

            if(email_value.isEmpty()) {
                email.error = "Email required"
                email.requestFocus()
                return@setOnClickListener
            }

            if(fullname_value.isEmpty()) {
                fullname.error = "Full Name required"
                fullname.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.createUser(
                username_value,password_value,email_value,fullname_value,null, null, null
            ).enqueue(object: Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    Toast.makeText(applicationContext, response.body()?.username, Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
            })

        }

        val btnGoToLogin = findViewById<Button>(R.id.goto_login)
        btnGoToLogin.setOnClickListener{
            val intent : Intent = Intent(this, LoginActivity::class.java);
            startActivity(intent);
            finish();
        }
    }
}

