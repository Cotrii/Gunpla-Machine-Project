package com.mobdeve.gunplamp

import android.content.Intent
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.mobdeve.gunplamp.databinding.ItemLayoutBinding


//Remove Intent parameter if we don't need to include comments
class MyAdapter(private val data: ArrayList<Post>, private val myActivityResultLauncher:
ActivityResultLauncher<Intent>) : Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")

        val itemViewBinding: ItemLayoutBinding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MyViewHolder(itemViewBinding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")

        holder.bindData(this.data[position])

        holder.setLikeOnClickListener(View.OnClickListener {

            holder.changeLike(this.data[position])

            notifyItemChanged(position)
        })
    }


}