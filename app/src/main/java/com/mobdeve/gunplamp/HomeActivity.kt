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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

//        viewBinding.myRecyclerView.adapter = MyAdapter(DataHelper.initializeData())
        viewBinding.myRecyclerView.adapter = MyAdapter(DataHelper.initializeData(), viewPostDetailsLauncher)

        viewBinding.myRecyclerView.layoutManager = LinearLayoutManager(this)

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


    }
}