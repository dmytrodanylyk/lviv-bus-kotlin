package com.lvivbus.ui.utils

import android.content.Context
import android.graphics.Canvas
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView

class DividerItemDecoration(context: Context, resId: Int) : RecyclerView.ItemDecoration() {

    private var leftPadding: Int = 0
    private var rightPadding: Int = 0

    private var mDivider = ContextCompat.getDrawable(context, resId)

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0..childCount - 1) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight

            mDivider.setBounds(left + leftPadding, top, right - rightPadding, bottom)
            mDivider.draw(c)
        }
    }

    fun setPadding(left: Int, right: Int) {
        leftPadding = left
        rightPadding = right
    }
}

