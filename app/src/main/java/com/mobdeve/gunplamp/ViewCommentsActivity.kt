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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.gunplamp.databinding.ActivityViewCommentsBinding
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * ViewCommentsActivity is used to view and determine the amount of comments in a post
 * A user will also be allowed to add its own comment
 */
class ViewCommentsActivity : AppCompatActivity() {

    // This key is used to get the POST ID and get comments that are part of it
    companion object {
        const val POST_ID_KEY: String = "POST_ID_KEY"
    }

    private var db = Firebase.firestore
    private var commentsList: MutableList<Comment> = ArrayList<Comment>()
    private lateinit var auth: FirebaseAuth
    var datePosted : Date = Date()
    private lateinit var user : User
    private lateinit var recyclerView: RecyclerView
    private lateinit var myCommentsAdapter: MyCommentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityViewCommentsBinding = ActivityViewCommentsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        this.recyclerView = viewBinding.commentsRecyclerView

        this.myCommentsAdapter = MyCommentsAdapter(commentsList as ArrayList<Comment>)
        viewBinding.commentsRecyclerView.adapter = myCommentsAdapter

        this.recyclerView.layoutManager = LinearLayoutManager(this)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if(currentUser != null){
            db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {document ->
                if(document != null) {
                    user = User(auth.currentUser!!.uid,document!!.getString("username").toString(),document!!.getString("fullName").toString(),document!!.getString("email").toString(), parseInt(document!!.getLong("profilePic").toString()) )
                }
            }
        }

        val postid = intent.getStringExtra(ViewCommentsActivity.POST_ID_KEY).toString()

        // If input is not empty, then add the comment to the list
        viewBinding.btnSubmitCmmt.setOnClickListener(View.OnClickListener {
            viewBinding.btnSubmitCmmt.isEnabled = false
            if (viewBinding.etEntry.text.toString() != "") {
                // Add new comment to database
                val comments = db.collection("comments")

                // Get all data input to a hashmap then add it to the database and recyclerview
                val data1 = hashMapOf(
                    "userID" to user.id,
                    "content" to viewBinding.etEntry.text.toString(),
                    "postID" to postid,
                    "commentDate" to datePosted
                )

                comments.add(data1)

                commentsList.add(Comment(user.username, viewBinding.etEntry.text.toString(), postid, datePosted.toString()))

                this.myCommentsAdapter.notifyDataSetChanged()
            }
            viewBinding.btnSubmitCmmt.isEnabled = true
            viewBinding.etEntry.setText("")
        })

        // Get all the comments according to its postID
        db.collection("comments")
//            .orderBy("datePosted", Query.Direction.ASCENDING)
            .whereEqualTo("postID", postid)
            .get()
            .addOnSuccessListener { result ->

            for (document in result) {
                var index = 0

                db.collection("users").document(document.getString("userID").toString()).get().addOnSuccessListener {user ->
                    if(user != null) {
                        val username = user!!.getString("username").toString()

                        val comment =  Comment(
                            username,
                            document.getString("content").toString(),
                            document.getString("postID").toString(),
                            document.getDate("commentDate").toString())
                        commentsList.add(comment)
                        this.myCommentsAdapter.notifyItemInserted(index)
                        index++


                        // Sort the comments by date it was created
                        val sortedData = commentsList.sortedBy {
                            it.commentDate
                        }

                        myCommentsAdapter.setData(sortedData)
                    }
                }
            }
        }

    }


}