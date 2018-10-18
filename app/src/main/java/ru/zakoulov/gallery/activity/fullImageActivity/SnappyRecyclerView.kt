package ru.zakoulov.gallery.activity.fullImageActivity

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

/**
 * Стандартная реализация fling работает не так как необходимо.
 * From nesquena/SnappyRecyclerView.java
 */

class SnappyRecyclerView : RecyclerView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        val linearLayoutManager = layoutManager as LinearLayoutManager

        val screenWidth = Resources.getSystem().displayMetrics.widthPixels

        // views on the screen
        val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
        val lastView = linearLayoutManager.findViewByPosition(lastVisibleItemPosition)
        val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
        val firstView = linearLayoutManager.findViewByPosition(firstVisibleItemPosition)

        // distance we need to scroll
        val leftMargin = (screenWidth - lastView.width) / 2
        val rightMargin = (screenWidth - firstView.width) / 2 + firstView.width
        val leftEdge = lastView.left
        val rightEdge = firstView.right
        val scrollDistanceLeft = leftEdge - leftMargin
        val scrollDistanceRight = rightMargin - rightEdge

        if (Math.abs(velocityX) < 1000) {
            // The fling is slow -> stay at the current page if we are less than half through,
            // or go to the next page if more than half through

            if (leftEdge > screenWidth / 2)
            // go to next page
                smoothScrollBy(-scrollDistanceRight, 0)
            else if (rightEdge < screenWidth / 2)
            // go to next page
                smoothScrollBy(scrollDistanceLeft, 0)
            else
            // stay at current page
                if (velocityX > 0)
                    smoothScrollBy(-scrollDistanceRight, 0)
                else
                    smoothScrollBy(scrollDistanceLeft, 0)
            return true

        } else {
            // The fling is fast -> go to next page
            if (velocityX > 0)
                smoothScrollBy(scrollDistanceLeft, 0)
            else
                smoothScrollBy(-scrollDistanceRight, 0)
            return true

        }
    }
}
