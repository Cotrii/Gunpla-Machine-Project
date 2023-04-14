package com.mobdeve.gunplamp

import android.net.Uri
import android.opengl.Visibility
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.gunplamp.databinding.ItemLayoutBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import kotlin.reflect.typeOf
/**
 * MyViewHolder - contains Post data to be binded into the viewholder
 */
class MyViewHolder(private val viewBinding: ItemLayoutBinding): RecyclerView.ViewHolder(viewBinding.root) {

    fun bindData(post: Post, userID: String) {
        this.viewBinding.ivUserIcon.setImageResource(getProfilePic(post.profilePic))
        this.viewBinding.tvUsername.text = post.username
        this.viewBinding.tvLocation.text = post.store.name + " - "+ post.store.city

        if(post.imagePost != null){
            if(post.imagePost.toIntOrNull() != null ){
                this.viewBinding.ivImagePosted.setImageResource((post.imagePost.toInt()))
            }
            else{
                Picasso.get().load(post.imagePost).into(this.viewBinding.ivImagePosted)
            }
        }

        if(userID != post.userID){
            this.viewBinding.ibDetailsBtn.visibility = View.GONE
        }
        else{
            this.viewBinding.ibDetailsBtn.visibility = View.VISIBLE
        }

        post.liked = userInLikeList(post, userID)

        this.viewBinding.ibFavorite.setImageResource(isLikedDrawable(post.liked))

        this.viewBinding.tvBotUsername.text = post.username
        this.viewBinding.tvDesc.text = post.caption
        this.viewBinding.tvLikeCounter.text = post.countLikes().toString() + " Likes"

        this.viewBinding.tvDate.text = post.datePosted.substring(0, 12)
    }
    // Listen if a user wants to like a post
    fun setLikeOnClickListener(onClickListener: View.OnClickListener) {
        this.viewBinding.ibFavorite.setOnClickListener(onClickListener)
    }
    // Listen if a user wants to edit its post
    fun setEditOnClickListener(onClickListener: View.OnClickListener) {
        this.viewBinding.ibDetailsBtn.setOnClickListener(onClickListener)
    }
    // This allows the user to view comments of a post
    fun setViewCommentsOnClickListener(onClickListener: View.OnClickListener) {
        this.viewBinding.tvComments.setOnClickListener(onClickListener)
    }

    /** isLikedDrawable() - Return a solid heart if a user liked the post, else if not*/
    fun isLikedDrawable(like: Boolean): Int {

        if (like) {
            return R.drawable.favorite_24
        }

        return R.drawable.not_favorite_24
    }

    /** changeLike() - Post the number of likes in a post*/
    fun changeLike(post: Post) : Boolean {
        post.liked = !(post.liked)
        this.viewBinding.tvLikeCounter.text = post.countLikes().toString() + " Likes"
        return post.liked
    }
    /** getProfilePic() - allow the user to get a drawable according to its prof pic index */
    fun getProfilePic(index : Int): Int {
        if(index == 1){
            return R.drawable.profpic1
        }
        else if(index == 2){
            return  R.drawable.profpic2
        }
        else if(index == 3){
            return  R.drawable.profpic3
        }
        else if(index == 4){
            return  R.drawable.profpic4
        }
        else{
            return R.drawable.profpic1
        }
    }

    /** Check if a user is present in the post's like list or not */
    fun userInLikeList(post: Post, userID: String): Boolean {

        return post.likes.contains(userID)
    }


}