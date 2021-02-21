package com.example.instagramclone

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.instagramclone.databinding.ActivityMainBinding
import com.parse.ParseException
import com.parse.ParseFile
import com.parse.ParseQuery
import com.parse.ParseUser
import java.io.File


private const val TAG = "MainActivity"
private const val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var etDescription: EditText
    private lateinit var photoFile: File
    private lateinit var ivPostImage: ImageView
    private val photoFileName: String = "photo.jpg"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        etDescription = mainBinding.etDescription
        ivPostImage = mainBinding.ivPostImage
        val btnCaptureImage = mainBinding.btnCaptureImage
        val btnSubmit = mainBinding.btnSubmit
        val btnLogOut = mainBinding.btnLogOut

        btnCaptureImage.setOnClickListener {
            launchCamera()
        }

        btnSubmit.setOnClickListener {
            val description = etDescription.text.toString()
            if (description.isEmpty()) {
                Toast.makeText(this, "Description cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (photoFile == null || ivPostImage.drawable == null) {
                Toast.makeText(this@MainActivity, "There is no image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val currentUser = ParseUser.getCurrentUser()
            savePost(description, currentUser, photoFile)
        }

        btnLogOut.setOnClickListener {
            val currentUser = ParseUser.getCurrentUser()
            ParseUser.logOutInBackground { e: ParseException? ->
                if (e == null) {
                    Log.i(TAG, "Logged out ${currentUser.username}")
                    Toast.makeText(this, "You are now signed out", Toast.LENGTH_SHORT).show()
                    launchLoginActivity()
                } else {
                    Log.e("TAG", "Error logging out", e)
                }
            }
        }
    }

    private fun launchLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun launchCamera() {
        // create Intent to take a picture and return control to the calling application
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName)

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        val fileProvider: Uri =
            FileProvider.getUriForFile(this@MainActivity, "com.codepath.fileprovider", photoFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(packageManager) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

            // by this point we have the camera photo on disk
            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)

            // Load the selected image into a preview
            ivPostImage.setImageBitmap(takenImage)
        } else {
            // Result was a failure
            Toast.makeText(this, "Picture was not taken!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getPhotoFileUri(fileName: String): File {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG)

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }

    private fun savePost(description: String, currentUser: ParseUser?, photoFile: File) {
        val post = Post()
        post.description = description
        post.image = ParseFile(photoFile)
        post.user = currentUser
        post.saveInBackground { e ->
            if (e != null) {
                Log.e(TAG, "Error while saving", e)
                Toast.makeText(this, "Error while saving!", Toast.LENGTH_SHORT).show()
            }

            Log.i(TAG, "Post save was successful!")
            etDescription.text = "".toEditable()
            ivPostImage.setImageResource(0)
        }
    }

    private fun queryPosts() {
        val query = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
        query.findInBackground { posts, e ->
            if (posts != null) {
                for (post in posts) {
                    Log.i(TAG, "Post: ${post.description}, username: ${post.user?.username}")
                }
            } else {
                Log.e(TAG, "Issues with getting posts", e)
            }
        }
    }

    private fun String.toEditable(): Editable {
        return Editable.Factory.getInstance().newEditable(this)
    }
}