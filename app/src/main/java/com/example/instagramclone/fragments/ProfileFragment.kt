package com.example.instagramclone.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.instagramclone.R
import com.example.instagramclone.activities.LoginActivity
import com.example.instagramclone.databinding.FragmentProfileBinding
import com.parse.ParseException
import com.parse.ParseUser

private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileBinding = FragmentProfileBinding.bind(view)
        val btnLogOut = profileBinding.btnLogOut

        btnLogOut.setOnClickListener {
            val currentUser = ParseUser.getCurrentUser()
            ParseUser.logOutInBackground { e: ParseException? ->
                if (e == null) {
                    Log.i(TAG, "Logged out ${currentUser.username}")
                    Toast.makeText(context, "You are now signed out", Toast.LENGTH_SHORT).show()
                    launchLoginActivity()
                } else {
                    Log.e("TAG", "Error logging out", e)
                }
            }
        }
    }

    private fun launchLoginActivity() {
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
    }
}