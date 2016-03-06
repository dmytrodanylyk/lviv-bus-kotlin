package com.lvivbus.ui.screen.selectbus

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lvivbus.R
import com.lvivbus.extensions.mapSafe
import com.lvivbus.ui.data.Bus
import com.lvivbus.ui.data.Displayable
import com.lvivbus.ui.data.Title

class BusListAdapter(val context: Context, val listener: (Bus) -> Unit) : RecyclerView.Adapter<BusListAdapter.ViewHolder>() {

    private val TYPE_TITLE = 0
    private val TYPE_BUS = 1
    private val TYPE_UNDEFINED = 100

    private var filteredList: MutableList<Displayable> = mutableListOf()
    private var originalList: MutableList<Displayable> = mutableListOf()
    private var inflater = LayoutInflater.from(context)

    override fun getItemCount() = filteredList.size

    override fun getItemViewType(position: Int): Int = when (filteredList[position]) {
        is Bus -> TYPE_BUS
        is Title -> TYPE_TITLE
        else -> TYPE_UNDEFINED
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder? = when (viewType) {
        TYPE_TITLE -> ViewHolder(inflater.inflate(R.layout.bus_list_item_title, viewGroup, false))
        TYPE_BUS -> ViewHolder(inflater.inflate(R.layout.bus_list_item, viewGroup, false))
        else -> null
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, index: Int) {
        val displayable = filteredList[index]
        when (displayable) {
            is Title -> viewHolder.title.text = displayable.value
            is Bus -> initBus(viewHolder, displayable)
        }
    }

    fun setData(recentList: List<Bus>, busList: List<Bus>) {
        if (recentList.isNotEmpty()) {
            filteredList.add(Title(context.getString(R.string.Recent).toUpperCase()))
            filteredList.addAll(recentList)
            filteredList.add(Title(context.getString(R.string.All).toUpperCase()))
        }
        filteredList.addAll(busList)
        originalList.addAll(filteredList)
    }

    fun filter(filter: String) {
        filteredList.clear()
        if (filter.isEmpty()) {
            filteredList.addAll(originalList)
        } else {
            val filteredBusList = originalList
                    .mapSafe { it as Bus }
                    .filter { it.name?.contains(filter, true) ?: false }
                    .sortedBy { it.name }
            filteredList.addAll(filteredBusList)
        }

        notifyDataSetChanged()
    }

    private fun initBus(viewHolder: ViewHolder, bus: Bus) = with(viewHolder) {
        title.text = bus.name
        title.setOnClickListener { listener(bus) }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById(R.id.txt_title) as TextView
    }
}