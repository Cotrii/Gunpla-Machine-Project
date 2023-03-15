package com.mobdeve.gunplamp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.mobdeve.gunplamp.databinding.ActivityViewPostDetailsBinding
import org.w3c.dom.Text

class ViewPostDetails : AppCompatActivity() {

    companion object {
        const val USERNAME_KEY: String = "USERNAME_KEY"
        const val IMAGE_KEY: String = "IMAGE_KEY"
        const val CAPTION_KEY: String = "CAPTION_KEY"
        const val POSITION_KEY: String = "POSITION_KEY"
    }

    private lateinit var captionStr: String
    private lateinit var viewBinding: ActivityViewPostDetailsBinding  // Holds the views of the ActivityViewNoteBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.viewBinding = ActivityViewPostDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Set Image View (not editable)
        val imageString = intent.getStringExtra(ViewPostDetails.IMAGE_KEY)
        if(imageString != null){
            if(imageString.toIntOrNull() != null ){
                this.viewBinding.ivImage.setImageResource(imageString.toInt())
            }
            else{
                val imagePostURI = Uri.parse(imageString);
                this.viewBinding.ivImage.setImageURI(imagePostURI)
            }
        }

        captionStr = intent.getStringExtra(ViewPostDetails.CAPTION_KEY).toString()
        val position = intent.getIntExtra(ViewPostDetails.POSITION_KEY, 0)


        viewBinding.etEditCaption.setText(captionStr)

        viewBinding.btnSave.isEnabled = false;

        viewBinding.etEditCaption.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewBinding.btnSave.isEnabled = !isTextStillOriginal()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        viewBinding.btnSave.setOnClickListener(View.OnClickListener {

            val changedIntent : Intent = Intent()

            changedIntent.putExtra(ViewPostDetails.CAPTION_KEY, viewBinding.etEditCaption.text.toString())
            changedIntent.putExtra(ViewPostDetails.POSITION_KEY, position)
            setResult(RESULT_OK, changedIntent)
            finish()

        })



    }

    private fun isTextStillOriginal() : Boolean {
        return ( this.viewBinding.etEditCaption.text.toString() == captionStr )
    }
}