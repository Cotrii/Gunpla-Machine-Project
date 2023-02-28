package com.mobdeve.gunplamp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobdeve.gunplamp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    companion object {
        const val INPUT_USER_KEY = "INPUT_USER_KEY"
//        const val
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}