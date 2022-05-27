package com.example.weatherapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNav : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        setContentView(R.layout.activity_main)
        loadFragment(CurrentFragment())

        bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener  {
            when(it.itemId){
                R.id.currentFragment -> {
                    loadFragment(CurrentFragment())
                    true
                }
                R.id.weeklyFragment -> {
                    // Respond to navigation item 2 click
                    loadFragment(WeeklyFragment())
                    true
                }
                R.id.hourlyFragment -> {
                    // Respond to navigation item 2 click
                    loadFragment(HourlyFragment())
                    true
                }
                R.id.searchFragment -> {
                    loadFragment(SearchFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}