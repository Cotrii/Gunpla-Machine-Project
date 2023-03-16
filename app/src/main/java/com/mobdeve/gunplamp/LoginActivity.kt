package com.mobdeve.gunplamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.mobdeve.gunplamp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

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

        viewBinding.tvSignUp.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            registerResultLauncher.launch(intent)
        }

        viewBinding.btnSavelogin.setOnClickListener {

            val userList : ArrayList<User> = DataGenerator.generateData()

//            userList.add( User("nugundam237", "pass1", "Amuro", "Ray", R.drawable.emblem))

            for (user in userList) {

                if (user.username == viewBinding.etUsernameInput.text.toString()
                    && user.password == viewBinding.etUsernameInput2.text.toString()) {
                        val intent : Intent = Intent(this@LoginActivity, HomeActivity:: class.java)
                        openHomeLauncher.launch(intent)
                }
            }

        }
    }
}