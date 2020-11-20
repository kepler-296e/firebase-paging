package com.noname.firebasepagination.post

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.noname.firebasepagination.R
import com.noname.firebasepagination.main.MainActivity

class PostAdapter(options: FirestorePagingOptions<Post>, private val mainActivity: MainActivity) :
    FirestorePagingAdapter<Post, PostViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int, post: Post) {
        // Bind to ViewHolder
        viewHolder.bind(post)
    }

    override fun onLoadingStateChanged(state: LoadingState) {
        when (state) {
            LoadingState.LOADING_INITIAL -> mainActivity.refreshLayout()
            LoadingState.LOADED,
            LoadingState.FINISHED,
            LoadingState.ERROR -> mainActivity.stopRefreshingLayout()
        }
    }
}