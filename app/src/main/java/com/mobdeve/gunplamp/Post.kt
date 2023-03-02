package com.mobdeve.gunplamp

class Post(
    val imageId: Int,
    val datePosted: String,
    val caption: String?,
    val location: String?,
    var liked: Boolean,
    val username: String,
    val userImageId: Int,

    val likeCounter: ArrayList<String> //Count the number of users who liked the post
    ) {
}