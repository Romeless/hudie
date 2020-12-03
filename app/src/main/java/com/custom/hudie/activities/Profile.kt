package com.custom.hudie.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.custom.hudie.R
import com.custom.hudie.api.RetrofitClient
import com.custom.hudie.models.UserResponse
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

        username_text.setOnFocusChangeListener { view, b ->
            if ( !b ){
                val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
                imm.hideSoftInputFromWindow(view.windowToken, 0);
            }
        }

        fullname_text.setOnFocusChangeListener{ view, b ->
            if ( !b ){
                val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
                imm.hideSoftInputFromWindow(view.windowToken, 0);
            }
        }

        address_text.setOnFocusChangeListener { view, b ->
            if ( !b ){
                val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
                imm.hideSoftInputFromWindow(view.windowToken, 0);
            }
        }

        phone_text.setOnFocusChangeListener { view, b ->
            if ( !b ){
                val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
                imm.hideSoftInputFromWindow(view.windowToken, 0);
            }
        }

        email.setOnFocusChangeListener { view, b ->
            if ( !b ){
                val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
                imm.hideSoftInputFromWindow(view.windowToken, 0);
            }
        }
        RetrofitClient.instance.showUser(
            setting.getString("user_id", "0").toString().toInt()
        ).enqueue(object: retrofit2.Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>

            ) {
                if (response.isSuccessful) {
                    val response = response.body();

                    val username_text = findViewById<TextView>(R.id.profile_username);
                    val fullname_text = findViewById<TextView>(R.id.profile_fullname);
                    val address_text = findViewById<TextView>(R.id.profile_address);
                    val phone_text = findViewById<TextView>(R.id.profile_phone_number);
                    val email = findViewById<TextView>(R.id.profile_email);



                    username_text.text = response?.username;
                    fullname_text.text = response?.full_name;
                    address_text.text = response?.address;
                    phone_text.text = response?.phone_number;
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