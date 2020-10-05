package com.example.hudie.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hudie.R
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_shop -> {
                    setContent("Shop")
                    true
                }
                R.id.menu_design -> {
                    setContent("Design")
                    true
                }
                R.id.menu_profile -> {
                    setContent("Profile")
                    true
                }
                else -> false
            }
        }
    }
    private fun setContent(content: String) {
        title = content
        tvLabel.text = content
    }
}