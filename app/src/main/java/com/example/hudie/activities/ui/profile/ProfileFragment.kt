package com.example.hudie.activities.ui.profile

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.hudie.MyOrder
import com.example.hudie.R
import com.example.hudie.activities.MainActivity
import com.example.hudie.activities.MyDesign
import com.example.hudie.activities.Profile

class ProfileFragment : Fragment() {


    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment();
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var setting = PreferenceManager.getDefaultSharedPreferences(this.context);
        var editor = setting.edit();

        val context = this.context;
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val profile_button = view.findViewById<Button>(R.id.profile_button_page);
        val myorder_button = view.findViewById<Button>(R.id.profile_myorder_page);
        val mydesign_button = view.findViewById<Button>(R.id.profile_mydesign_page);
        val logout = view.findViewById<Button>(R.id.logout_button_profile);

        profile_button.setOnClickListener {
            var intent: Intent;
            intent = Intent(context, Profile::class.java);
            Log.i("tesr", "masuk")
            startActivity(intent);
        }

        mydesign_button.setOnClickListener {
            var intent: Intent;
            intent = Intent(context, MyDesign::class.java);
            Log.i("tesr", "masuk")
            startActivity(intent);
        }

        myorder_button.setOnClickListener {
            var intent: Intent;
            intent = Intent(context, MyOrder::class.java);
            Log.i("tesr", "masuk")
            startActivity(intent);
        }

        logout.setOnClickListener {
            editor.clear().commit();
            val intent = Intent(this.context, MainActivity::class.java);
            startActivity(intent);
        }

        return view
    }
}