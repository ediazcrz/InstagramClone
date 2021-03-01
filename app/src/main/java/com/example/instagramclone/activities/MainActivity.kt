package com.example.instagramclone.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.instagramclone.R
import com.example.instagramclone.databinding.ActivityMainBinding
import com.example.instagramclone.fragments.ComposeFragment
import com.example.instagramclone.fragments.PostsFragment
import com.example.instagramclone.fragments.ProfileFragment
import com.parse.ParseUser
import java.text.ParseException

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val fragmentManager = supportFragmentManager
        val bottomNavView = mainBinding.bottomNavigation
        val topAppBar = mainBinding.topAppBar

        topAppBar.setOnMenuItemClickListener{ menuItem ->
            when (menuItem.itemId) {
                R.id.action_log_out -> {
                    val currentUser = ParseUser.getCurrentUser()
                    ParseUser.logOutInBackground { e: com.parse.ParseException? ->
                        if (e == null) {
                            Log.i(TAG, "Logged out ${currentUser.username}")
                            Toast.makeText(this, "You are now signed out", Toast.LENGTH_SHORT).show()
                            launchLoginActivity()
                        } else {
                            Log.e(TAG, "Error logging out", e)
                        }
                    }
                    return@setOnMenuItemClickListener true
                }
                else -> false
            }
        }

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

    private fun launchLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}