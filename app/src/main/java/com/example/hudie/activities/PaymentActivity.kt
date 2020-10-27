package com.example.hudie.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hudie.R
import com.example.hudie.api.RetrofitClient
import com.example.hudie.models.OrderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val ovoButton = findViewById<Button>(R.id.ovo)
        val danaButton = findViewById<Button>(R.id.dana)
        val tvHarga = findViewById<TextView>(R.id.tv_harga)

        val ovo = findViewById<ConstraintLayout>(R.id.ovo_layout)
        val dana = findViewById<ConstraintLayout>(R.id.dana_layout)

        ovoButton.setOnClickListener {
            dana.visibility = View.GONE
            ovo.visibility = View.VISIBLE
        }

        danaButton.setOnClickListener {
            dana.visibility = View.VISIBLE
            ovo.visibility = View.GONE
        }

        var setting = PreferenceManager.getDefaultSharedPreferences(this);

        var userID = setting.getInt("user_id", 0)
        var token = setting.getString("token", "")
        val orderID = intent.getIntExtra("order_id", 0 )
        var orderDesignID = 0
        var orderStatus = 0
        var orderPrice = 0

        RetrofitClient.instance.showOrder(orderID).enqueue(object: Callback<OrderResponse> {
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if(response.isSuccessful){
                    Log.i("PAYMENT", "Order Fetch Detail Complete")

                    orderStatus = response.body()?.status!!
                    orderPrice = response.body()?.price!!
                    orderDesignID = response.body()?.designID!!
                } else {
                    Log.i("PAYMENT", response.body().toString())
                    Toast.makeText(applicationContext, "Failed to get Order Details", Toast.LENGTH_LONG)
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Log.i("PAYMENT", t.message.toString())
                Toast.makeText(applicationContext, "Failed to get Order Details", Toast.LENGTH_LONG)
            }

        })

        if (orderStatus != 1) {
            Toast.makeText(applicationContext, "Order status is not Unpaid!", Toast.LENGTH_LONG)
        }

        tvHarga.text = (orderPrice - userID).toString()

        val submitPayment = findViewById<Button>(R.id.payment_submit)
        submitPayment.setOnClickListener {
            RetrofitClient.instance.updateOrder(
                orderID = orderID,
                userID = userID,
                designID = orderDesignID,
                status = 3,
                price = orderPrice,
                token = token.toString()
            ).enqueue(object: Callback<OrderResponse> {
                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ) {
                    if (response.isSuccessful)
                    {
                        Log.i("ORDER", response.body().toString())
                        Toast.makeText(applicationContext, "Order Status Update Completed", Toast.LENGTH_LONG)

                        val intent = Intent(applicationContext, MyOrder::class.java)
                        applicationContext.startActivity(intent)
                        finish()

                    } else {
                        Log.i("ORDER", response.body().toString())
                        Toast.makeText(applicationContext, "Order Status Update Failed", Toast.LENGTH_LONG)
                    }
                }

                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    Log.i("ORDER", t.message.toString())
                    Toast.makeText(applicationContext, "Order Status Update Failed", Toast.LENGTH_LONG)
                }

            })
        }
    }
}