package com.example.hudie.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.example.hudie.R
import com.example.hudie.api.RetrofitClient
import com.example.hudie.models.DesignDetails
import com.example.hudie.models.DesignResponse
import com.hopenlib.flextools.FlexRadioGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_design)

        // val passedDesignID = intent.getStringExtra("designID")

        val submitButton: Button = findViewById<Button>(R.id.submit_design)

        val size = findViewById<FlexRadioGroup>(R.id.size)
        val color: RadioGroup = findViewById<RadioGroup>(R.id.color)
        val head: RadioGroup = findViewById<RadioGroup>(R.id.kepala)
        val hand: RadioGroup = findViewById<RadioGroup>(R.id.tangan)
        val body: RadioGroup = findViewById<RadioGroup>(R.id.badan)

        val share = findViewById<CheckBox>(R.id.shareable)

        val designName = findViewById<EditText>(R.id.design_name)


        var setting = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        var userId = setting?.getString("user_id", "0")?.toInt()

        designName.setText(intent.getStringExtra("designName"))

        color.setOnCheckedChangeListener { _, _ ->
            val color_text = colorValue(findViewById<RadioButton>(color.checkedRadioButtonId).text.toString());
            val body_text = bodyValue(findViewById<RadioButton>(body.checkedRadioButtonId).text.toString());
            val hand_text = handValue(findViewById<RadioButton>(hand.checkedRadioButtonId).text.toString());
            val head_text = headValue(findViewById<RadioButton>(head.checkedRadioButtonId).text.toString());

            val image_head = findViewById<ImageView>(R.id.imagesOfHead);
            val image_left_hand = findViewById<ImageView>(R.id.imagesOflefthand);
            val image_right_hand = findViewById<ImageView>(R.id.imagesOfrighthand);
            val image_body = findViewById<ImageView>(R.id.imagesOfbody);

            val image_head_id = resources.getIdentifier(head_text + "_" + color_text, "drawable", packageName);
            val image_hand_id = resources.getIdentifier( hand_text + "_" + color_text, "drawable", packageName);
            val image_body_id = resources.getIdentifier(body_text + "_" + color_text, "drawable", packageName);

            image_head.setImageResource(image_head_id);
            image_left_hand.setImageResource(image_hand_id);
            image_right_hand.setImageResource(image_hand_id);
            image_body.setImageResource(image_body_id);
        }
        head.setOnCheckedChangeListener { _, _ ->
            val color_text = colorValue(findViewById<RadioButton>(color.checkedRadioButtonId).text.toString());
            val head_text = headValue(findViewById<RadioButton>(head.checkedRadioButtonId).text.toString());
            val image_head = findViewById<ImageView>(R.id.imagesOfHead);
            val image_head_id = resources.getIdentifier(head_text + "_" + color_text, "drawable", packageName
            );
            image_head.setImageResource(image_head_id);
        }
        hand.setOnCheckedChangeListener { _, _ ->
            val color_text = colorValue(findViewById<RadioButton>(color.checkedRadioButtonId).text.toString());
            val hand_text = handValue(findViewById<RadioButton>(hand.checkedRadioButtonId).text.toString());
            val image_left_hand = findViewById<ImageView>(R.id.imagesOflefthand);
            val image_right_hand = findViewById<ImageView>(R.id.imagesOfrighthand);
            val image_hand_id = resources.getIdentifier( hand_text + "_" + color_text, "drawable", packageName);
            image_left_hand.setImageResource(image_hand_id);
            image_right_hand.setImageResource(image_hand_id);
        }
        body.setOnCheckedChangeListener { _, _ ->
            val color_text = colorValue(findViewById<RadioButton>(color.checkedRadioButtonId).text.toString());
            val body_text = bodyValue(findViewById<RadioButton>(body.checkedRadioButtonId).text.toString());
            val image_body = findViewById<ImageView>(R.id.imagesOfbody);
            val image_body_id = resources.getIdentifier(body_text + "_" + color_text, "drawable", packageName);
            image_body.setImageResource(image_body_id);
        }

        if (intent.getStringExtra("details") != null) {
            var detailJson = Klaxon().parse<DesignDetails>(intent.getStringExtra("details")!!)
            var detailsID = detailJson?.let { detailsJSON(it) }

            detailsID?.get("size")?.let { size.check(it) }
            detailsID?.get("warna")?.let { color.check(it) }
            detailsID?.get("kepala")?.let { head.check(it) }
            detailsID?.get("tangan")?.let { hand.check(it) }
            detailsID?.get("badan")?.let { body.check(it) }
        }

        submitButton.setOnClickListener {
            Log.i("DESIGN", "Button pressed")
            var designNameText = designName.text.toString()
            if (designNameText == null) { designNameText = "Custom Jacket" }

            val selSize = findViewById<RadioButton>(size.checkedRadioButtonId).text.toString();
            val selColor = findViewById<RadioButton>(color.checkedRadioButtonId).text.toString();
            val selHead = findViewById<RadioButton>(head.checkedRadioButtonId).text.toString();
            val selHand = findViewById<RadioButton>(hand.checkedRadioButtonId).text.toString();
            val selBody = findViewById<RadioButton>(body.checkedRadioButtonId).text.toString();

            val images = HashMap<String, String>()
            images["front-image"] = ""
            images["back-image"] = ""

            val imagesPosition = HashMap<String, HashMap<String, String>>()
            val imagesPositionFrontimage = HashMap<String, String>()
            val imagesPositionBackimage = HashMap<String, String>()

            imagesPositionFrontimage["position"] = "tengah"
            imagesPositionFrontimage["size"] = "80"
            imagesPosition["front-image"] = imagesPositionFrontimage

            imagesPositionBackimage["position"] = "tengah"
            imagesPositionBackimage["size"] = "80"
            imagesPosition["back-image"] = imagesPositionBackimage

            val details = HashMap<String, String>()
            details["size"] = selSize
            details["warna"] = colorValue(selColor)
            details["bahan"] = "fleece"
            details["kepala"] = headValue(selHead)
            details["tangan"] = handValue(selHand)
            details["badan"] = bodyValue(selBody)

            val imagesJson = JsonObject(images)
            val detailsJSON = JsonObject(details)
            val imagesPositionJson = JsonObject(imagesPosition)

            val price = calculatePrice(details)

            var shareInt: Int = 0

            if (share.isChecked) {
                shareInt = 1;
            }

            Log.i("DESIGN", "ID: " + userId.toString())
            Log.i("DESIGN", "Nama: $designNameText")
            Log.i("DESIGN", "Details: " + detailsJSON.toJsonString())
            Log.i("DESIGN", "Images: " + imagesJson.toJsonString())
            Log.i("DESIGN", "ImagesPosition: " + imagesPositionJson.toJsonString())
            Log.i("DESIGN", "Price: $price")
            Log.i("DESIGN", "Share: $shareInt")

            if (userId != null) {
                RetrofitClient.instance.createDesign(
                    user_id = userId,
                    design_name = designNameText,
                    details = detailsJSON.toJsonString(),
                    images = imagesJson.toJsonString(),
                    images_position = imagesPositionJson.toJsonString(),
                    information = "No Information",
                    price = price,
                    share = shareInt
                ).enqueue(object: Callback<DesignResponse> {
                    override fun onResponse(
                        call: Call<DesignResponse>,
                        response: Response<DesignResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.i("DESIGN", response.body().toString())
                            Toast.makeText(applicationContext, "Design berhasil terbuat!", Toast.LENGTH_LONG).show()
                        } else {
                            Log.i("DESIGN", "Design gagal dibuat: CODE" + response.code())
                            Toast.makeText(applicationContext, "Design gagal dibuat: CODE" + response.code(), Toast.LENGTH_LONG).show()
                        }
                    }
                    override fun onFailure(call: Call<DesignResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }
                })
            } else {
                Toast.makeText(applicationContext, "User belum Login!", Toast.LENGTH_LONG).show()
            }
        }
    }

    // why i made these function?
    // cuz android doesn't have goddamn value attribute

    private fun colorValue(text:String) : String {
        return if (text == "Navy"){
            "navy";
        } else if (text == "Black"){
            "black";
        } else if (text == "Grey"){
            "grey";
        } else{
            "";
        }
    }
    private fun headValue(text:String) : String {
        return if (text == "Type 1"){
            "kepala1";
        } else if (text == "Type 2"){
            "kepala2";
        } else{
            "";
        }
    }
    private fun handValue(text:String) : String {
        return if (text == "Type 1"){
            "tangan1";
        } else if (text == "Type 2"){
            "tangan2";
        } else if (text == "Type 3"){
            "tangan3";
        } else{
            "";
        }
    }
    private fun bodyValue(text:String) : String {
        return if (text == "Type 1"){
            "badan1";
        } else if (text == "Type 2"){
            "badan2";
        } else if (text == "Type 3"){
            "badan3";
        } else{
            "";
        }
    }
    private fun calculatePrice(details: HashMap<String, String>) :Int  {
        var price = 70000;

        var bahan = details["bahan"]
        var warna = details["warna"]
        var kepala = details["kepala"]
        var tangan = details["tangan"]
        var badan = details["badan"]

        Log.i("DESIGN", "details: $bahan $warna $kepala $tangan $badan")

        if (bahan == "fleece")
        {
            price += 40000
        } else if (bahan == "baby-terry")
        {
            price += 30000
        }

        if (kepala == "kepala1")
        {
            price += 5000
        } else if (kepala == "kepala2")
        {
            price += 10000
        }

        if (tangan == "tangan1")
        {
            price += 15000
        } else if (tangan == "tangan2") {
            price += 10000
        } else if (tangan == "tangan3") {
            price += 5000
        }

        if (badan == "badan1") {
            price += 5000
        } else if (badan == "badan2")
        {
            price += 15000
        }

        return price
    }
    private fun detailsJSON(details: DesignDetails): HashMap<String, Int> {
        val radioDesignID = HashMap<String, Int>()

        when (details.size) {
            "S" -> {radioDesignID["size"] = R.id.s}
            "M" -> {radioDesignID["size"] = R.id.m}
            "L" -> {radioDesignID["size"] = R.id.l}
            "XL" -> {radioDesignID["size"] = R.id.xl}
            "XXL" -> {radioDesignID["size"] = R.id.xxl}
            "XXXL" -> {radioDesignID["size"] = R.id.xxxl}
        }

        when (details.warna) {
            "navy" -> {radioDesignID["warna"] = R.id.navy}
            "black" -> {radioDesignID["warna"] = R.id.black}
            "grey" -> {radioDesignID["warna"] = R.id.grey}
        }

        when (details.kepala) {
            "kepala1" -> {radioDesignID["kepala"] = R.id.kepala1}
            "kepala2" -> {radioDesignID["kepala"] = R.id.kepala2}
        }
        
        when (details.tangan) {
            "tangan1" -> {radioDesignID["tangan"] = R.id.tangan1}
            "tangan2" -> {radioDesignID["tangan"] = R.id.tangan2}
            "tangan3" -> {radioDesignID["tangan"] = R.id.tangan3}
        }

        when (details.badan) {
            "badan1" -> {radioDesignID["badan"] = R.id.badan1}
            "badan2" -> {radioDesignID["badan"] = R.id.badan2}
        }

        return radioDesignID
    }

}