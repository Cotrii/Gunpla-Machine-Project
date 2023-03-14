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

//class MyAdapter(private val data: ArrayList<Post>, private val myActivityResultLauncher:
////ActivityResultLauncher<Intent>) : Adapter<MyViewHolder>()
class MyAdapter(private val data: ArrayList<Post>, private val myActivityResultLauncher: ActivityResultLauncher<Intent>) : Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemViewBinding: ItemLayoutBinding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
//
        val myViewHolder = MyViewHolder(itemViewBinding)

//        myViewHolder.itemView.setOnClickListener {
//
//            val intent : Intent = Intent(myViewHolder.itemView.context, ViewPostDetails::class.java)
//
//            this.myActivityResultLauncher.launch(intent)
//        }

//        return MyViewHolder(itemViewBinding)

        return myViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(this.data[position])

        holder.setLikeOnClickListener(View.OnClickListener {

            holder.changeLike(this.data[position])

            notifyItemChanged(position)

        })

        holder.setEditOnClickListener(View.OnClickListener {
            val intent : Intent = Intent(holder.itemView.context, ViewPostDetails::class.java)

            intent.putExtra(ViewPostDetails.CAPTION_KEY, data[position].caption)
            intent.putExtra(ViewPostDetails.IMAGE_KEY, data[position].imagePost)
            intent.putExtra(ViewPostDetails.POSITION_KEY, position)
            intent.putExtra(ViewPostDetails.USERNAME_KEY, data[position].username)

            this.myActivityResultLauncher.launch(intent)

        })
    }

    override fun getItemCount(): Int {
        return data.size
    }


}