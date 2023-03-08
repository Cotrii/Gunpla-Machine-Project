package com.mobdeve.gunplamp

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Post(poster: User, imagePost: Int, caption: String?, store: Store, date: String, isLiked: Boolean) {
    val username: String = poster.username
    val profilePic: Int = poster.profilePic

    val imagePost: Int = imagePost
    val caption: String? = caption
    val datePosted: String = date
    val store: Store = store

    var liked: Boolean = isLiked
    var likes: ArrayList<User>? = null
    var comments: ArrayList<Comment>? = null

}