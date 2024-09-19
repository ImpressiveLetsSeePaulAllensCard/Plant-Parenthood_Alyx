package com.example.plantparenthood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
//import com.example.plantidentifier.databinding.ActivityMainBinding
import com.example.plantparenthood.BadgesFragment
import com.example.plantparenthood.CameraFragment
import com.example.plantparenthood.GardenFragment
import com.example.plantparenthood.R
import com.example.plantparenthood.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Initializing fragments
    private val gardenFragment = GardenFragment()
    private val cameraFragment = CameraFragment()
    private val badgesFragment = BadgesFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set default fragment - This will be the "Main page" of the app
        replaceFragment(gardenFragment)

        // Set up navigation listener
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_garden -> {
                    replaceFragment(gardenFragment)
                    true
                }
                R.id.navigation_camera -> {
                    replaceFragment(cameraFragment)
                    true
                }
                R.id.navigation_badges -> {
                    replaceFragment(badgesFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        // Replace the fragment in the container
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
