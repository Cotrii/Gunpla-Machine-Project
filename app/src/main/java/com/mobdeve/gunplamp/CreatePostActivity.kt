package com.mobdeve.gunplamp

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mobdeve.gunplamp.databinding.ActivityCreatePostBinding
import java.net.URI

class CreatePostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityCreatePostBinding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val imagePostString = intent.getStringExtra("imagePost");
        val imagePostURI = Uri.parse(imagePostString)


        Toast.makeText(this, "IMAGE POST IS:" + imagePostString, Toast.LENGTH_SHORT).show()
        viewBinding.ivImagePost.setImageURI(imagePostURI)


    }
}