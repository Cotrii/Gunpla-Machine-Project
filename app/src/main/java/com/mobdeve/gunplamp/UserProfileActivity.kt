package com.mobdeve.gunplamp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.gunplamp.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityUserProfileBinding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}