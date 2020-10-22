package com.example.hudie.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.hudie.R
import com.example.hudie.api.RetrofitClient
import com.example.hudie.models.Models
import com.example.hudie.models.TokenResponse
import com.example.hudie.models.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            val username = etUsername.text.toString();
            val password = etPassword.text.toString();
            if(username.isEmpty()) {
                etUsername.error = "Username required"
                etUsername.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()) {
                etPassword.error = "Password required"
                etPassword.requestFocus()
                return@setOnClickListener
            }
            RetrofitClient.instance.login(
                username,password,""
            ).enqueue(object: Callback<TokenResponse> {
                override fun onResponse(
                    call: Call<TokenResponse>,
                    response: Response<TokenResponse>
                ) {
                    Log.i("masuk", response.body().toString())
                    val token = response.body()?.token.toString();
                    val username = response.body()?.username.toString()
                    var setting = getSharedPreferences("Hudie", 0);
                    var editor = setting.edit();

                    editor.putString("token", token);
                    editor.putString("username", username);

                    val intent = Intent(this@LoginActivity, MainActivity::class.java);
                    startActivity(intent);
                    finish();
                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
            })

        }

        btnGoToRegister.setOnClickListener{
            val intent : Intent = Intent(this, Register::class.java);
            startActivity(intent);
            finish();
        }
    }
}