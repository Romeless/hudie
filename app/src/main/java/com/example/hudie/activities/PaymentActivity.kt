package com.example.hudie.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hudie.R
import com.example.hudie.api.RetrofitClient

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val ovoButton = findViewById<Button>(R.id.ovo)
        val danaButton = findViewById<Button>(R.id.dana)

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

        val submitPayment = findViewById<Button>(R.id.payment_submit)
        submitPayment.setOnClickListener {  }
    }
}