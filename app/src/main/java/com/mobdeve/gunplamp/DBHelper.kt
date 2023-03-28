package com.mobdeve.gunplamp

import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class DBHelper {
    companion object{

        var storage = Firebase.storage
        private var auth = FirebaseAuth.getInstance()
        private val db = Firebase.firestore


        fun login(email : String, password: String) : Boolean? {
            var returnVal : Boolean? = null
            auth.signInWithEmailAndPassword(email.trim(), password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {document ->
                            if(document != null) {
                                returnVal = true
                            }
                        }
                    } else {
                        returnVal = false
                    }
                }
            return returnVal
        }
    }

}