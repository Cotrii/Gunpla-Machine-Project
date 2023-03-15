package com.mobdeve.gunplamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        var edit: Boolean = false
        var editUsername : Boolean = false

        val viewBinding : ActivityUserProfileBinding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.saveButton.visibility = View.INVISIBLE

        viewBinding.backButton.setOnClickListener{
            val intent = Intent(this@UserProfileActivity, HomeActivity::class.java)
            homeActivityLauncher.launch(intent)
        }

        viewBinding.editNameButton.setOnClickListener{
            edit =! edit
            if (edit){
                findViewById<EditText>(R.id.name).isEnabled = true
                viewBinding.saveButton.visibility = View.VISIBLE
                viewBinding.editNameButton.setImageResource(R.drawable.baseline_cancel_24)
            }
            else{
                findViewById<EditText>(R.id.name).isEnabled = false
                viewBinding.saveButton.visibility = View.INVISIBLE
                viewBinding.editNameButton.setImageResource(R.drawable.baseline_edit_24)

            }
        }

        viewBinding.editUsernameButton.setOnClickListener{
            editUsername =! editUsername
            if (editUsername){
                findViewById<EditText>(R.id.username).isEnabled = true
                viewBinding.saveButton.visibility = View.VISIBLE
                viewBinding.editUsernameButton.setImageResource(R.drawable.baseline_cancel_24)
            }
            else{
                findViewById<EditText>(R.id.username).isEnabled = false
                viewBinding.saveButton.visibility = View.INVISIBLE
                viewBinding.editUsernameButton.setImageResource(R.drawable.baseline_edit_24)

            }
        }



    }
}