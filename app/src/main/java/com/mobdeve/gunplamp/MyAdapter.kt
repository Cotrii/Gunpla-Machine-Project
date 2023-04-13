package com.mobdeve.gunplamp

import android.content.Intent
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.gunplamp.databinding.ItemLayoutBinding


/**
 * MyAdapter holds most of the RecyclerView setup logic for Post data
 */
class MyAdapter(private val data: ArrayList<Post>, private val myActivityResultLauncher: ActivityResultLauncher<Intent>, private val userID : String) : Adapter<MyViewHolder>() {

    private val db = Firebase.firestore
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemViewBinding: ItemLayoutBinding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val myViewHolder = MyViewHolder(itemViewBinding)

        return myViewHolder
    }
    /** Binds data and also updates the document containing the likes for an item */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(this.data[position], userID)

        holder.setLikeOnClickListener(View.OnClickListener {
            if(holder.changeLike(this.data[position])){
                this.data[position].likes?.add(userID)

            }else{
                this.data[position].likes?.remove(userID)
            }
//            Log.d("ASDJKLASJDKLAJSDKLJSAK", "onBindViewHolder: " + this.data[position].likes)
            notifyItemChanged(position)

//            Log.d("moshi", this.data[position].likes.toString())
//            Log.d("moshi", this.data[position].id.toString())

            val docRef = db.collection("posts").document(data[position].id)

            docRef.update("likes", this.data[position].likes)
        })
        // If a userID matches a post's userID then store its details on an intent
        if(userID == data[position].userID){
            holder.setEditOnClickListener(View.OnClickListener {
                val intent : Intent = Intent(holder.itemView.context, ViewPostDetails::class.java)

                intent.putExtra(ViewPostDetails.CAPTION_KEY, data[position].caption)
                intent.putExtra(ViewPostDetails.IMAGE_KEY, data[position].imagePost)
                intent.putExtra(ViewPostDetails.POSITION_KEY, position)
                intent.putExtra(ViewPostDetails.USERNAME_KEY, data[position].username)

                intent.putExtra(ViewPostDetails.POST_ID_KEY, data[position].id)
                this.myActivityResultLauncher.launch(intent)
            })
        }

        // This sets a view for viewing comments for a certain activity
        holder.setViewCommentsOnClickListener(View.OnClickListener {
           val intent : Intent = Intent(holder.itemView.context, ViewCommentsActivity::class.java)

            intent.putExtra(ViewCommentsActivity.POST_ID_KEY, data[position].id)

//            intent.putExtra()
            this.myActivityResultLauncher.launch(intent)
        })

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newPostList: List<Post>) {
        // Clear the existing data.
        data.clear()
        // Add the new data to the list.
        data.addAll(newPostList)
        // Notify the adapter that the data has changed.
        notifyDataSetChanged()
    }


}