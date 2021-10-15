package com.kumail.dogbreeds.util

import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.kumail.dogbreeds.R

/**
 * Created by kumailhussain on 14/10/2021.
 */
fun showErrorDialog(
    context: Context,
    message: String,
    listener: (DialogInterface, Int) -> Unit = { _, _ -> }
) {
    AlertDialog.Builder(context)
        .setTitle(R.string.error)
        .setMessage(message)
        .setPositiveButton(R.string.ok) { dialogInterface, i ->
            run {
                dialogInterface.dismiss()
                listener(dialogInterface, i)
            }
        }
        .setCancelable(true)
        .create()
        .show()
}