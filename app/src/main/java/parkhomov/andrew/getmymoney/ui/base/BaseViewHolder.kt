package parkhomov.andrew.getmymoney.ui.base

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun onBind(position: Int) {}

    companion object {

        const val VIEW_TYPE_EMPTY = 0

    }
}
