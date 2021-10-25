package com.kumail.dogbreeds.util

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.kumail.dogbreeds.data.model.ErrorResponse


/**
 * Created by kumailhussain on 13/10/2021.
 */
fun RecyclerView.enableTopDivider() {
    val topLineDecoration = TopLineDivider(context)
    addItemDecoration(topLineDecoration)
}

fun Fragment.setToolbarTitle(title: String) {
    activity?.title = title
}

fun String.parseErrorJson(): ErrorResponse {
    return Gson().fromJson(this, ErrorResponse::class.java)
}

fun ImageView.rotate() {
    this.animate().rotationBy(180f).setDuration(200).start()
}