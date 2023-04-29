package s13.mobdeve.gunplamp

import android.text.TextUtils.substring
import androidx.recyclerview.widget.RecyclerView
import s13.mobdeve.gunplamp.Comment
import s13.mobdeve.gunplamp.databinding.CommentsLayoutBinding

/**
 * The class "CommentViewHolder" contains a username and comment. This is used to initialize
 * comment data for a single view
 */
class CommentViewHolder(private val viewBinding: CommentsLayoutBinding): RecyclerView.ViewHolder(viewBinding.root) {

    fun bindData(comment: Comment) {

        this.viewBinding.tvUser.text = comment.username
        this.viewBinding.tvComment.text = comment.content

        this.viewBinding.tvCmtDate.text = comment.commentDate.substring(4, 16)

    }
}