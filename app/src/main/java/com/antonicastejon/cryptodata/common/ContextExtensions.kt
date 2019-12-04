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
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
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
        is SocketTimeoutException, is TimeoutException -> showShortToast(R.string.time_out_message)
        is UnknownHostException, is ConnectException -> showShortToast(R.string.no_connection)
        is HttpException -> {
            when (error.code()) {
                401 -> {
                    /* SessionManager(AppController.getContext()).clearLoginSession()
             startActivity(Intent(this, LoginActivity::class.java))
             showShortToast(R.string.session)*/
                }
                429 -> showShortToast(R.string.error_too_many_requests)
                else -> {
                    showShortToast(R.string.error_occurred)
                }
            }
        }
        else -> showShortToast(R.string.error_occurred)
    }
}