package com.example.instagramclone

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import com.parse.ktx.putOrIgnore

@ParseClassName("Post")
class Post : ParseObject() {
    companion object {
        const val KEY_DESCRIPTION = "description"
        const val KEY_IMAGE = "image"
        const val KEY_USER = "user"
        const val KEY_CREATED_AT = "createdAt"
    }

    var description : String?
        get() = getString(KEY_DESCRIPTION)
        set(value) = putOrIgnore(KEY_DESCRIPTION, value)

    var image : ParseFile?
        get() = getParseFile(KEY_IMAGE)
        set(value) = putOrIgnore(KEY_IMAGE, value)

    var user : ParseUser?
        get() = getParseUser(KEY_USER)
        set(value) = putOrIgnore(KEY_USER, value)
}
