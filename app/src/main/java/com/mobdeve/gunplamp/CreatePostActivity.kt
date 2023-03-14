package com.mobdeve.gunplamp

import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.PermissionRequest
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.fondesa.kpermissions.extension.checkPermissionsStatus
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.mobdeve.gunplamp.databinding.ActivityCreatePostBinding
import java.net.URI

class CreatePostActivity : AppCompatActivity() {

    lateinit var imagePostURI : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        //
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityCreatePostBinding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        permissionsBuilder(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA).build().send();
        val permStatus = checkPermissionsStatus(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA);

        for (res in permStatus){
            if(res.toString().contains("Granted")){
                val imageString = intent.getStringExtra("imagePost");
                imagePostURI = Uri.parse(imageString);
                Toast.makeText(this, "status is:" + res, Toast.LENGTH_SHORT).show()
//                viewBinding.ivImagePost.setImageBitmap(selectedImage)
                viewBinding.ivImagePost.setImageURI(imagePostURI)
            }
            else{
                finish()
            }
        }
    }



}