package com.example.hudie.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hudie.R
import com.example.hudie.api.RetrofitClient
import com.example.hudie.models.Models
import com.example.hudie.models.TokenResponse
import com.example.hudie.models.UserResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private val list =  ArrayList<UserResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var intent: Intent;

        var setting = getSharedPreferences("Hudie", 0);
        var editor = setting.edit()

        var localtoken = setting.getString("token", " ").toString();
        //var servertoken = Models()

        Log.i("TOKEN", localtoken)

        intent = if (localtoken == " "){
            Intent(this, LoginActivity::class.java);
        } else {
            Intent(this, Home::class.java);
        }

        startActivity(intent);
        finish();
    }
}