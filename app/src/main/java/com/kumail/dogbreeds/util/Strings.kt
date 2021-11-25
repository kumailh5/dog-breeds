package com.kumail.dogbreeds.util

import androidx.annotation.StringRes
import com.kumail.dogbreeds.DogBreedsApplication

/**
 * Created by kumailhussain on 31/10/2021.
 */
object Strings {
    fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return DogBreedsApplication.instance.getString(stringRes, *formatArgs)
    }
}