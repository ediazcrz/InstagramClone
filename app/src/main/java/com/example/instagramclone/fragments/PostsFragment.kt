package com.example.instagramclone.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.Post
import com.example.instagramclone.R
import com.example.instagramclone.adapters.PostsAdapter
import com.example.instagramclone.databinding.FragmentPostsBinding
import com.parse.ParseQuery

private const val TAG = "PostFragment"

class PostsFragment : Fragment() {
    private val allPosts = arrayListOf<Post>()
    private lateinit var rvPosts: RecyclerView
    private lateinit var postsAdapter : PostsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postBinding = FragmentPostsBinding.bind(view)

        rvPosts = postBinding.rvPosts
        postsAdapter = PostsAdapter(context!!, allPosts)
        val layoutManager = LinearLayoutManager(context)

        rvPosts.adapter = postsAdapter
        rvPosts.layoutManager = layoutManager
        queryPosts()
    }

    private fun queryPosts() {
        val query = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
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