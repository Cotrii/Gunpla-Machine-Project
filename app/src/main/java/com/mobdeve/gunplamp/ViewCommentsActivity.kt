package com.mobdeve.gunplamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.gunplamp.databinding.ActivityViewCommentsBinding

class ViewCommentsActivity : AppCompatActivity() {

    companion object {
        const val POST_ID_KEY: String = "POST_ID_KEY"
    }

    private var db = Firebase.firestore
    private var commentsList: MutableList<Comment> = ArrayList<Comment>()


    private lateinit var recyclerView: RecyclerView
    private lateinit var myCommentsAdapter: MyCommentsAdapter

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

        this.recyclerView = viewBinding.commentsRecyclerView


//        commentsList.add(Comment(  User("123","nugundam237", "pass1", "Amuro",  R.drawable.emblem)
//            , "Thanks"))
//        commentsList.add(Comment(  User("123","thehawk", "pass2", "Borat",  R.drawable.borat)
//            , "It's just by my place. TYYYY"))
//        commentsList.add(Comment(  User("123","thehawk", "pass2", "Borat",  R.drawable.borat)
//            , "Any zakus perhaps?"))


        this.myCommentsAdapter = MyCommentsAdapter(commentsList as ArrayList<Comment>, addCommentLauncher)
        viewBinding.commentsRecyclerView.adapter = myCommentsAdapter

        this.recyclerView.layoutManager = LinearLayoutManager(this)


        viewBinding.btnSubmitCmmt.setOnClickListener(View.OnClickListener {

            if (viewBinding.etEntry.text.toString() != "") {
//                commentsList.add(Comment(  User("123","thehawk", "pass2", "Borat",  R.drawable.borat)
//                    , viewBinding.etEntry.text.toString()))

                this.myCommentsAdapter.notifyDataSetChanged()
            }
        })


        db.collection("comments").get().addOnSuccessListener { result ->

            for (document in result) {
//                val persons = documents.map { doc ->
//                    val name = doc.getString("name") ?: ""
//                    val age = doc.getLong("age")?.toInt() ?: 0
//                    Person(name, age)
//                }
                val comment =  Comment(  document.getString("username").toString(),
                    document.getString("comment").toString(),
                    document.getString("postID").toString())

//                val comment = document.toObject(Comment::class.java)
//                Log.d("hai", comment.toString())
                commentsList.add(comment)
            }
        }
    }


}