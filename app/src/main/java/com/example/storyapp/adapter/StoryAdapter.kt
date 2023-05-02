package com.example.storyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.database.StoryApp
import com.example.storyapp.databinding.ItemStoryBinding

class StoryAdapter(private val callback: (story: StoryApp, imageView: View, nameView: View) -> Unit)
    : PagingDataAdapter<StoryApp, StoriesViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        val view = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        val item = getItem(position)
        holder.view.root.setOnClickListener{
            if (item != null) {
                callback.invoke(
                    item,
                    holder.view.listThumbnail,
                    holder.view.listName,
                )
            }
        }
        if (item != null) {
            holder.bind(item)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryApp>() {
            override fun areItemsTheSame(oldItem: StoryApp, newItem: StoryApp): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryApp, newItem: StoryApp): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}

class StoriesViewHolder(val view: ItemStoryBinding) : RecyclerView.ViewHolder(view.root) {
    fun bind(item: StoryApp) {

        with(view) {
            listName.text = item.name
            listThumbnail.setImageUrl(item.photoUrl, true)
        }

    }
}

private fun ImageView.setImageUrl(photoUrl: String, b: Boolean) {
    Glide.with(context)
        .load(photoUrl)
        .centerCrop()
        .into(this)
}
