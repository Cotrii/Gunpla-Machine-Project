package com.mobdeve.gunplamp

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.gunplamp.Comment
import com.mobdeve.gunplamp.databinding.CommentsLayoutBinding

class CommentViewHolder(private val viewBinding: CommentsLayoutBinding): RecyclerView.ViewHolder(viewBinding.root) {

    fun bindData(comment: Comment) {

        this.viewBinding.tvUser.text = comment.username
        this.viewBinding.tvComment.text = comment.content

    }
}