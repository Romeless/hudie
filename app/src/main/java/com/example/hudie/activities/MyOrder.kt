package com.example.hudie.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hudie.R
import com.example.hudie.adapters.OrderCardAdapter
import com.example.hudie.api.RetrofitClient
import com.example.hudie.models.OrderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess

class MyOrder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order)

        var setting = PreferenceManager.getDefaultSharedPreferences(this);
        var editor = setting.edit();

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        val context = this;
        val mRecyclerView = findViewById<RecyclerView>(R.id.myorder_recycler_view)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.setHasFixedSize(true)

        RetrofitClient.instance.userOrder(
            setting.getString("user_id", "0").toString().toInt()
        ).enqueue(object: Callback<ArrayList<OrderResponse>> {
            override fun onResponse(
                call: Call<ArrayList<OrderResponse>>,
                response: Response<ArrayList<OrderResponse>>
            ) {
                if (response.isSuccessful) {
                    val designList = response.body()
                    val mRecyclerAdapter = designList?.let { OrderCardAdapter(it)  }

                    Log.i("HOME", response.body().toString())
                    mRecyclerView.adapter = mRecyclerAdapter
                    mRecyclerAdapter?.notifyDataSetChanged()
                }
                else {
                    Log.i("MYDESIGN", "ERROR" + response.code())
                }
            }

            override fun onFailure(call: Call<ArrayList<OrderResponse>>, t: Throwable) {
                Log.i("HOME", "onFailure")
                Toast.makeText(context, "Something went wrong fetching shop database!", Toast.LENGTH_LONG)
            }

        })

    }

    override fun onRestart() {
        super.onRestart()
        recreate()
    }
}