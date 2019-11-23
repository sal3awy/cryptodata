package com.antonicastejon.cryptodata.common

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.antonicastejon.cryptodata.R
import java.util.concurrent.TimeoutException


fun Context.showLongToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Context.showLongToast(msgId: Int) {
    Toast.makeText(this, getString(msgId), Toast.LENGTH_SHORT).show()
}

fun Context.showShortToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.showShortToast(msgId: Int) {
    Toast.makeText(this, getString(msgId), Toast.LENGTH_SHORT).show()
}

fun Context.getDeviceMetrics(): DisplayMetrics {
    val metrics = DisplayMetrics()
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    display.getMetrics(metrics)
    return metrics
}

fun Context.hideKeyboard(view: View?) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}

fun Fragment.showMessage(error: Throwable) {
    activity?.run {
        showMessage(error)
    }
}

fun Activity.showMessage(error: Throwable) {
    when (error) {
        is InternalServerErrorException -> showShortToast(R.string.error_occurred)
        is TimeoutException -> showShortToast(R.string.time_out_message)
        is ServerUnreachableException -> showShortToast(R.string.no_connection)
        is UnAuthorizedException -> {
            /* SessionManager(AppController.getContext()).clearLoginSession()
             startActivity(Intent(this, LoginActivity::class.java))
             showShortToast(R.string.session)*/
        }
    }
}