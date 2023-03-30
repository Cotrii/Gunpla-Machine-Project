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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.gunplamp.databinding.ActivityViewPostDetailsBinding
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class ViewPostDetails : AppCompatActivity() {

    companion object {
        const val USERNAME_KEY: String = "USERNAME_KEY"
        const val IMAGE_KEY: String = "IMAGE_KEY"
        const val CAPTION_KEY: String = "CAPTION_KEY"
        const val POSITION_KEY: String = "POSITION_KEY"
        const val STATUS_KEY: String = "STATUS_KEY"

        const val POST_ID_KEY: String = "POST_ID_KEY"
    }

    private lateinit var captionStr: String
    private lateinit var viewBinding: ActivityViewPostDetailsBinding  // Holds the views of the ActivityViewNoteBinding

    //Declaration of firestore
    val db = Firebase.firestore


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

                Picasso.get().load(imageString).into(this.viewBinding.ivImage)
//
//                val imagePostURI = Uri.parse(imageString);
//                this.viewBinding.ivImage.setImageURI(imagePostURI)
            }
        }

        captionStr = intent.getStringExtra(ViewPostDetails.CAPTION_KEY).toString()
        val position = intent.getIntExtra(ViewPostDetails.POSITION_KEY, 0)


        //Display id of Post
        val id = intent.getStringExtra(ViewPostDetails.POST_ID_KEY).toString()


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

            //Update the database
            //val id = intent.getStringExtra(ViewPostDetails.POST_ID_KEY).toString()
            val docRef = db.collection("posts").document(id)

            val updates = hashMapOf(
                "caption" to viewBinding.etEditCaption.text.toString()
            )

            docRef.update(updates as Map<String, String>)
                .addOnSuccessListener {
                    Log.d("success", "DocumentSnapshot successfully updated!")
                }
                .addOnFailureListener { e ->
                    Log.w("failed", "Error updating document", e)
                }
            // *************

            val changedIntent : Intent = Intent()

            changedIntent.putExtra(ViewPostDetails.CAPTION_KEY, viewBinding.etEditCaption.text.toString())
            changedIntent.putExtra(ViewPostDetails.POSITION_KEY, position)

            changedIntent.putExtra(ViewPostDetails.STATUS_KEY, "Edit")

            setResult(RESULT_OK, changedIntent)
            finish()

        })

        viewBinding.btnDelete.setOnClickListener(View.OnClickListener {


            //Delete a document from the database
            //val id = intent.getStringExtra(ViewPostDetails.POST_ID_KEY).toString()
            val docRef = db.collection("posts").document(id)
            docRef.delete()


            val deleteIntent : Intent = Intent()

//            deleteIntent.putExtra(ViewPostDetails.CAPTION_KEY, viewBinding.etEditCaption.text.toString())
//            deleteIntent.putExtra(ViewPostDetails.POSITION_KEY, position)
//
//            deleteIntent.putExtra(ViewPostDetails.STATUS_KEY, "Delete")
            setResult(RESULT_OK, deleteIntent)
            finish()

        })
    }

    private fun isTextStillOriginal() : Boolean {
        return ( this.viewBinding.etEditCaption.text.toString() == captionStr )
    }
}