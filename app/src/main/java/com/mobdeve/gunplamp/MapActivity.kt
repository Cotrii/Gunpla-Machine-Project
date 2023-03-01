package com.mobdeve.gunplamp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobdeve.gunplamp.databinding.ActivityRegisterBinding

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}