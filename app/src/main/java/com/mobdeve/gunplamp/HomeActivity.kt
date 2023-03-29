package com.mobdeve.gunplamp

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import android.util.Log
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
import java.text.SimpleDateFormat
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
    private var posts : MutableList<Post> = ArrayList<Post>()

    private val createPostLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result: ActivityResult ->
        
        if (result.resultCode == RESULT_OK) {
            this.myAdapter.notifyDataSetChanged()
        }
    }

    private val galleryViewLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val imageURI = result?.data?.data
            val intent = Intent(this, CreatePostActivity::class.java)
            intent.putExtra("imagePost", imageURI.toString())
//            intent.putExtra("username", username)
            createPostLauncher.launch(intent)

        }
    }

    //Edit (?)
    private val viewPostDetailsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->

        if (result.resultCode == RESULT_OK) {
//            val position = result.data!!.getIntExtra(ViewPostDetails.POSITION_KEY, 0)
//            val newCaption = result.data!!.getStringExtra(ViewPostDetails.CAPTION_KEY).toString()
//
//            val status = result.data!!.getStringExtra(ViewPostDetails.STATUS_KEY).toString()
//            if (status == "Edit")
//            {
//                data[position].changeCaption(newCaption)
//                myAdapter.notifyItemChanged(position)
//            }
//            else //DELETE
//            {
//                data.remove(data[position])
//                myAdapter.notifyDataSetChanged()
//            }
        }
    }

    private val userProfileLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result : ActivityResult ->
        if (result.resultCode == RESULT_OK){

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialize ViewBinding for HomeActivity
        val viewBinding : ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        auth = FirebaseAuth.getInstance()

        //RecyclerView setup; Note how MyAdapter has viewPostResultLauncher
        this.recyclerView = viewBinding.myRecyclerView
        this.myAdapter = MyAdapter(posts as ArrayList<Post>, viewPostDetailsLauncher, auth.currentUser!!.uid)
        viewBinding.myRecyclerView.adapter = myAdapter
        viewBinding.myRecyclerView.layoutManager = LinearLayoutManager(this)

        val createPostButton = viewBinding.fabCreatePost

        createPostButton.setOnClickListener {
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




        //Remove flickering of item
        val animator = recyclerView.itemAnimator
        if (animator is SimpleItemAnimator) {
            (animator as SimpleItemAnimator).supportsChangeAnimations = false
        }

//        db.collection("posts").get().addOnSuccessListener { documents ->
//            if(documents != null){
//                var index=0
//                for (document in documents) {
//                    Log.d("SDAJKDLJWIOJLOOOOOOOOOOOOOOOOK", "onCreate: document is:" + document.getString("storeID").toString())
//                    db.collection("users").document(document.getString("userID").toString()).get().addOnSuccessListener {user ->
//                        db.collection("stores").document(document.getString("storeID").toString()).get().addOnSuccessListener { store ->
//                            val poster = User(user.id,user.getString("username").toString(),user.getString("fullName").toString(),user.getString("email").toString(),user.getLong("profilePic")!!.toInt())
//                            val store = Store(store.id, store.getString("name"), store.getString("city"))
//                            val datePosted = SimpleDateFormat("MMM d, yyyy").format(document.getDate("datePosted"))
//                            posts.add(Post(document.id,poster,document.getString("imagePost"),document.getString("caption"),store,datePosted, false))
//                            this.myAdapter.notifyItemInserted(index)
//                            index++
//                        }
//                    }
//                }
//
//            }
//
//        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {document ->
                if(document != null) {
                    user = User(auth.currentUser!!.uid,document!!.getString("username").toString(),document!!.getString("fullName").toString(),document!!.getString("email").toString(), parseInt(document!!.getLong("profilePic").toString()) )
                }
            }
        }


        //Remove all posts, if posts is empty, then notify the adapter
        if (posts.isEmpty() != true) {
            posts.clear()
            this.myAdapter.notifyDataSetChanged()
        }

        db.collection("posts").get().addOnSuccessListener { documents ->
            if(documents != null){
                var index=0
                for (document in documents) {
                    Log.d("SDAJKDLJWIOJLOOOOOOOOOOOOOOOOK", "onCreate: document is:" + document.getString("storeID").toString())
                    db.collection("users").document(document.getString("userID").toString()).get().addOnSuccessListener {user ->
                        db.collection("stores").document(document.getString("storeID").toString()).get().addOnSuccessListener { store ->
                            val poster = User(user.id,user.getString("username").toString(),user.getString("fullName").toString(),user.getString("email").toString(),user.getLong("profilePic")!!.toInt())
                            val store = Store(store.id, store.getString("name"), store.getString("city"))
                            val datePosted = SimpleDateFormat("MMM d, yyyy").format(document.getDate("datePosted"))
                            posts.add(Post(document.id,poster,document.getString("imagePost"),document.getString("caption"),store,datePosted, false))
                            this.myAdapter.notifyItemInserted(index)
                            index++
                        }
                    }
                }

            }

        }
    }

    //Last resort dont touch
//    override fun onResume() {
//        super.onResume()
//
//        this.recreate()
//    }

}