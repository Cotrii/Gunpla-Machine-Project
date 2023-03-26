package com.mobdeve.gunplamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobdeve.gunplamp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        //
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        auth = FirebaseAuth.getInstance()

        viewBinding.buttonRegister.setOnClickListener {
            if (viewBinding.editTextFullName.text.toString()
                    .isNotEmpty() && viewBinding.editTextEmail.text.toString().isNotEmpty()
                && viewBinding.editTextUsername.toString()
                    .isNotEmpty() && viewBinding.editTextEmail.text.toString().isNotEmpty()
            ) {

                if (viewBinding.editTextPassword.text.toString() == viewBinding.editTextConfirmPassword.text.toString()
                ) {

                    auth.createUserWithEmailAndPassword(viewBinding.editTextEmail.text.toString(), viewBinding.editTextPassword.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                val user = auth.currentUser
                                Toast.makeText(this, "user is:" + user, Toast.LENGTH_SHORT).show()
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(baseContext, "Authentication failed." + task,
                                    Toast.LENGTH_SHORT).show()
                            }
                        }

//                    Toast.makeText(this, "Successfully Registered!", Toast.LENGTH_SHORT).show()
//                    val returnIntent = Intent()
//                    intent.putExtra("fullName", viewBinding.editTextFullName.text.toString())
//                    intent.putExtra("email", viewBinding.editTextEmail.text.toString())
//                    intent.putExtra("username", viewBinding.editTextUsername.text.toString())
//                    intent.putExtra("password", viewBinding.editTextPassword.text.toString())
//                    setResult(RESULT_OK,intent)
//                    finish()
                } else {
                    Toast.makeText(this, "Mismatched Password", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Missing Fields", Toast.LENGTH_SHORT).show()
            }
        }

    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            Toast.makeText(this, "current user is:" + currentUser, Toast.LENGTH_SHORT).show()
        }
    }
}