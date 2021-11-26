package com.kumail.dogbreeds.util

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kumail.dogbreeds.data.model.ErrorResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi


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
    val moshi: Moshi = Moshi.Builder().build()
    val adapter: JsonAdapter<ErrorResponse> = moshi.adapter(ErrorResponse::class.java)
    val errorResponse = adapter.fromJson(this)

    return errorResponse ?: ErrorResponse()
}

fun ImageView.rotate() {
    this.animate().rotationBy(180f).setDuration(200).start()
}