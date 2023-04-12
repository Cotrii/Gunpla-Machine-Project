package com.mobdeve.gunplamp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.gunplamp.CommentViewHolder
import com.mobdeve.gunplamp.databinding.CommentsLayoutBinding
import com.mobdeve.gunplamp.databinding.ItemLayoutBinding

class MyCommentsAdapter(private val data: ArrayList<Comment>) : RecyclerView.Adapter<CommentViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {

        val commentsViewBinding: CommentsLayoutBinding = CommentsLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CommentViewHolder(commentsViewBinding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindData(this.data[position])

    }

}