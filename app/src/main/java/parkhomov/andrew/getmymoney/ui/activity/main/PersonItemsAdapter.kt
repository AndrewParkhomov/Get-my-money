package parkhomov.andrew.getmymoney.ui.activity.main

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.person_adapter_item.view.*
import parkhomov.andrew.getmymoney.R
import parkhomov.andrew.getmymoney.ui.base.BaseViewHolder
import java.util.*

class PersonItemsAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    internal var personList: MutableList<MainActivity.PersonItem> = mutableListOf()
    internal var isCalculated = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            BaseViewHolder.VIEW_TYPE_PERSON -> PersonHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.person_adapter_item,
                    parent,
                    false)
            )
            else -> EmptyHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.person_adapter_empty,
                    parent,
                    false)
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.onBind(position)

    override fun getItemCount(): Int = if (personList.isNotEmpty()) personList.count() else 1

    override fun getItemViewType(position: Int): Int =
            if (personList.isNotEmpty())
                BaseViewHolder.VIEW_TYPE_PERSON
            else
                0

    inner class PersonHolder(private var view: View) : BaseViewHolder(view) {

        override fun onBind(position: Int) {
            super.onBind(position)
            val person = personList[position]
            view.number.text = String.format(
                    Locale.US,
                    "%s%s",
                    adapterPosition + 1, ".")
            view.target_value.visibility = if (isCalculated) View.VISIBLE else View.GONE
            view.target_value.text = String.format(
                    Locale.US,
                    "%.02f",
                    person.targetValue)
            view.target_value.setTextColor(person.targetValueColor)
            // set running line if needed
            view.person_name.apply {
                text = person.name
                ellipsize = TextUtils.TruncateAt.MARQUEE
                marqueeRepeatLimit = -1
                setSingleLine(true)
                isSelected = true
            }
            view.person_value.text = person.value.toString()
        }
    }

    inner class EmptyHolder(view: View) : BaseViewHolder(view)
}
