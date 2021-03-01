package com.example.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.instagramclone.Post
import com.example.instagramclone.adapters.PostsAdapter.PostsViewHolder
import com.example.instagramclone.databinding.ItemPostBinding

class PostsAdapter(private val context: Context, private val posts: MutableList<Post>): Adapter<PostsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = ItemPostBinding.inflate(LayoutInflater.from(context), parent, false)
        return PostsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun clear() {
        posts.clear()
        notifyDataSetChanged()
    }

    fun addAll(postList: MutableList<Post>) {
        posts.addAll(postList)
        notifyDataSetChanged()
    }

    inner class PostsViewHolder(private val binding: ItemPostBinding): ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.tvUsername.text = post.user?.username
            binding.tvDescription.text = post.description
            Glide.with(context).load(post.image?.url).into(binding.ivImage)
        }
    }

}