package com.mobdeve.gunplamp

import android.net.Uri
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Post(id: String,poster: User, imagePost: String?, caption: String?, store: Store, date: String, isLiked: Boolean, likes: ArrayList<String>) {
    val id: String = id
    val username: String = poster.username
    val profilePic: Int = poster.profilePic
    val userID: String = poster.id

    val imagePost: String? = imagePost
    var caption: String? = caption
    val datePosted: String = date
    val store: Store = store

    var liked: Boolean = isLiked
    var likes: ArrayList<String> = likes
    var commentsID: String = ""

    fun changeCaption(newCap: String) {
        caption = newCap
    }

    fun countLikes(): Int? {

        if(likes != null){
            return likes?.size
        } else{
            return 0
        }
    }
}