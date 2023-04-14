package com.mobdeve.gunplamp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.gunplamp.databinding.ActivityUserProfileBinding

/**
 * UserProfileActivity - In this activity a user may view its current profile pic, username
 * and full name. It will also be allowed to edit content in it
 */
class UserProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private lateinit var user : User
    private lateinit var viewBinding: ActivityUserProfileBinding
    private var editPassword : Boolean = false

    private val homeActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result: ActivityResult ->

        if (result.resultCode == RESULT_OK) {
            //TODO
        }
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var editFullName: Boolean = false
        var editUsername : Boolean = false
        var editProfilePic: Boolean = false

        viewBinding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        auth = FirebaseAuth.getInstance()

        viewBinding.saveButton.visibility = View.INVISIBLE

        val originalFullName = viewBinding.fullName.text.toString()
        val originalUserName = viewBinding.username.text.toString()

        viewBinding.oldPassword.visibility = View.GONE
        viewBinding.newPassword.visibility = View.GONE
        viewBinding.confirmNewPassword.visibility = View.GONE
        viewBinding.textView8.visibility = View.GONE
        viewBinding.textView9.visibility = View.GONE
        viewBinding.textView10.visibility = View.GONE
        viewBinding.svProfilePics.visibility = View.GONE

        fun showSavedButton(){
            if(originalFullName != viewBinding.fullName.text.toString() ||  originalUserName != viewBinding.username.text.toString()){
                viewBinding.saveButton.visibility = View.VISIBLE
            }
            else{
                viewBinding.saveButton.visibility = View.INVISIBLE
            }
        }

        // Sets profile pic based on image selected
        viewBinding.ivProfilePic.setOnClickListener {
            editProfilePic = !editProfilePic
            if (editProfilePic) {
                viewBinding.svProfilePics.visibility = View.VISIBLE
                viewBinding.saveButton.visibility = View.VISIBLE
                viewBinding.ivProfPic1.setOnClickListener {
                    user.profilePic = 1
                    viewBinding.ivProfilePic.setImageResource(R.drawable.profpic1)
                }
                viewBinding.ivProfPic2.setOnClickListener {
                    user.profilePic = 2
                    viewBinding.ivProfilePic.setImageResource(R.drawable.profpic2)
                }
                viewBinding.ivProfPic3.setOnClickListener {
                    user.profilePic = 3
                    viewBinding.ivProfilePic.setImageResource(R.drawable.profpic3)
                }
                viewBinding.ivProfPic4.setOnClickListener {
                    user.profilePic = 4
                    viewBinding.ivProfilePic.setImageResource(R.drawable.profpic4)
                }
            } else {
                viewBinding.svProfilePics.visibility = View.GONE
            }
        }

        // Logs out user from auth
        viewBinding.buttonLogout.setOnClickListener {
            val logoutIntent = Intent(applicationContext, MainActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            auth.signOut()
            startActivity(logoutIntent)
        }

        // checks if user wants to change password and shows appropriate fields for it
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


        //goes back to previous activity
        viewBinding.backButton.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }

        //Listeners for editing fields
        viewBinding.editFullNameButton.setOnClickListener{
            editFullName =! editFullName
            if (editFullName){
                findViewById<EditText>(R.id.fullName).isEnabled = true
                viewBinding.editFullNameButton.setImageResource(R.drawable.baseline_cancel_24)
            }
            else{
                findViewById<EditText>(R.id.fullName).isEnabled = false
                viewBinding.editFullNameButton.setImageResource(R.drawable.baseline_edit_24)
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

        // text changed listeners to enable saved button if fields are changed
        viewBinding.fullName.addTextChangedListener(object: TextWatcher {
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

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {document ->
                if(document != null) {
                    user = User(auth.currentUser!!.uid, document!!.getString("username").toString(),document!!.getString("fullName").toString(),document!!.getString("email").toString(),
                        Integer.parseInt(document!!.getLong("profilePic").toString())
                    )
//                    Toast.makeText(this, "profile pic:" + user.profilePic, Toast.LENGTH_SHORT).show()
                    viewBinding.fullName.setText(user.fullName)
                    viewBinding.email.setText(user.email)
                    viewBinding.username.setText(user.username)
                    viewBinding.ivProfilePic.setImageResource(getProfilePic(user.profilePic))
                }
            }
        }

        // Saves changes to db
        viewBinding.saveButton.setOnClickListener {
            val intent = Intent()

            // check if password and confirm password is valid as well as if user wants password to be edited
            if(editPassword && (viewBinding.oldPassword.text.length <8 ||viewBinding.newPassword.text.length < 8 || viewBinding.newPassword.text.toString() != viewBinding.confirmNewPassword.text.toString())){
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()
            }
            // fields are left empty
            else if(viewBinding.fullName.text.length == 0 || viewBinding.username.text.length == 0){
                Toast.makeText(this, "Invalid Fields", Toast.LENGTH_SHORT).show()
            }
            else{
                db.collection("users").whereEqualTo("username", viewBinding.username.text.toString()).get().addOnSuccessListener {result ->
                    if(result.size() == 0) {
                        // updates values of the user in DB
                        db.collection("users").document(currentUser!!.uid).update("fullName", viewBinding.fullName.text.toString(),
                            "username", viewBinding.username.text.toString(),"profilePic", user.profilePic).addOnSuccessListener { document ->
                            // if editPassword is true then...
                            if(document != null) {
                                if (editPassword) {
                                    // get credential and reauthenticate user
                                    val credential = EmailAuthProvider.getCredential(
                                        currentUser?.email ?: "",
                                        viewBinding.oldPassword.text.toString()
                                    )
                                    currentUser.reauthenticate(credential)?.addOnCompleteListener { task ->
                                        // update password to the new password
                                        if (task.isSuccessful) {
                                            currentUser.updatePassword(viewBinding.confirmNewPassword.text.toString())
                                                ?.addOnCompleteListener { task ->
                                                    if (task.isSuccessful) {
                                                        if (document != null) {
                                                            setResult(RESULT_OK, intent)
                                                            finish()
                                                        }
                                                    } else {
                                                        setResult(RESULT_CANCELED, intent)
                                                        finish()
                                                    }
                                                }
                                        } else {
                                            setResult(RESULT_CANCELED, intent)
                                            finish()
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else{
                        Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show()
                    }

                }
            }


        }
    }


    // gets profile pic corresponding to integer saved in the DB for each user
    fun getProfilePic(index : Int): Int {
        if(index == 1){
            return R.drawable.profpic1
        }
        else if(index == 2){
            return  R.drawable.profpic2
        }
        else if(index == 3){
            return  R.drawable.profpic3
        }
        else if(index == 4){
            return  R.drawable.profpic4
        }
        else{
            return R.drawable.profpic1
        }
    }



}