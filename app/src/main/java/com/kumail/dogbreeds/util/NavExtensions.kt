package com.kumail.dogbreeds.util

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import timber.log.Timber

/**
 * Created by kumailhussain on 12/10/2021.
 */
fun Fragment.navigatePopUp(): Boolean {
    return try {
        NavHostFragment.findNavController(this).popBackStack()
    } catch (ex: Exception) {
        Timber.e(ex, "Unable to pop up from this view")
        false
    }
}

fun Fragment.navigateTo(@IdRes destination: Int) {
    try {
        NavHostFragment.findNavController(this).navigate(destination)
    } catch (ex: Exception) {
        Timber.e(ex, "Unable to navigate from this fragment")
    }
}

fun Fragment.navigateTo(navDirections: NavDirections) {
    try {
        NavHostFragment.findNavController(this).navigate(navDirections)
    } catch (ex: Exception) {
        Timber.e(ex, "Unable to navigate from this fragment")
    }
}

fun View.navigateTo(@IdRes destination: Int) {
    try {
        this.findNavController().navigate(destination)
    } catch (ex: Exception) {
        Timber.e(ex, "Unable to navigate from this fragment")
    }
}

fun View.navigateTo(navDirections: NavDirections) {
    try {
        this.findNavController().navigate(navDirections)
    } catch (ex: Exception) {
        Timber.e(ex, "Unable to navigate from this fragment")
    }
}