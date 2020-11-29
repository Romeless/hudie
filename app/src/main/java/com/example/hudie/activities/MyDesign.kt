package com.example.hudie.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hudie.R
import com.example.hudie.adapters.ShopCardAdapter
import com.example.hudie.api.RetrofitClient
import com.example.hudie.decorations.GridSpacingItemDecoration
import com.example.hudie.models.DesignResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyDesign : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var setting = PreferenceManager.getDefaultSharedPreferences(this);
        var editor = setting.edit();

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_design)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL


        val context = this;
        val mRecyclerView = findViewById<RecyclerView>(R.id.mydesign_recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.addItemDecoration(GridSpacingItemDecoration(3, 20, true))
        mRecyclerView.layoutManager = GridLayoutManager(this, 3)

        RetrofitClient.instance.userDesign(
            setting.getString("user_id", "0").toString().toInt()
        ).enqueue(object: Callback<ArrayList<DesignResponse>> {
            override fun onResponse(
                call: Call<ArrayList<DesignResponse>>,
                response: Response<ArrayList<DesignResponse>>
            ) {

                if (response.isSuccessful) {
                    val designList = response.body()
                    val mRecyclerAdapter = designList?.let { ShopCardAdapter(it)  }

                    Log.i("HOME", response.body().toString())
                    mRecyclerView.adapter = mRecyclerAdapter
                    mRecyclerAdapter?.notifyDataSetChanged()
                }
                else {
                    Log.i("MYDESIGN", "ERROR" + response.code())
                }

            }

            override fun onFailure(call: Call<ArrayList<DesignResponse>>, t: Throwable) {
                Log.i("HOME", "onFailure")
                Toast.makeText(context, "Something went wrong fetching shop database!", Toast.LENGTH_LONG)
            }

        })

    }
}