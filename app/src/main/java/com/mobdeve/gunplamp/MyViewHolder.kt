package com.mobdeve.gunplamp

import android.net.Uri
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.gunplamp.databinding.ItemLayoutBinding
import kotlin.reflect.typeOf

class MyViewHolder(private val viewBinding: ItemLayoutBinding): RecyclerView.ViewHolder(viewBinding.root) {

    fun bindData(post: Post) {
        this.viewBinding.ivUserIcon.setImageResource(post.profilePic)
        this.viewBinding.tvUsername.text = post.username


        this.viewBinding.tvLocation.text = post.store.name

        if(post.imagePost != null){
            if(post.imagePost.toIntOrNull() != null ){
                Log.d("DJSAKLDJSALKDJSA", "bindData: " + post.imagePost)
                this.viewBinding.ivImagePosted.setImageResource((post.imagePost.toInt()))
            }
            else{
                val imagePostURI = Uri.parse(post.imagePost);
                this.viewBinding.ivImagePosted.setImageURI(imagePostURI)
            }
        }

        //How will we track every user's activity with likes (Solution: add or check if user
        // is in list/array of strings?

        //
        this.viewBinding.ibFavorite.setImageResource(isLikedDrawable(post.liked))

        this.viewBinding.tvBotUsername.text = post.username
        this.viewBinding.tvDesc.text = post.caption

        this.viewBinding.tvDate.text = post.datePosted
    }

    fun setLikeOnClickListener(onClickListener: View.OnClickListener) {
        this.viewBinding.ibFavorite.setOnClickListener(onClickListener)
    }

    fun setEditOnClickListener(onClickListener: View.OnClickListener) {
        this.viewBinding.ibDetailsBtn.setOnClickListener(onClickListener)
    }

    fun isLikedDrawable(like: Boolean): Int {

        if (like) {
            return R.drawable.favorite_24
        }

        return R.drawable.not_favorite_24
    }

    fun changeLike(post: Post) {
        post.liked = !(post.liked)
    }


    //Suggestion
//    fun userInLikeList(post: Post, user: User): Boolean {
//
//        return (post.likeCounter).contains(user.username)
//    }


}