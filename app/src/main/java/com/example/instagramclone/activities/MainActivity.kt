package com.example.instagramclone.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.instagramclone.R
import com.example.instagramclone.databinding.ActivityMainBinding
import com.example.instagramclone.fragments.ComposeFragment
import com.example.instagramclone.fragments.PostsFragment
import com.example.instagramclone.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val fragmentManager = supportFragmentManager

        val bottomNavView = mainBinding.bottomNavigation

        bottomNavView.setOnNavigationItemSelectedListener{ menuItem ->
            var fragment: Fragment? = null
            when (menuItem.itemId) {
                R.id.action_home -> fragment = PostsFragment()
                R.id.action_compose -> fragment = ComposeFragment()
                R.id.action_profile -> fragment = ProfileFragment()
            }
            if (fragment != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit()
            }
            return@setOnNavigationItemSelectedListener true
        }
        // Set default selection
        bottomNavView.selectedItemId = R.id.action_home;
    }
}