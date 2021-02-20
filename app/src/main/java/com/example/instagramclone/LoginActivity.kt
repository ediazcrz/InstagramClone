package com.example.instagramclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.instagramclone.databinding.ActivityLoginBinding
import com.parse.LogInCallback
import com.parse.ParseException
import com.parse.ParseUser

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        if (ParseUser.getCurrentUser() != null) {
            launchMainActivity()
        }

        val etUsername = loginBinding.etUsername
        val etPassword = loginBinding.etPassword
        val btnLogin = loginBinding.btnLogin

        btnLogin.setOnClickListener() {
            Log.i(TAG, "onClick login button")
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            loginUser(username, password)
        }
    }

    private fun loginUser(username: String, password: String) {
        Log.i(TAG, "Attempting to login user $username")
        ParseUser.logInInBackground(username, password, object: LogInCallback {
            override fun done(user: ParseUser?, e: ParseException?) {
                if (e!= null) {
                    Log.e(TAG,"Issue with login", e)
                    return
                }

                // navigate to the main activity if the user has signed in properly
                launchMainActivity()
                Toast.makeText(this@LoginActivity, "Success!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun launchMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}