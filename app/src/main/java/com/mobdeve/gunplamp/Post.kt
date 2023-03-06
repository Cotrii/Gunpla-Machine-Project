package com.mobdeve.gunplamp

class Post(
    val imageId: Int,
    val datePosted: String,
    val caption: String?,
    val location: String?,
    var liked: Boolean,
    val username: String,
    val userImageId: Int,

    //Count the number of users who liked the post
//    val likeCounter: ArrayList<String>,
//    //
//    val comments: ArrayList<String>
    ) {
}