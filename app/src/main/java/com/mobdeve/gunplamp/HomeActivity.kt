package com.mobdeve.gunplamp

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.gunplamp.databinding.ActivityHomeBinding
import java.lang.Integer.parseInt
import kotlin.properties.Delegates


class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var username : String
    private lateinit var firstName : String
    private lateinit var lastName : String
    private var profilePic by Delegates.notNull<Int>()
    private lateinit var user : User
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    companion object {
        private val data : ArrayList<Post> = DataHelper.initializeData()
    }

    private val createPostLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result: ActivityResult ->
        
        if (result.resultCode == RESULT_OK) {
//            Toast.makeText(this, "SUCCESS:" + result.data?.getStringExtra("caption"), Toast.LENGTH_SHORT).show()
            val imagePost = result.data?.getStringExtra("imagePost")
            val caption = result.data?.getStringExtra("caption")
            val datePost = result.data?.getStringExtra("datePosted")
            val storeName = result.data?.getStringExtra("storeName")
            val storeCity = result.data?.getStringExtra("storeCity")

            data.add(Post(user, imagePost, caption, Store(storeName,storeCity),datePost.toString(), false))
            this.myAdapter.notifyDataSetChanged()
        }
    }

    private val galleryViewLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            Toast.makeText(this, "gallery view:" + RESULT_OK, Toast.LENGTH_SHORT).show()
            val imageURI = result?.data?.data
            val intent = Intent(this, CreatePostActivity::class.java)
            intent.putExtra("imagePost", imageURI.toString())
            intent.putExtra("username", username)
            createPostLauncher.launch(intent)
        }
    }

    //Edit (?)
    private val viewPostDetailsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->

        if (result.resultCode == RESULT_OK) {
            val position = result.data!!.getIntExtra(ViewPostDetails.POSITION_KEY, 0)
            val newCaption = result.data!!.getStringExtra(ViewPostDetails.CAPTION_KEY).toString()

            val status = result.data!!.getStringExtra(ViewPostDetails.STATUS_KEY).toString()
            if (status == "Edit")
            {
                data[position].changeCaption(newCaption)
                myAdapter.notifyItemChanged(position)
            }
            else //DELETE
            {
                data.remove(data[position])
                myAdapter.notifyDataSetChanged()
            }
        }
    }

    private val userProfileLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result : ActivityResult ->
        if (result.resultCode == RESULT_OK){
//            val username = result.data!!.getStringExtra("username")
//            val fullName = result.data!!.getStringExtra("fullName")
//            val email = result.data!!.getStringExtra("email")
//            val password = result.data!!.getStringExtra("password")
//            val profilePic = result.data!!.getStringExtra("profilePic")
//
//            if (username != null) {
//                user.username = username
//            }
//
//            if(fullName != null){
//                user.fullName = fullName
//            }
//
//            if(email != null){
//                user.email = email
//            }
//
//            if(password != null){
//                user.password = password
//            }

//            if(profilePic != null){
//                user.profilePic
//            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialize ViewBinding for HomeActivity
        val viewBinding : ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        auth = FirebaseAuth.getInstance()

        val createPostButton = viewBinding.fabCreatePost

        createPostButton.setOnClickListener {
//            val intent = Intent(applicationContext, CreatePostActivity::class.java)
////            intent.putExtra("username", username)
////            intent.putExtra("firstName", firstName)
////            intent.putExtra("lastName", lastName)
////            intent.putExtra("profilePic", profilePic)
//            createPostLauncher.launch(intent)

            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            gallery.type = "image/*";
            galleryViewLauncher.launch(gallery)
        }
//
//        viewBinding.imageButton.setImageDrawable(profilePic.toDrawable())

        viewBinding.imageButton.setOnClickListener{
            val intent = Intent(this@HomeActivity, UserProfileActivity::class.java)
//            intent.putExtra("username", user.username)
//            intent.putExtra("firstName", user.firstName)
//            intent.putExtra("lastName", user.lastName)
//            intent.putExtra("profilePic", user.profilePic)
//            intent.putExtra("password", user.password)
            this.userProfileLauncher.launch(intent)
        }


        //RecyclerView setup; Note how MyAdapter has viewPostResultLauncher
        this.recyclerView = viewBinding.myRecyclerView
        this.myAdapter = MyAdapter(data, viewPostDetailsLauncher)
        viewBinding.myRecyclerView.adapter = myAdapter
        viewBinding.myRecyclerView.layoutManager = LinearLayoutManager(this)

        //Remove flickering of item
        val animator = recyclerView.itemAnimator
        if (animator is SimpleItemAnimator) {
            (animator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {document ->
                if(document != null) {
                    user = User(document!!.getString("username").toString(),document!!.getString("fullName").toString(),document!!.getString("email").toString(), parseInt(document!!.getLong("profilePic").toString()) )
                }
            }
        }
    }
}