package com.noname.firebasepagination.auth

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.noname.firebasepagination.R
import com.noname.firebasepagination.base.AuthBaseActivity
import com.noname.firebasepagination.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AuthBaseActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = getString(R.string.login)
        // start main activity if user is already logged
        if (auth.currentUser != null)
            startActivity(MainActivity::class.java, true)
        // setting buttons
        btn_login.setOnClickListener { login() }
        btn_go_to_sign_up.setOnClickListener { startActivity(SignUpActivity::class.java) }
    }

    private fun login() {
        val email = etxt_login_email.text.toString()
        val password = etxt_login_password.text.toString()
        // sign in if email and password are not empty
        if (email.isNotBlank()) {
            if (password.isNotBlank()) {
                signInWithEmailAndPassword(email, password)
            } else showEmptyFieldError(etxt_login_password)
        } else showEmptyFieldError(etxt_login_email)
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { startActivity(MainActivity::class.java, true) }
            .addOnFailureListener { showToast(it.message.toString()) }
    }
}