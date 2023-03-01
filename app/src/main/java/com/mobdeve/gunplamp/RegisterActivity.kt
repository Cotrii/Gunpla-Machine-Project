package com.mobdeve.gunplamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mobdeve.gunplamp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.buttonRegister.setOnClickListener {
            if (viewBinding.editTextFirstName.text.toString()
                    .isNotEmpty() && viewBinding.editTextLastName.text.toString().isNotEmpty()
                && viewBinding.editTextUsername.toString()
                    .isNotEmpty() && viewBinding.editTextPassword.text.toString().isNotEmpty()
            ) {

                if (viewBinding.editTextPassword.text.toString() == viewBinding.editTextConfirmPassword.text.toString()
                ) {
                    Toast.makeText(this, "Successfully Registered!", Toast.LENGTH_SHORT).show()
                    val returnIntent = Intent()
                    intent.putExtra("firstName", viewBinding.editTextFirstName.text.toString())
                    intent.putExtra("lastName", viewBinding.editTextLastName.text.toString())
                    intent.putExtra("username", viewBinding.editTextUsername.text.toString())
                    intent.putExtra("password", viewBinding.editTextPassword.text.toString())
                    setResult(RESULT_OK,intent)
                    finish()
                } else {
                    Toast.makeText(this, "Mismatched Password", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Missing Fields", Toast.LENGTH_SHORT).show()
            }
        }


    }
}