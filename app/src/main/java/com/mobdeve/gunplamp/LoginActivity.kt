package com.mobdeve.gunplamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.gunplamp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    companion object {
        const val INPUT_USER_KEY = "INPUT_USER_KEY"
//        private lateinit var USERLIST_KEY: ArrayList<User> =
    }


    private val registerResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result: ActivityResult ->

        if (result.resultCode == RESULT_OK) {
            //TODO
            Toast.makeText(this, "RESULT IS:" + result.data?.getStringExtra("firstName") + " " + result.data?.getStringExtra("password") + " " +result.data?.getStringExtra("username"), Toast.LENGTH_SHORT).show()
        }
    }

    private val openHomeLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result: ActivityResult ->

//        if (result.resultCode == RESULT_OK) {
//            //TODO
//            Toast.makeText(this, "RESULT IS:" + result.data?.getStringExtra("firstName") + " " + result.data?.getStringExtra("password") + " " +result.data?.getStringExtra("username"), Toast.LENGTH_SHORT).show()
//        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        auth = FirebaseAuth.getInstance()

        viewBinding.tvSignUp.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            registerResultLauncher.launch(intent)
        }

        viewBinding.btnSavelogin.setOnClickListener {
                if(viewBinding.etPasswordInput.text.isNotEmpty() && viewBinding.etEmailInput.text.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(viewBinding.etEmailInput.text.trim().toString(), viewBinding.etPasswordInput.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {document ->
                                    if(document != null) {
                                        finish()
                                        val intent = Intent(applicationContext, HomeActivity::class.java)
                                        registerResultLauncher.launch(intent)
                                    }
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }

        }
    }