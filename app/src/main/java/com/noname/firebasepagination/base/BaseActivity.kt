package com.noname.firebasepagination.base

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    fun <T> startActivity(activity: Class<T>, finish: Boolean = false) {
        val intent = Intent(this, activity)
        startActivity(intent)
        if (finish) finish()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun <T> startActivityFinishingAllPrevious(activity: Class<T>) {
        val intent = Intent(this, activity)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}