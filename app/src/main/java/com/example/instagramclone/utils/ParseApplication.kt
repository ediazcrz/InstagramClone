package com.example.instagramclone

import android.app.Application
import com.parse.Parse
import com.parse.ParseObject

class ParseApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // Register parse models
        ParseObject.registerSubclass(Post::class.java)

        // Initializes Parse SDK as soon as the application is created
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(BuildConfig.APPLICATION_ID)
                .clientKey(BuildConfig.CLIENT_KEY)
                .server("https://parseapi.back4app.com")
                .build()
        )
    }
}