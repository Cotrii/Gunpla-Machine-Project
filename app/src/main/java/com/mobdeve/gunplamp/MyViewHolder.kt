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
import kotlin.reflect.typeOf

class MyViewHolder(private val viewBinding: ItemLayoutBinding): RecyclerView.ViewHolder(viewBinding.root) {

    fun bindData(post: Post, userID: String) {
        this.viewBinding.ivUserIcon.setImageResource(getProfilePic(post.profilePic))
        this.viewBinding.tvUsername.text = post.username
        this.viewBinding.tvLocation.text = post.store.name

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

        //How will we track every user's activity with likes (Solution: add or check if user
        // is in list/array of strings?

        post.liked = userInLikeList(post, userID)
        //
        this.viewBinding.ibFavorite.setImageResource(isLikedDrawable(post.liked))

        this.viewBinding.tvBotUsername.text = post.username
        this.viewBinding.tvDesc.text = post.caption
        this.viewBinding.tvLikeCounter.text = post.countLikes().toString() + " Likes"

        this.viewBinding.tvDate.text = post.datePosted
    }

    fun setLikeOnClickListener(onClickListener: View.OnClickListener) {
        this.viewBinding.ibFavorite.setOnClickListener(onClickListener)
    }

    fun setEditOnClickListener(onClickListener: View.OnClickListener) {
        this.viewBinding.ibDetailsBtn.setOnClickListener(onClickListener)
    }

    fun setViewCommentsOnClickListener(onClickListener: View.OnClickListener) {
        this.viewBinding.tvComments.setOnClickListener(onClickListener)
    }

    fun isLikedDrawable(like: Boolean): Int {

        if (like) {
            return R.drawable.favorite_24
        }

        return R.drawable.not_favorite_24
    }

    fun changeLike(post: Post) : Boolean {
        post.liked = !(post.liked)
        this.viewBinding.tvLikeCounter.text = post.countLikes().toString() + " Likes"
        return post.liked
    }

    fun getProfilePic(index : Int): Int {
        if(index == 1){
            return R.drawable.person1
        }
        else if(index == 2){
            return  R.drawable.person2
        }
        else if(index == 3){
            return  R.drawable.person3
        }
        else if(index == 4){
            return  R.drawable.person4
        }
        else{
            return R.drawable.person1
        }
    }


    fun userInLikeList(post: Post, userID: String): Boolean {

        return post.likes.contains(userID)
    }


}