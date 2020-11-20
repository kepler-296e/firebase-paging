package com.noname.firebasepagination.auth

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.noname.firebasepagination.R
import com.noname.firebasepagination.base.AuthBaseActivity
import com.noname.firebasepagination.main.MainActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AuthBaseActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        // display back arrow in action bar
        title = getString(R.string.sign_up)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // setting sign up button click listener
        btn_sign_up.setOnClickListener { signUp() }
    }

    private fun signUp() {
        val email = etxt_sign_up_email.text.toString()
        val password = etxt_sign_up_password.text.toString()
        // create user if email and password are not empty
        if (email.isNotBlank()) {
            if (password.isNotBlank()) {
                createUserWithEmailAndPassword(email, password)
            } else showEmptyFieldError(etxt_sign_up_password)
        } else showEmptyFieldError(etxt_sign_up_email)
    }

    private fun createUserWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { startActivityFinishingAllPrevious(MainActivity::class.java) }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

}