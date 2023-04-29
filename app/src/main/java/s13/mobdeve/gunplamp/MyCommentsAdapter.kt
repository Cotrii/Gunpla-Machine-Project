package s13.mobdeve.gunplamp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import s13.mobdeve.gunplamp.CommentViewHolder
import s13.mobdeve.gunplamp.databinding.CommentsLayoutBinding
import s13.mobdeve.gunplamp.databinding.ItemLayoutBinding

/**
 * MyCommentsAdapter holds Comments data of a Post
 */
class MyCommentsAdapter(private val data: ArrayList<Comment>) : RecyclerView.Adapter<CommentViewHolder>()
{
    /** Sets viewBinding for viewholder*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val commentsViewBinding: CommentsLayoutBinding = CommentsLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CommentViewHolder(commentsViewBinding)
    }

    /** Get the item count of data */
    override fun getItemCount(): Int {
        return data.size
    }

    /** Bind data to viewholder*/
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindData(this.data[position])

    }
    /** Replace data for an arraylist*/
    fun setData(newCommentList: List<Comment>) {
        // Clear the existing data.
        data.clear()
        // Add the new data to the list.
        data.addAll(newCommentList)
        // Notify the adapter that the data has changed.
        notifyDataSetChanged()
    }


}