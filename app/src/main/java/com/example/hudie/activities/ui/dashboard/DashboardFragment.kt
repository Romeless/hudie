package com.example.hudie.activities.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

        return view
    }

}