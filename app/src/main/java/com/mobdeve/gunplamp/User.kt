package com.mobdeve.gunplamp

/**
 * A User contains an id,username, fullname, email (All Strings) and a profilePic of int which
 * is a drawable currently.
 */
class User(
    id: String,
    username: String,
    fullName: String,
    email: String,
    profilePic: Int,

   ) {
    var id: String = id
    var username: String = username
    var fullName: String = fullName
    var email: String = email
    var profilePic: Int = profilePic
}