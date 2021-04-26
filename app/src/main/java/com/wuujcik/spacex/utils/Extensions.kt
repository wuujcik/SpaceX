package com.wuujcik.spacex.utils

import android.graphics.Rect
import android.view.Gravity
import androidx.fragment.app.DialogFragment

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