package com.example.hudie.activities

import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hudie.R
import com.example.hudie.activities.ui.design.DesignFragment
import com.example.hudie.activities.ui.shop.ShopFragment
import com.example.hudie.activities.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class Home : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item
        -> when(item.itemId) {
            R.id.navigation_shop -> {
                val hFragment = ShopFragment.newInstance()
                replaceFragment(hFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_design -> {
                val dFragment = DesignFragment.newInstance()
                replaceFragment(dFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                val sFragment = ProfileFragment.newInstance()
                replaceFragment(sFragment)
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun replaceFragment(fragment: Fragment)
    {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor = getColor(R.color.royalblue);
        window.navigationBarColor = getColor(R.color.royalblue);
        supportActionBar?.hide();

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_shop, R.id.navigation_design, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }*/
}