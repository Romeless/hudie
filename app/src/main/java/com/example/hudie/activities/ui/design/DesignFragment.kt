package com.example.hudie.activities.ui.design

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.beust.klaxon.JsonObject
import com.example.hudie.R
import com.example.hudie.api.RetrofitClient
import com.example.hudie.models.DesignResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DesignFragment : Fragment() {

    companion object {
        fun newInstance(): DesignFragment = DesignFragment();
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_design, container, false)
        val context = this.context;
        val submitButton: Button = view.findViewById<Button>(R.id.submit_design)

        val size: RadioGroup = view.findViewById<RadioGroup>(R.id.size)
        val color: RadioGroup = view.findViewById<RadioGroup>(R.id.color)
        val head: RadioGroup = view.findViewById<RadioGroup>(R.id.kepala)
        val hand: RadioGroup = view.findViewById<RadioGroup>(R.id.tangan)
        val body: RadioGroup = view.findViewById<RadioGroup>(R.id.badan)

        val share = view.findViewById<CheckBox>(R.id.shareable)

        val designName = view.findViewById<EditText>(R.id.design_name)

        var setting = PreferenceManager.getDefaultSharedPreferences(getContext());
        var userId = setting?.getString("user_id", "0")?.toInt()

        color.setOnCheckedChangeListener { radioGroup, i ->
            val color_text = colorValue(view.findViewById<RadioButton>(color.checkedRadioButtonId).text.toString());
            val body_text = bodyValue(view.findViewById<RadioButton>(body.checkedRadioButtonId).text.toString());
            val hand_text = handValue(view.findViewById<RadioButton>(hand.checkedRadioButtonId).text.toString());
            val head_text = headValue(view.findViewById<RadioButton>(head.checkedRadioButtonId).text.toString());

            val image_head = view.findViewById<ImageView>(R.id.imagesOfHead);
            val image_left_hand = view.findViewById<ImageView>(R.id.imagesOflefthand);
            val image_right_hand = view.findViewById<ImageView>(R.id.imagesOfrighthand);
            val image_body = view.findViewById<ImageView>(R.id.imagesOfbody);

            val image_head_id = resources.getIdentifier(head_text + "_" + color_text, "drawable",
                context?.packageName
            );
            val image_hand_id = resources.getIdentifier( hand_text + "_" + color_text, "drawable",
                context?.packageName
            );
            val image_body_id = resources.getIdentifier(body_text + "_" + color_text, "drawable",
                context?.packageName
            );

            image_head.setImageResource(image_head_id);
            image_left_hand.setImageResource(image_hand_id);
            image_right_hand.setImageResource(image_hand_id);
            image_body.setImageResource(image_body_id);
         }
        head.setOnCheckedChangeListener { radioGroup, i ->
            val color_text = colorValue(view.findViewById<RadioButton>(color.checkedRadioButtonId).text.toString());
            val head_text = headValue(view.findViewById<RadioButton>(head.checkedRadioButtonId).text.toString());
            val image_head = view.findViewById<ImageView>(R.id.imagesOfHead);
            val image_head_id = resources.getIdentifier(head_text + "_" + color_text, "drawable",
                context?.packageName
            );
            image_head.setImageResource(image_head_id);
        }
        hand.setOnCheckedChangeListener { radioGroup, i ->
            val color_text = colorValue(view.findViewById<RadioButton>(color.checkedRadioButtonId).text.toString());
            val hand_text = handValue(view.findViewById<RadioButton>(hand.checkedRadioButtonId).text.toString());
            val image_left_hand = view.findViewById<ImageView>(R.id.imagesOflefthand);
            val image_right_hand = view.findViewById<ImageView>(R.id.imagesOfrighthand);
            val image_hand_id = resources.getIdentifier( hand_text + "_" + color_text, "drawable",
                context?.packageName
            );
            image_left_hand.setImageResource(image_hand_id);
            image_right_hand.setImageResource(image_hand_id);
        }
        body.setOnCheckedChangeListener { radioGroup, i ->
            val color_text = colorValue(view.findViewById<RadioButton>(color.checkedRadioButtonId).text.toString());
            val body_text = bodyValue(view.findViewById<RadioButton>(body.checkedRadioButtonId).text.toString());
            val image_body = view.findViewById<ImageView>(R.id.imagesOfbody);
            val image_body_id = resources.getIdentifier(body_text + "_" + color_text, "drawable",
                context?.packageName
            );
            image_body.setImageResource(image_body_id);
        }

        submitButton.setOnClickListener {
            Log.i("DESIGN", "Button pressed")
            var designNameText = designName.text.toString()
            if (designNameText == null) { designNameText = "Custom Jacket" }

            val selSize = view.findViewById<RadioButton>(size.checkedRadioButtonId).text.toString();
            val selColor = view.findViewById<RadioButton>(color.checkedRadioButtonId).text.toString();
            val selHead = view.findViewById<RadioButton>(head.checkedRadioButtonId).text.toString();
            val selHand = view.findViewById<RadioButton>(hand.checkedRadioButtonId).text.toString();
            val selBody = view.findViewById<RadioButton>(body.checkedRadioButtonId).text.toString();

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
                            Toast.makeText(getContext(), "Design berhasil terbuat!", Toast.LENGTH_LONG).show()
                        } else {
                            Log.i("DESIGN", "Design gagal dibuat: CODE" + response.code())
                            Toast.makeText(getContext(), "Design gagal dibuat: CODE" + response.code(), Toast.LENGTH_LONG).show()
                        }
                    }
                    override fun onFailure(call: Call<DesignResponse>, t: Throwable) {
                        Toast.makeText(getContext(), t.message, Toast.LENGTH_LONG).show()
                    }
                })
            } else {
                Toast.makeText(getContext(), "User belum Login!", Toast.LENGTH_LONG).show()
            }
        }
        return view
    }

    // why i made these function?
    // cuz android doesn't have goddamn value attribute

    private fun colorValue(text:String) : String{
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

    private fun headValue(text:String) : String{
        return if (text == "Type 1"){
            "kepala1";
        } else if (text == "Type 2"){
            "kepala2";
        } else{
            "";
        }
    }

    private fun handValue(text:String) : String{
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

    private fun bodyValue(text:String) : String{
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

    private fun calculatePrice(details: HashMap<String, String>) :Int
    {
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
}