package com.example.hudie.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hudie.models.Models
import com.example.hudie.R
import com.example.hudie.models.UserResponse


class MainActivity : AppCompatActivity() {

    private val list =  ArrayList<UserResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var setting = getSharedPreferences("Hudie", 0);
        var editor = setting.edit();

        var localtoken = setting.getString("token", " ").toString();
        var servertoken = Models();

        var intent:Intent;


        intent = if (localtoken != servertoken.getToken()){
            Intent(this, LoginActivity::class.java);
        } else{
            Intent(this, Home::class.java);
        }
        startActivity(intent);
        finish();

    }
}