package com.noname.firebasepagination.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.noname.firebasepagination.R
import com.noname.firebasepagination.auth.LoginActivity
import com.noname.firebasepagination.base.BaseActivity
import com.noname.firebasepagination.post.Post
import com.noname.firebasepagination.post.PostAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.pow

class MainActivity : BaseActivity(), MainActivityInterface {

    // Init paging configuration
    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPrefetchDistance(2)
        .setPageSize(10)
        .build()

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseFirestore.getInstance()
    private val mQuery = database.collection("posts")

    // Init adapter options
    private val options = FirestorePagingOptions.Builder<Post>()
        .setLifecycleOwner(this)
        .setQuery(mQuery, config, Post::class.java)
        .build()

    private var postAdapter = PostAdapter(options, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Setting main recycler view adapter
        rview_main.adapter = postAdapter
        // Setting add post fab button
        fab_add_post.setOnClickListener {
            val post = Post(
                title = getRandomNumber(),
                message = getRandomNumber(),
                timestamp = System.currentTimeMillis()
            )
            addPost(post)
        }
        // Refresh adapter with swipe refresh
        srlayout_main.setOnRefreshListener { postAdapter.refresh() }
    }

    override fun refreshLayout() {
        srlayout_main.isRefreshing = true
    }

    override fun stopRefreshingLayout() {
        srlayout_main.isRefreshing = false
    }

    private fun getRandomNumber(): String {
        return (0..(10.0.pow(5).toInt())).random().toString()
    }

    private fun addPost(post: Post) {
        mQuery.add(post)
            .addOnSuccessListener { postAdapter.refresh() }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_sign_out -> signOut()
            R.id.item_delete_all_posts -> deleteAllPosts()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun signOut() {
        auth.signOut()
        startActivity(LoginActivity::class.java)
        finish()
    }

    private fun deleteAllPosts() {
        mQuery.get()
            .addOnSuccessListener {
                for (snapshot in it.documents)
                    deletePost(snapshot.id)
            }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    private fun deletePost(id: String) {
        mQuery.document(id).delete()
            .addOnSuccessListener { postAdapter.refresh() }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

}