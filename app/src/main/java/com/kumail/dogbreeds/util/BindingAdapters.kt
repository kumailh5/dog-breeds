package com.kumail.dogbreeds.util

import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import timber.log.Timber

/**
 * Created by kumailhussain on 12/10/2021.
 */
@BindingAdapter("loadImage")
fun ImageView.loadImage(url: String?) {
    try {
        when {
            url.isNullOrBlank() -> {
                this.setImageDrawable(null)
            }
            else -> {
                Glide.with(this)
                    .load(url)
                    .into(this)
            }
        }
    } catch (ex: Exception) {
        Timber.e(ex, "Unable to load image url: $url")
    }
}

@BindingAdapter("setVisibility")
fun RecyclerView.setVisibility(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("setVisibility")
fun ProgressBar.setVisibility(isVisible: Boolean) {
    this.isVisible = isVisible
}


