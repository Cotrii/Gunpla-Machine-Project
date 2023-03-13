package com.mobdeve.gunplamp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobdeve.gunplamp.databinding.ActivityViewPostDetailsBinding

class ViewPostDetails : AppCompatActivity() {

    companion object {
        const val IMAGE_KEY = "IMAGE_KEY"
        const val CAPTION_KEY = "CAPTION_KEY"
        const val POSITION_KEY = "POSITION_KEY"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityViewPostDetailsBinding = ActivityViewPostDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.ivImage.setImageResource(intent.getIntExtra(ViewPostDetails.IMAGE_KEY, 0))

        viewBinding.etEditCaption.setText(intent.getStringExtra(ViewPostDetails.CAPTION_KEY))



    }
}