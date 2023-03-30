package com.mobdeve.gunplamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.mobdeve.gunplamp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    companion object{
        private val data = ArrayList<User>()
    }

    private val registerResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result: ActivityResult ->

        if (result.resultCode == RESULT_OK) {
            //TODO
//            val username: String = result.data?.getStringExtra("username").toString()
//            val password: String = result.data?.getStringExtra("password").toString()
//            val fullName: String = result.data?.getStringExtra("fullName").toString()
//            val email: String = result.data?.getStringExtra("email").toString()
//            val profilePic: Int = 0
//            data.add(User(username,password,fullName,email,profilePic))
            val intent = Intent(applicationContext, HomeActivity::class.java)
//            intent.putExtra("username", username)
//            intent.putExtra("fullName", fullName)
//            intent.putExtra("email", email)
//            intent.putExtra("profilePic", profilePic)
            startActivity(intent)
        }
    }

    private val loginResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result: ActivityResult ->

        if (result.resultCode == RESULT_OK) {
            //TODO
        }
    }


    private val homeActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result: ActivityResult ->

        if (result.resultCode == RESULT_OK) {
            //TODO
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        auth = FirebaseAuth.getInstance()

        viewBinding.btnLogin.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            loginResultLauncher.launch(intent)
        })

        viewBinding.btnRegister.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            registerResultLauncher.launch(intent)
        })

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            homeActivityLauncher.launch(intent)
        }
    }
}