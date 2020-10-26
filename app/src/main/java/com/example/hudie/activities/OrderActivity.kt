package com.example.hudie.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.beust.klaxon.Klaxon
import com.example.hudie.R
import com.example.hudie.api.RetrofitClient
import com.example.hudie.models.DesignDetails
import com.example.hudie.models.OrderResponse
import com.example.hudie.models.UserResponse
import com.google.gson.JsonParser
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        var details = intent.getStringExtra("details")
        var images = intent.getStringExtra("images")
        var imagesPosition = intent.getStringExtra("imagesPosition")

        val detailsArray = Klaxon().parse<DesignDetails>(details.toString())

        Log.i("ORDER", "DETAILS: $details")

        val headText = detailsArray?.kepala.toString()
        val handText = detailsArray?.tangan.toString()
        val bodyText = detailsArray?.badan.toString()
        val colorText = detailsArray?.warna.toString()

        val imageHead = findViewById<ImageView>(R.id.imagesOfHead);
        val imageLeftHand = findViewById<ImageView>(R.id.imagesOflefthand);
        val imageRightHand = findViewById<ImageView>(R.id.imagesOfrighthand);
        val imageBody = findViewById<ImageView>(R.id.imagesOfbody);

        val imageHeadId = resources.getIdentifier(headText + "_" + colorText, "drawable", packageName);
        val imageHandId = resources.getIdentifier( handText + "_" + colorText, "drawable", packageName);
        val imageBodyId = resources.getIdentifier(bodyText + "_" + colorText, "drawable", packageName);

        imageHead.setImageResource(imageHeadId);
        imageLeftHand.setImageResource(imageHandId);
        imageRightHand.setImageResource(imageHandId);
        imageBody.setImageResource(imageBodyId);

        var setting = getSharedPreferences("Hudie", 0)
        var userID = setting.getString("user_id", "0").toString().toInt()

        var email = findViewById<TextView>(R.id.emailBox)
        var address = findViewById<EditText>(R.id.addressBox)
        var phone = findViewById<EditText>(R.id.phoneBox)

        RetrofitClient.instance.showUser(userID).enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.i("ORDER", "User data fetch success!")

                email.text = response?.body()?.email.toString()
                address.setText(response?.body()?.address.toString())
                phone.setText(response?.body()?.phone_number.toString())
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.i("ORDER", "User data fetch fails!")
                email.text = "user@gmail.com"
            }
        })

        val pricePerQty = intent.getIntExtra("price", 0)
        var price = findViewById<TextView>(R.id.priceBox)
        price.text = (pricePerQty + 20000).toString()

        var qty = findViewById<EditText>(R.id.qtyBox)
        qty.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //
            }
            override fun afterTextChanged(s: Editable?) {
                price.text = (qty.text.toString().toInt() * pricePerQty + 20000).toString()
            }
        })

        var information = findViewById<EditText>(R.id.information)

        var makeOrderButton = findViewById<Button>(R.id.make_order)
        makeOrderButton.setOnClickListener {

            var designID = intent.getIntExtra("designID", 0)

            var qtyText = qty.text.toString().toInt()
            var addressText = address.text.toString()
            var phoneText = phone.text.toString()
            var informationText = information.text.toString()
            var priceText = pricePerQty * qtyText + 20000
            var emailText = email.text.toString()

            Log.i("ORDER", userID.toString())
            Log.i("ORDER", designID.toString())
            Log.i("ORDER", emailText)
            Log.i("ORDER", qtyText.toString())
            Log.i("ORDER", addressText)
            Log.i("ORDER", informationText)
            Log.i("ORDER", priceText.toString())
            Log.i("ORDER", phoneText)
            Log.i("ORDER", details.toString())
            Log.i("ORDER", images.toString())
            Log.i("ORDER", imagesPosition.toString())

            RetrofitClient.instance.createOrder(
                userID = userID, designID = designID, email = emailText, qty = qtyText, address = addressText, information = informationText, price = priceText, phoneNumber = phoneText, details = details, images = images, imagesPosition = imagesPosition
            ).enqueue(object: Callback<OrderResponse> {
                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ) {
                    Log.i("ORDER", "Order berhasil dibuat: ID")
                    Toast.makeText(applicationContext, "Order berhasil dibuat: ID" + response?.body()?.id.toString(), Toast.LENGTH_LONG).show()

                    val intent = Intent(this@OrderActivity, MainActivity::class.java);
                    startActivity(intent);
                    finish();
                }
                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    Log.i("ORDER", call.toString())
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}