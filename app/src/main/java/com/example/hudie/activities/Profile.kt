package com.example.hudie.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.hudie.R
import com.example.hudie.adapters.ShopCardAdapter
import com.example.hudie.api.RetrofitClient
import com.example.hudie.models.DesignResponse
import com.example.hudie.models.UserResponse
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        var setting = PreferenceManager.getDefaultSharedPreferences(this);
        var editor = setting.edit();

        Log.i("id", setting.getString("user_id", "0").toString() );
        RetrofitClient.instance.showUser(
            setting.getString("user_id", "0").toString().toInt()
        ).enqueue(object: retrofit2.Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                val username_text = findViewById<TextView>(R.id.profile_username);
                val fullname_text = findViewById<TextView>(R.id.profile_fullname);
                val address_text = findViewById<TextView>(R.id.profile_address);
                val phone_text = findViewById<TextView>(R.id.profile_phone_number);
                val email = findViewById<TextView>(R.id.profile_email);

                val response = response.body();

                username_text.text = response?.username;
                fullname_text.text = response?.full_name;
                address_text.text = response?.address;
                phone_text.text = response?.phone_number;
                email.text = response?.email;
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {

            }

        })


    }
}