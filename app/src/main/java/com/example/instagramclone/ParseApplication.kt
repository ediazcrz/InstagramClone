package com.example.instagramclone

import android.app.Application
import com.parse.Parse

class ParseApplication: Application() {
    // Initializes Parse SDK as soon as the application is created
    override fun onCreate() {
        super.onCreate()

        // Register parse models
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(BuildConfig.APPLICATION_ID)
                .clientKey(BuildConfig.CLIENT_KEY)
                .server("https://parseapi.back4app.com")
                .build()
        )
    }
}