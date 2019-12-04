package com.antonicastejon.cryptodata.common

import android.graphics.Color
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.antonicastejon.cryptodata.R
import com.google.android.material.snackbar.Snackbar

fun View.toggleVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

infix fun View.onClick(action: (() -> Unit)) {
    this.setOnClickListener { action.invoke() }
}

fun View.showSnackbar(message: String) {
    if (!TextUtils.isEmpty(message)) {
        val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        snackBar.setActionTextColor(Color.WHITE)
        val sbView = snackBar.view
        sbView.setBackgroundColor(ContextCompat.getColor(this.context, R.color.colorAccent))
        snackBar.show()
    }
}