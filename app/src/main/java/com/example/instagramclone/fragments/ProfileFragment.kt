package com.example.instagramclone.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.instagramclone.Post
import com.example.instagramclone.R
import com.example.instagramclone.activities.LoginActivity
import com.example.instagramclone.databinding.FragmentProfileBinding
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

private const val TAG = "ProfileFragment"

class ProfileFragment : PostsFragment() {
    override fun queryPosts() {
        val query = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        query.limit = 20
        query.addDescendingOrder(Post.KEY_CREATED_AT)
        query.findInBackground { posts, e ->
            if (posts != null) {
                for (post in posts) {
                    Log.i(TAG, "Post: ${post.description}, username: ${post.user?.username}")
                }
                allPosts.addAll(posts)
                postsAdapter.notifyDataSetChanged()
            } else {
                Log.e(TAG, "Issues with getting posts", e)
            }
        }
    }
}