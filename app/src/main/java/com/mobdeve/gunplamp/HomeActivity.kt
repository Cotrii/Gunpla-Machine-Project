package com.mobdeve.gunplamp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.gunplamp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        this.recyclerView = viewBinding.recyclerView
        this.myAdapter = MyAdapter(DataHelper.initializeData())
        this.recyclerView.adapter = myAdapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)



    }
}