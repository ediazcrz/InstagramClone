package com.example.instagramclone.fragments

import android.util.Log
import com.example.instagramclone.Post
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