package com.mobdeve.gunplamp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.gunplamp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter

    companion object {
        private val data : ArrayList<Post> = DataHelper.initializeData()
    }

    private val createPostLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result: ActivityResult ->

        if (result.resultCode == RESULT_OK) {

        }
    }

    private val galleryViewLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->

        if (result.resultCode == RESULT_OK) {
            val imageURI = result?.data?.data
            val intent = Intent(this, CreatePostActivity::class.java)
            intent.putExtra("imagePost", imageURI.toString())
            createPostLauncher.launch(intent)
        }
    }

    //Edit (?)
    private val viewPostDetailsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->

        if (result.resultCode == RESULT_OK) {

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

        val username: String = intent.getStringExtra("username").toString()
        val firstName: String =intent.getStringExtra("firstName").toString()
        val lastName: String = intent.getStringExtra("lastName").toString()
        val profilePic: Int? = intent.getIntExtra("profilePic",0)
        Toast.makeText(this, "FROM HOME:" + username, Toast.LENGTH_SHORT).show()

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

        viewBinding.imageButton.setOnClickListener{
            val intent = Intent(this@HomeActivity, UserProfileActivity::class.java)
            this.userProfileLauncher.launch(intent)
        }


        //RecyclerView setup; Note how MyAdapter has viewPostResultLauncher
        this.recyclerView = viewBinding.myRecyclerView
        this.myAdapter = MyAdapter(data, viewPostDetailsLauncher)
        viewBinding.myRecyclerView.adapter = myAdapter
        viewBinding.myRecyclerView.layoutManager = LinearLayoutManager(this)


    }
}