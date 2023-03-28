package com.mobdeve.gunplamp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.webkit.PermissionRequest
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.fondesa.kpermissions.extension.checkPermissionsStatus
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mobdeve.gunplamp.databinding.ActivityCreatePostBinding
import java.net.URI
import java.security.AccessController.getContext
import java.util.*
import kotlin.collections.ArrayList

class CreatePostActivity : AppCompatActivity() {

    lateinit var imagePostURI : Uri
    lateinit var caption : String
    lateinit var store : Store
    var datePosted : Date = Date()
    lateinit var tempName : String
    lateinit var tempCity : String
    lateinit var imageString : String
    val storeNames : MutableList<String?> = ArrayList<String?>()
    var stores : MutableList<Store> = ArrayList<Store>()
    lateinit var user : User

    lateinit var viewBinding : ActivityCreatePostBinding

    var storage = Firebase.storage
    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        //
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        permissionsBuilder(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA).build().send {
            result ->
                if((result[0].toString().contains("Granted") || result[0].toString().contains("Permanently"))  && ( result[1].toString().contains("Granted") || result[1].toString().contains("Permanently"))){
                    imageString = intent.getStringExtra("imagePost").toString();
                    imagePostURI = Uri.parse(imageString);
                    viewBinding.ivImagePost.setImageURI(imagePostURI)
                }
                else{
                    finish()
                }
        };

        db.collection("stores").get().addOnSuccessListener { documents ->
            if(documents != null){
                for (document in documents) {
                    stores.add(Store(document.id,document.getString("name").toString(), document.getString("city").toString()))
                }
                for (store in stores) {
                    storeNames.add(store.name)
                }
                storeNames.add("Add New Store")
                initializeSpinner()
            }
        }

        viewBinding.tvStoreAddress.visibility = View.GONE
        viewBinding.tvStoreName.visibility = View.GONE
        viewBinding.etStoreAddress.visibility = View.GONE
        viewBinding.etStoreName.visibility = View.GONE
}

    override fun onStart() {
        super.onStart()

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if(currentUser != null){
            db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {document ->
                if(document != null) {
                    user = User(auth.currentUser!!.uid,document!!.getString("username").toString(),document!!.getString("fullName").toString(),document!!.getString("email").toString(),
                        Integer.parseInt(document!!.getLong("profilePic").toString())
                    )
                }
            }
        }



        viewBinding.btnCancel.setOnClickListener {
            finish()
        }

        viewBinding.btnPost.setOnClickListener {
            if (viewBinding.etCaption.text.isNotEmpty() && tempName.isNotEmpty() && tempCity.isNotEmpty()) {
                if(viewBinding.spinnerStore.selectedItem.toString().isNotEmpty()){
                    if(viewBinding.spinnerStore.selectedItem.toString() == "Add New Store" && viewBinding.etStoreAddress.text.toString()
                            .isNotEmpty() && viewBinding.etStoreName.text.toString().isNotEmpty()){

                        val storeData = hashMapOf(
                            "city" to viewBinding.etStoreAddress.text.toString(),
                            "name" to viewBinding.etStoreName.text.toString()
                        )
                        db.collection("stores").add(storeData).addOnSuccessListener { document ->
                            createPost(document.id)
                        }
                    }
                    else{
                        createPost(stores[viewBinding.spinnerStore.selectedItemId.toInt()].id.toString())
                    }
                }
            }
        }
    }

    private fun createPost(storeID: String){
        val storageRef = storage.reference
        val fileName = datePosted.toString() + getFileName(applicationContext, imagePostURI!!)
        val uploadTask = storageRef.child("imagePosts/$fileName").putFile(imagePostURI)
        uploadTask.addOnSuccessListener {
            storageRef.child("imagePosts/$fileName").downloadUrl.addOnSuccessListener { result ->


                val postData = hashMapOf(
                    "caption" to viewBinding.etCaption.text.toString(),
                    "datePosted" to datePosted,
                    "imagePost" to result.toString(),
                    "storeID" to storeID,
                    "userID" to user.id,
                    "likes" to ArrayList<String>()
                )
                db.collection("posts").add(postData).addOnSuccessListener {
                    val returnIntent = Intent()
                    setResult(RESULT_OK,returnIntent)
                    finish()
                }
            }
        }
    }

    fun createStore(){

    }

    private fun initializeSpinner(){
        val adapter : ArrayAdapter<String> =  ArrayAdapter(this, android.R.layout.simple_spinner_item, storeNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewBinding.spinnerStore.adapter = adapter

        viewBinding.spinnerStore.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(viewBinding.spinnerStore.selectedItem.toString() == "Add New Store") {
                    viewBinding.tvStoreAddress.visibility = View.VISIBLE
                    viewBinding.tvStoreName.visibility = View.VISIBLE
                    viewBinding.etStoreAddress.visibility = View.VISIBLE
                    viewBinding.etStoreName.visibility = View.VISIBLE

                    viewBinding.etStoreName.setOnClickListener {
                        tempName = viewBinding.etStoreName.text.toString()
                    }

                    viewBinding.etStoreAddress.setOnClickListener {
                        tempCity = viewBinding.etStoreAddress.text.toString()
                    }
                }
                else{
                    viewBinding.tvStoreAddress.visibility = View.GONE
                    viewBinding.tvStoreName.visibility = View.GONE
                    viewBinding.etStoreAddress.visibility = View.GONE
                    viewBinding.etStoreName.visibility = View.GONE
                    for (i in stores.indices)
                    {
                        if (stores[i].name == viewBinding.spinnerStore.selectedItem.toString()) {
                            store = stores[i]
                            tempName = stores[i].name.toString()
                            tempCity = stores[i].city.toString()
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("Range")
    private fun getFileName(context: Context, uri: Uri): String? {
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (cursor != null) {
                    if(cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }
            }
        }
        return uri.path?.lastIndexOf('/')?.let { uri.path?.substring(it) }
    }

}