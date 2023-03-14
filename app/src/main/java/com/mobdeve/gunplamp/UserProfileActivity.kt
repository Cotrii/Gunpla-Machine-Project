package com.mobdeve.gunplamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.mobdeve.gunplamp.databinding.ActivityMainBinding
import com.mobdeve.gunplamp.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {

    private val homeActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result: ActivityResult ->

        if (result.resultCode == RESULT_OK) {
            //TODO
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityUserProfileBinding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.backButton.setOnClickListener{
            val intent = Intent(this@UserProfileActivity, HomeActivity::class.java)
            homeActivityLauncher.launch(intent)
        }

        viewBinding.EditButton.setOnClickListener{
            findViewById<EditText>(R.id.username).isEnabled = true
            findViewById<EditText>(R.id.name).isEnabled = true
            findViewById<TextView>(R.id.EditButton).text = "Save"
            findViewById<TextView>(R.id.backButton).text = "Cancel"
        }


    }
}