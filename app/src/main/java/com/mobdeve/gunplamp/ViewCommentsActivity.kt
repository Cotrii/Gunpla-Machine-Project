package com.mobdeve.gunplamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.gunplamp.databinding.ActivityViewCommentsBinding

class ViewCommentsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private val addCommentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->

        if (result.resultCode == RESULT_OK) {
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityViewCommentsBinding = ActivityViewCommentsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        this.recyclerView = findViewById(R.id.commentsRecyclerView)

        val commentsList = ArrayList<Comment>()

        commentsList.add(Comment(  User("nugundam237", "pass1", "Amuro", "Ray", R.drawable.emblem)
            , "Thanks"))
        commentsList.add(Comment(  User("thehawk", "pass2", "Borat", "Sagdiyev", R.drawable.borat)
            , "It's just by my place. TYYYY"))
        commentsList.add(Comment(  User("thehawk", "pass2", "Borat", "Sagdiyev", R.drawable.borat)
            , "Any zakus perhaps?"))


        this.recyclerView.adapter = MyCommentsAdapter(commentsList, addCommentLauncher)

        this.recyclerView.layoutManager = LinearLayoutManager(this)



    }
}