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
import java.util.*
import kotlin.collections.ArrayList

/**
 * ViewCommentsActivity is used to determine
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

//    private val addCommentLauncher = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result: ActivityResult ->
//
//        if (result.resultCode == RESULT_OK) {
//        }
//    }


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


        viewBinding.btnSubmitCmmt.setOnClickListener(View.OnClickListener {

            if (viewBinding.etEntry.text.toString() != "") {
                // Add new comment to database
                val comments = db.collection("comments")

                // Get all data input to a hashmap then add it to the database and recyclerview
                val data1 = hashMapOf(
                    "username" to user.username,
                    "content" to viewBinding.etEntry.text.toString(),
                    "postID" to postid,
                    "datePosted" to datePosted
                )

                comments.add(data1)

                commentsList.add(Comment(user.username, viewBinding.etEntry.text.toString(), postid))

                this.myCommentsAdapter.notifyDataSetChanged()
            }
        })


        db.collection("comments")
//            .orderBy("datePosted", Query.Direction.ASCENDING)
            .whereEqualTo("postID", postid)
            .get()
            .addOnSuccessListener { result ->

            for (document in result) {
                var index = 0

                val comment =  Comment(  document.getString("username").toString(),
                    document.getString("content").toString(),
                    document.getString("postID").toString())

                commentsList.add(comment)
                this.myCommentsAdapter.notifyItemInserted(index)
                index++
            }
        }
    }


}