package com.netchar.nicknamer.presentation.infrastructure.helpers

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.netchar.nicknamer.R

class SwipeToDeleteCallback(
    private val context: Context,
    private val listener: Listener
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    interface Listener {
        fun onSwiped(viewHolder: RecyclerView.ViewHolder)
    }

    private val background by lazy {
        ColorDrawable(ContextCompat.getColor(context.applicationContext, R.color.red_600))
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onSwiped(viewHolder)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView: View = viewHolder.itemView
        when {
            dX > 0 -> background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
            dX < 0 -> background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
            else -> background.setBounds(0, 0, 0, 0)
        }
        background.draw(c)
    }
}