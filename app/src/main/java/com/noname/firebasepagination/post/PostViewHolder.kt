package com.noname.firebasepagination.post

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.noname.firebasepagination.R
import java.sql.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val titleTextView: TextView = itemView.findViewById(R.id.txt_post_title)
    private val messageTextView: TextView = itemView.findViewById(R.id.txt_post_message)
    private val timestampTextView: TextView = itemView.findViewById(R.id.txt_post_timestamp)

    fun bind(post: Post) {
        titleTextView.text = post.title
        messageTextView.text = post.message
        timestampTextView.text = getDateFromMillis(post.timestamp!!)
    }

    private fun getDateFromMillis(timestamp: Long): String {
        val date = Date(timestamp)
        val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy - HH:mm aa", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        return formatter.format(date)
    }
}