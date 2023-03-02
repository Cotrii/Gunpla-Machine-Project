package com.mobdeve.gunplamp

import android.content.Intent
import android.provider.ContactsContract
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView.Adapter


//Remove Intent parameter if we don't need to include comments
class MyAdapter(private val data: ArrayList<Post>, private val myActivityResultLauncher:
ActivityResultLauncher<Intent>) : Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


}