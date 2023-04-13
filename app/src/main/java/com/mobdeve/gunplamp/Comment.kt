package com.mobdeve.gunplamp

/**
 * The class "Comment" contains a username, content, postID (all in String format)
 */
class Comment(username: String, content: String, postID: String, commentDate: String) {
    val username: String = username
    val content: String = content
    val postID: String = postID
    val commentDate: String = commentDate
}

//class Comment(user: User, content: String, ) {
//    val username: String = user.username
//    val content: String = content
//}