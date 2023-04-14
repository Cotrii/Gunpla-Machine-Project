package com.mobdeve.gunplamp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.gunplamp.databinding.ActivityHomeBinding
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates


/***
 *  Home Activity contains most of the functions to be used by a user.
 *  It also contains the data retrieved from the query that are considered as Posts.
 */
class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var user : User
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private var posts : MutableList<Post> = ArrayList<Post>()
    private var oldPosts: MutableList<Post> = ArrayList<Post>()

    // Launcher for Create Post
    private val createPostLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result: ActivityResult ->
        
        if (result.resultCode == RESULT_OK) {
            this.myAdapter.notifyDataSetChanged()
        }
    }
    //Launcher for Gallery View
    private val galleryViewLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val imageURI = result?.data?.data
            val intent = Intent(this, CreatePostActivity::class.java)
            intent.putExtra("imagePost", imageURI.toString())
//            intent.putExtra("username", username)
            createPostLauncher.launch(intent)

        }
    }

    //The viewPostDetailsLauncher is used for EditActivity
    private val viewPostDetailsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
    }

    //userProfileLauncher is used for UserProfileAct
    private val userProfileLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result : ActivityResult ->
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialize ViewBinding for HomeActivity
        val viewBinding : ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        auth = FirebaseAuth.getInstance()

        //RecyclerView setup; Note how MyAdapter has viewPostDetailsLauncher
        this.recyclerView = viewBinding.myRecyclerView
        this.myAdapter = MyAdapter(posts as ArrayList<Post>, viewPostDetailsLauncher, auth.currentUser!!.uid)
        viewBinding.myRecyclerView.adapter = myAdapter
        viewBinding.myRecyclerView.layoutManager = LinearLayoutManager(this)

        //If a user branches off from the filter group, then make radiogroup invisible
        viewBinding.myRecyclerView.setOnTouchListener { _, _ ->
            viewBinding.rgFilter.isVisible = false
            false
        }

        viewBinding.btnFilter.setOnClickListener(View.OnClickListener {
                viewBinding.rgFilter.isVisible = true
        })

        viewBinding.btnClear.setOnClickListener(View.OnClickListener {
            viewBinding.rgFilter.clearCheck()
        })

        //This function does the filter if a user clicks the search/enter function
        fun doFilter() {
            viewBinding.rgFilter.isVisible = false
            val selectedId = viewBinding.rgFilter.checkedRadioButtonId

            // Get reference to InputMethodManager
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            // Hide keyboard
            imm.hideSoftInputFromWindow(viewBinding.root.windowToken, 0)

            if (selectedId != -1) {

                val radioButton = viewBinding.root.findViewById<RadioButton>(selectedId)
                val selectedText = radioButton.text.toString()

                if (selectedText == "Store") {  //Store

                    var filteredList = posts.filter { post ->
                        post.store!!.name!!.lowercase().contains(viewBinding.etSearchInput.text.toString().lowercase())
                    }
                    //If current posts is empty, then try to set the data again
                    if (filteredList.isEmpty()) {
                        myAdapter.setData(oldPosts)
                        filteredList = posts.filter {  post ->
                            post.store!!.name!!.lowercase().contains(viewBinding.etSearchInput.text.toString().lowercase())
                        }
                    }

                    updateFilter(filteredList as ArrayList<Post>)

                } else if (selectedText == "User") {  //User
                    var filteredList = posts.filter { post ->
                        post.username!!.lowercase().contains(viewBinding.etSearchInput.text.toString().lowercase())
                    }

                    if (filteredList.isEmpty()) {
                        myAdapter.setData(oldPosts)
                        filteredList = posts.filter { post ->
                            post.username!!.lowercase().contains(viewBinding.etSearchInput.text.toString().lowercase())
                        }
                    }

                    updateFilter(filteredList as ArrayList<Post>)

                } else if (selectedText == "Caption"){ //Caption
                    var filteredList = posts.filter { post ->
                        post.caption!!.lowercase().contains(viewBinding.etSearchInput.text.toString().lowercase())
                    }

                    if (filteredList.isEmpty()) {
                        myAdapter.setData(oldPosts)
                        filteredList = posts.filter { post ->
                            post.caption!!.lowercase().contains(viewBinding.etSearchInput.text.toString().lowercase())
                        }
                    }

                    updateFilter(filteredList as ArrayList<Post>)
                } else { //City

                    var filteredList = posts.filter { post ->
                        post.store!!.city!!.lowercase().contains(viewBinding.etSearchInput.text.toString().lowercase())
                    }

                    if (filteredList.isEmpty()) {
                        myAdapter.setData(oldPosts)
                        filteredList = posts.filter { post ->
                            post.store!!.city!!.lowercase().contains(viewBinding.etSearchInput.text.toString().lowercase())
                        }
                    }

                    updateFilter(filteredList as ArrayList<Post>)
                }
            }
            else{ // If the user did not indicate any fields, search for anything

                if (viewBinding.etSearchInput.text.toString() != "") {
                    myAdapter.setData(oldPosts)
                    var filList1 = posts.filter { post ->
                        post.store!!.name!!.lowercase().contains(viewBinding.etSearchInput.text.toString().lowercase())
                    }

                    var filList2 = posts.filter { post ->
                        post.username!!.lowercase().contains(viewBinding.etSearchInput.text.toString().lowercase())
                    }

                    var filList3 = posts.filter { post ->
                        post.caption!!.lowercase().contains(viewBinding.etSearchInput.text.toString().lowercase())
                    }

                    var filList4 = posts.filter { post ->
                        post.store!!.city!!.lowercase().contains(viewBinding.etSearchInput.text.toString().lowercase())
                    }

                    val combinedArr = filList1.plus(filList2).plus(filList3).plus(filList4)

                    if (combinedArr.isEmpty()) {
                        showToast(combinedArr as ArrayList<Post>)
                    }

                    myAdapter.setData(combinedArr)

                } else {
                    posts.clear()
                    this.myAdapter.notifyDataSetChanged()
                    callPostQuery()
                }
            }

        }

        // This command listens whether Search in Keyboard was pressed
        viewBinding.etSearchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doFilter()
                true
            } else {
                Toast.makeText(baseContext, "Invalid",
                    Toast.LENGTH_SHORT).show()
                false
            }
        }

        viewBinding.ibSearchBtn.setOnClickListener(View.OnClickListener {
            doFilter()
        })

        val createPostButton = viewBinding.fabCreatePost

        createPostButton.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            gallery.type = "image/*";
            galleryViewLauncher.launch(gallery)
        }

        viewBinding.imageButton.setOnClickListener{
            val intent = Intent(this@HomeActivity, UserProfileActivity::class.java)
            this.userProfileLauncher.launch(intent)
        }

        //Remove flickering of item
        val animator = recyclerView.itemAnimator
        if (animator is SimpleItemAnimator) {
            (animator as SimpleItemAnimator).supportsChangeAnimations = false
        }

    }

    // Once the user comes is redirected to the home activity, reload the current data
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {document ->
                if(document != null) {
                    user = User(auth.currentUser!!.uid,document!!.getString("username").toString(),document!!.getString("fullName").toString(),document!!.getString("email").toString(), parseInt(document!!.getLong("profilePic").toString()) )
                }
            }
        }

        //Remove all posts, if posts is empty, then notify the adapter
        if (posts.isEmpty() != true) {
            posts.clear()
            this.myAdapter.notifyDataSetChanged()
        }

        this.callPostQuery()
    }

    /**
     * callPostQuery retrieves all the post data then transfers it to the adapter
     */
    private fun callPostQuery() {

        db.collection("posts").orderBy("datePosted", Query.Direction.DESCENDING).get().addOnSuccessListener { documents ->
            if(documents != null){
                var index=0
                for (document in documents) {
                    Log.d("SDAJKDLJWIOJLOOOOOOOOOOOOOOOOK", "onCreate: document is:" + document.getString("storeID").toString())
                    db.collection("users").document(document.getString("userID").toString()).get().addOnSuccessListener {user ->
                        db.collection("stores").document(document.getString("storeID").toString()).get().addOnSuccessListener { store ->
                            val poster = User(user.id,user.getString("username").toString(),user.getString("fullName").toString(),user.getString("email").toString(),user.getLong("profilePic")!!.toInt())
                            val store = Store(store.id, store.getString("name"), store.getString("city"))
                            val datePosted = SimpleDateFormat("MMM d, yyyy HH:mm:ss").format(document.getDate("datePosted"))
                            posts.add(Post(document.id,poster,document.getString("imagePost"),document.getString("caption"),store,datePosted, false, document["likes"] as ArrayList<String>))
//                            oldPosts.add(Post(document.id,poster,document.getString("imagePost"),document.getString("caption"),store,datePosted, false, document["likes"] as ArrayList<String>))
                            this.myAdapter.notifyItemInserted(index)
                            index++



                            if (documents.size() == index) {
                                val dateFormat= SimpleDateFormat("MMM d, yyyy HH:mm:ss")

                                val sortedList = posts.sortedByDescending {
                                    dateFormat.parse(it.datePosted)
                                }

                                myAdapter.setData(sortedList)
                                oldPosts = sortedList as MutableList<Post>
                            }
                        }
                    }
                }
            }
        }

    }
    /** shows a Toast if input is actually invalid */
    private fun showToast(filteredList : ArrayList<Post>){
        if (filteredList.isEmpty()) {
            Toast.makeText(baseContext, "No results. Please clear filter & input",
                Toast.LENGTH_LONG).show()
        }
    }
    /** updates the currentlist in myAdapter*/
    private fun updateFilter(filteredList: ArrayList<Post>){
        myAdapter.setData(filteredList)
        showToast(filteredList)
    }

}