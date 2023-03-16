package com.mobdeve.gunplamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        var editPassword : Boolean = false

        val viewBinding : ActivityUserProfileBinding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.saveButton.visibility = View.INVISIBLE

        val originalFirstName = viewBinding.firstName.text.toString()
        val originalLastName = viewBinding.lastName.text.toString()
        val originalUserName = viewBinding.username.text.toString()

        viewBinding.oldPassword.visibility = View.GONE
        viewBinding.newPassword.visibility = View.GONE
        viewBinding.confirmNewPassword.visibility = View.GONE
        viewBinding.textView8.visibility = View.GONE
        viewBinding.textView9.visibility = View.GONE
        viewBinding.textView10.visibility = View.GONE

        fun showSavedButton(){
            if(originalLastName != viewBinding.lastName.text.toString() || originalFirstName != viewBinding.firstName.text.toString() || originalUserName != viewBinding.username.text.toString()){
                viewBinding.saveButton.visibility = View.VISIBLE
            }
            else{
                viewBinding.saveButton.visibility = View.INVISIBLE
            }
        }

        viewBinding.buttonChangePass.setOnClickListener {
            editPassword = !editPassword
            if (editPassword) {
                viewBinding.textView8.visibility = View.VISIBLE
                viewBinding.textView9.visibility = View.VISIBLE
                viewBinding.textView10.visibility = View.VISIBLE
                viewBinding.oldPassword.visibility = View.VISIBLE
                viewBinding.newPassword.visibility = View.VISIBLE
                viewBinding.confirmNewPassword.visibility = View.VISIBLE
                viewBinding.buttonChangePass.text = "CANCEL"
            } else {
                viewBinding.buttonChangePass.text = "CHANGE PASSWORD"
                viewBinding.oldPassword.setText("")
                viewBinding.newPassword.setText("")
                viewBinding.confirmNewPassword.setText("")
                viewBinding.textView8.visibility = View.GONE
                viewBinding.textView9.visibility = View.GONE
                viewBinding.textView10.visibility = View.GONE
                viewBinding.oldPassword.visibility = View.GONE
                viewBinding.newPassword.visibility = View.GONE
                viewBinding.confirmNewPassword.visibility = View.GONE
            }
        }

        viewBinding.saveButton.setOnClickListener {
            val intent = Intent()
            if(originalUserName != viewBinding.username.text.toString())
            intent.putExtra("username", viewBinding.username.text.toString())

            if(originalFirstName != viewBinding.firstName.text.toString())
            intent.putExtra("firstName", viewBinding.firstName.text.toString())

            if(originalLastName != viewBinding.lastName.text.toString())
            intent.putExtra("lastName", viewBinding.firstName.text.toString())

            if(viewBinding.confirmNewPassword.text.toString() == viewBinding.newPassword.text.toString() && viewBinding.newPassword.text.toString().length > 0)
            intent.putExtra("password", viewBinding.newPassword.text.toString())

            setResult(RESULT_OK, intent)
            finish()
        }

        viewBinding.backButton.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }

        viewBinding.editFirstNameButton.setOnClickListener{
            edit =! edit
            if (edit){
                findViewById<EditText>(R.id.firstName).isEnabled = true
                viewBinding.editFirstNameButton.setImageResource(R.drawable.baseline_cancel_24)
            }
            else{
                findViewById<EditText>(R.id.firstName).isEnabled = false
                viewBinding.editFirstNameButton.setImageResource(R.drawable.baseline_edit_24)

            }
        }

        viewBinding.editLastNameButton.setOnClickListener{
            edit =! edit
            if (edit){
                findViewById<EditText>(R.id.lastName).isEnabled = true
                viewBinding.editLastNameButton.setImageResource(R.drawable.baseline_cancel_24)
            }
            else{
                findViewById<EditText>(R.id.lastName).isEnabled = false
                viewBinding.editLastNameButton.setImageResource(R.drawable.baseline_edit_24)

            }
        }

        viewBinding.editUsernameButton.setOnClickListener{
            editUsername =! editUsername
            if (editUsername){
                findViewById<EditText>(R.id.username).isEnabled = true
                viewBinding.editUsernameButton.setImageResource(R.drawable.baseline_cancel_24)
            }
            else{
                findViewById<EditText>(R.id.username).isEnabled = false
                viewBinding.editUsernameButton.setImageResource(R.drawable.baseline_edit_24)

            }
        }

        viewBinding.firstName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                showSavedButton()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        viewBinding.lastName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                showSavedButton()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        viewBinding.username.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                showSavedButton()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })





    }



}