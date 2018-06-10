package parkhomov.andrew.getmymoney.ui.activity.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.person_adapter_item.view.*
import parkhomov.andrew.getmymoney.R
import java.util.*

class InitialAdapter : RecyclerView.Adapter<InitialAdapter.ViewHolder>() {

    internal var personList: MutableList<InitialActivity.PersonItem> = mutableListOf()
    internal var isCalculated = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.person_adapter_item,
                    parent,
                    false)
            )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bindPersonItem(personList[position])

    override fun getItemCount(): Int = personList.count()

    inner class ViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {

        internal fun bindPersonItem(personItem: InitialActivity.PersonItem) {
            view.number.text = String.format(
                    Locale.US,
                    "%s%s",
                    adapterPosition + 1, ".")
            view.target_value.visibility = if(isCalculated) View.VISIBLE else View.GONE
            view.target_value.text = String.format(
                    Locale.US,
                    "%.02f",
                    personItem.targetValue)
            view.target_value.setTextColor(personItem.targetValueColor)
            view.person_name.text = personItem.name
            view.person_value.text = personItem.value.toString()
        }
    }
}
