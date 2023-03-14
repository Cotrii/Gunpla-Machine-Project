package com.mobdeve.gunplamp

import android.app.AlertDialog
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
import androidx.core.view.isVisible
import com.fondesa.kpermissions.extension.checkPermissionsStatus
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.mobdeve.gunplamp.databinding.ActivityCreatePostBinding
import java.net.URI
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
    val stores = arrayOf(Store("Store1", "ABC CITY FIRST"), Store("Store2", "DEF CITY SECOND"), Store("STORE3", "GHI CITY THIRD"))

    override fun onCreate(savedInstanceState: Bundle?) {
        //
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityCreatePostBinding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        permissionsBuilder(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA).build().send();
        val permStatus = checkPermissionsStatus(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA);
        viewBinding.tvStoreAddress.visibility = View.GONE
        viewBinding.tvStoreName.visibility = View.GONE
        viewBinding.etStoreAddress.visibility = View.GONE
        viewBinding.etStoreName.visibility = View.GONE

        for (store in stores) {
            storeNames.add(store.name)
        }
        storeNames.add("Add New Store")
        val adapter : ArrayAdapter<String> =  ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, storeNames)
        viewBinding.spinnerStore.adapter = adapter

        for (res in permStatus){
            if(res.toString().contains("Granted")){
                imageString = intent.getStringExtra("imagePost").toString();
                imagePostURI = Uri.parse(imageString);
//                viewBinding.ivImagePost.setImageBitmap(selectedImage)
                viewBinding.ivImagePost.setImageURI(imagePostURI)
            }
            else{
                finish()
            }
        }

        viewBinding.spinnerStore?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("TAGGGGGGGGGGG", "onItemSelected: NOTHINGGGGGGGGGGGG" )
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

        viewBinding.btnCancel.setOnClickListener {
            finish()
        }

        viewBinding.btnPost.setOnClickListener {
            if (viewBinding.etCaption.text.length > 0) {
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



}}