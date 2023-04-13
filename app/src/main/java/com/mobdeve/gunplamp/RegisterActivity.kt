package com.mobdeve.gunplamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.gunplamp.databinding.ActivityRegisterBinding

/**
 * RegisterActivity allows the user to input its full name, username, and password
 */
class RegisterActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private val db = Firebase.firestore

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
                    db.collection("users").whereEqualTo("username", viewBinding.editTextUsername.text.toString()).get().addOnSuccessListener {result ->
                        if(result.size() == 0){
                            auth.createUserWithEmailAndPassword(viewBinding.editTextEmail.text.trim().toString(), viewBinding.editTextPassword.text.toString())
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {
                                        // Sign in success, update UI with the signed-in user's information
                                        val user = hashMapOf(
                                            "fullName" to viewBinding.editTextFullName.text.toString(),
                                            "email" to viewBinding.editTextEmail.text.trim().toString(),
                                            "username" to  viewBinding.editTextUsername.text.toString(),
                                            "profilePic" to 1
                                        )

                                        db.collection("users").document(auth.currentUser!!.uid).set(user).addOnSuccessListener {
                                            val returnIntent = Intent()
                                            intent.putExtra("email", viewBinding.editTextEmail.text.trim().toString())
                                            intent.putExtra("username", viewBinding.editTextUsername.text.toString())
                                            setResult(RESULT_OK,intent)
                                            finish()
                                        }

                                        Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(baseContext, "Authentication failed." + task,
                                            Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }
                        else{
                            Toast.makeText(this, "Username Already Taken", Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    Toast.makeText(this, "Mismatched Password", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Missing Fields", Toast.LENGTH_SHORT).show()
            }
        }

    }

    /** onStart contains the code to check whether if a user is signed in or not */
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
//            Toast.makeText(this, "current user is:" + currentUser, Toast.LENGTH_SHORT).show()
            auth.signOut()
        }
    }
}