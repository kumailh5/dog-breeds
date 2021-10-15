package com.kumail.dogbreeds.util

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


/**
 * Created by kumailhussain on 15/10/2021.
 */
class TopLineDivider(context: Context) : ItemDecoration() {
    private var divider: Drawable?

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }

    init {
        val a: TypedArray = context.obtainStyledAttributes(ATTRS)
        divider = a.getDrawable(0)!!
        a.recycle()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top: Int = child.top - params.topMargin
            val bottom = top + divider!!.intrinsicHeight
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            divider?.setBounds(left, top, right, bottom)
            divider?.draw(c)
        }
    }
}