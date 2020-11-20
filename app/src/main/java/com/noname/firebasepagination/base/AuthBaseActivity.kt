package com.noname.firebasepagination.base

import com.google.android.material.textfield.TextInputEditText
import com.noname.firebasepagination.R

open class AuthBaseActivity : BaseActivity() {

    fun showEmptyFieldError(textInputEditText: TextInputEditText) {
        textInputEditText.error = getString(R.string.empty_field)
    }
}