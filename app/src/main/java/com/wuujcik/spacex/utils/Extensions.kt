package com.wuujcik.spacex.utils

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.Gravity
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

/**
 * Resize dialog window to match mobile screen
 */
fun DialogFragment.resizeDialogWindowToMatchScreen() {
    context?.let { _ ->
        with(dialog?.window) {
            val frame = Rect()
            this?.decorView?.getWindowVisibleDisplayFrame(frame)
            this?.setLayout(frame.width(), frame.height())
            this?.setGravity(Gravity.CENTER)
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun Date.toIsoDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    return formatter.format(this)
}


fun Date.toUnixTime(): Long {
    return (this.time / 1000).toLong()
}