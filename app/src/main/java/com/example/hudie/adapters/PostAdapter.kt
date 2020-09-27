package com.example.hudie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hudie.R
import com.example.hudie.models.UserResponse
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter (private val list: ArrayList<UserResponse>): RecyclerView.Adapter<PostAdapter.PostViewHolder>()
{
    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        fun bind(userData: UserResponse)
        {
            with(itemView)
            {
                val text ="id: ${userData.id}\n" +
                        "title: ${userData.username}"
                tvText.text = text
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])
    }
}