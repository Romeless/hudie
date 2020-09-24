package com.example.hudie

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private val list =  ArrayList<PostResponse>()

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