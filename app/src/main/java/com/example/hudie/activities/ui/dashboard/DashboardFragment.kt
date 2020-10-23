package com.example.hudie.activities.ui.dashboard

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProviders
import com.example.hudie.R
import com.example.hudie.api.RetrofitClient

class DashboardFragment : Fragment() {

    companion object {
        fun newInstance(): DashboardFragment = DashboardFragment();
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        val context = this.context;
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val submitButton: Button = view.findViewById<Button>(R.id.submit_design)

        val size: RadioGroup = view.findViewById<RadioGroup>(R.id.size)
        val color: RadioGroup = view.findViewById<RadioGroup>(R.id.color)
        val head: RadioGroup = view.findViewById<RadioGroup>(R.id.kepala)
        val hand: RadioGroup = view.findViewById<RadioGroup>(R.id.tangan)
        val body: RadioGroup = view.findViewById<RadioGroup>(R.id.badan)

        var setting = this.activity?.getSharedPreferences("Hudie", 0);
        var user_id = setting?.getString("id", "0")

        submitButton.setOnClickListener {
            val selSize = view.findViewById<RadioButton>(size.checkedRadioButtonId).text.toString();
            val selColor = view.findViewById<RadioButton>(color.checkedRadioButtonId).text.toString();
            val selHead = view.findViewById<RadioButton>(head.checkedRadioButtonId).text.toString();
            val selHand = view.findViewById<RadioButton>(hand.checkedRadioButtonId).text.toString();
            val selBody = view.findViewById<RadioButton>(body.checkedRadioButtonId).text.toString();

//            RetrofitClient.instance.createDesign(
//                user_id = user_id,
//                design_name = ,
//                details = ,
//                images = ,
//                images_position = ,
//                information = ,
//                price = ,
//                share =
//            ).enqueue(object: Callback<TokenResponse> {
//                override fun onResponse(
//                    call: Call<TokenResponse>,
//                    response: Response<TokenResponse>
//                ) {
//                    Log.i("Design Create #1", response.body().toString())
//
//
//                }
//
//                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
//                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
//                }
//            }
        }

        val color_navy_btn = view.findViewById<RadioButton>(R.id.navy);
        val color_black_btn = view.findViewById<RadioButton>(R.id.black);
        val color_grey_btn = view.findViewById<RadioButton>(R.id.grey);

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
}