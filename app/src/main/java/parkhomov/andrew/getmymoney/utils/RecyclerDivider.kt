package parkhomov.andrew.getmymoney.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ItemDecoration
import android.support.v7.widget.RecyclerView.State
import android.view.View

class RecyclerDivider(dividerHeight: Int, color: Int) : ItemDecoration() {

    private var mDividerHeight: Int = dividerHeight
    private var mColor: Int = color

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
        val layoutManager = parent.layoutManager
                ?: throw RuntimeException("LayoutManager not found")
        if (layoutManager.getPosition(view) != 0) outRect.top = 0
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: State) {
        val paint = Paint()
        paint.color = mColor

        val left = parent.paddingLeft
        val right = left + parent.width - parent.paddingRight

        for (i in 0 until parent.childCount) {

            val top = parent.getChildAt(i).bottom
            val bottom = top + mDividerHeight

            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }
}