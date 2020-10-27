package com.example.hudie.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
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
import retrofit2.Callback

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        var setting = PreferenceManager.getDefaultSharedPreferences(this);
        var editor = setting.edit();
        var user_id = setting.getString("user_id", "0").toString().toInt()

        Log.i("PROFILE", user_id.toString() );

        val username_text = findViewById<EditText>(R.id.profile_username);
        val fullname_text = findViewById<EditText>(R.id.profile_fullname);
        val address_text = findViewById<EditText>(R.id.profile_address);
        val phone_text = findViewById<EditText>(R.id.profile_phone_number);
        val email = findViewById<TextView>(R.id.profile_email);

        RetrofitClient.instance.showUser(
            setting.getString("user_id", "0").toString().toInt()
        ).enqueue(object: retrofit2.Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    val response = response.body();

                    username_text.setText(response?.username)
                    fullname_text.setText(response?.full_name)
                    address_text.setText(response?.address)
                    phone_text.setText(response?.phone_number)

                    email.text = response?.email;
                } else {
                    Log.i("PROFILE", response.body().toString())
                    Toast.makeText(applicationContext, "Failed to fetch User Profile", Toast.LENGTH_LONG)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.i("PROFILE", t.message.toString())
            }
        })

        val profileSubmit = findViewById<Button>(R.id.profile_submit)
        profileSubmit.setOnClickListener {
            RetrofitClient.instance.updateUser(
                id = user_id,
                username = username_text.text.toString(),
                fullname = fullname_text.text.toString(),
                address = address_text.text.toString(),
                phone_number = phone_text.text.toString(),
                email = email.text.toString(),
                token = setting.getString("token", "").toString()
            ).enqueue(object: Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful)
                    {
                        Log.i("PROFILE", response.body().toString())
                        Toast.makeText(applicationContext, "User Profile Updated", Toast.LENGTH_LONG).show()

                        recreate()

                    } else {
                        Log.i("PROFILE", response.body().toString())
                        Toast.makeText(applicationContext, "Failed to Update Profile: CODE " + response.code().toString(), Toast.LENGTH_LONG)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    t.message?.let { it1 -> Log.i("PROFILE", it1) }
                    Toast.makeText(applicationContext, "Failed to Update Profile", Toast.LENGTH_LONG).show()
                }

            })
        }


    }
}