package com.mobdeve.gunplamp

import android.app.AlertDialog
import android.app.Application
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    lateinit var datePosted : Date
    lateinit var tempName : String
    lateinit var tempCity : String
    lateinit var imageString : String
    val storeNames : MutableList<String?> = ArrayList<String?>()
    var stores : MutableList<Store> = ArrayList<Store>()

    lateinit var viewBinding : ActivityCreatePostBinding

    var storage = Firebase.storage
    private val db = Firebase.firestore

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
                    stores.add(Store(document.getString("name") , document.getString("city")))
                }
                for (store in stores) {
                    storeNames.add(store.name)
                }
                storeNames.add("Add New Store")
            }
        }

        viewBinding.tvStoreAddress.visibility = View.GONE
        viewBinding.tvStoreName.visibility = View.GONE
        viewBinding.etStoreAddress.visibility = View.GONE
        viewBinding.etStoreName.visibility = View.GONE
}

    override fun onStart() {
        super.onStart()

        val adapter : ArrayAdapter<String> =  ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, storeNames)
        viewBinding.spinnerStore.adapter = adapter

        viewBinding.spinnerStore.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("ASDJKLJSLKAJDOIWAIWODL:KLAJWI:DJSKLA:DJIWADK", "onItemSelected: " + viewBinding.spinnerStore.selectedItem.toString())
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

        viewBinding.btnCancel.setOnClickListener {
            finish()
        }

        viewBinding.btnPost.setOnClickListener {
            if (viewBinding.etCaption.text.isNotEmpty() && tempName.isNotEmpty() && tempCity.isNotEmpty()) {
                datePosted = Date()
                caption = viewBinding.etCaption.text.toString()
                intent.putExtra("imagePost", imageString)
                intent.putExtra("datePosted", datePosted)
                intent.putExtra("caption", caption)
                intent.putExtra("storeName", tempName)
                intent.putExtra("storeCity", tempCity)
                setResult(RESULT_OK,intent)
                finish()
            }
        }
    }

}