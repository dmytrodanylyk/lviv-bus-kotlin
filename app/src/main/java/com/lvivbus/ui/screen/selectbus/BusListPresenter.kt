package com.lvivbus.ui.screen.selectbus

import android.os.Bundle
import com.lvivbus.model.db.BusDAO
import com.lvivbus.ui.data.Bus
import com.lvivbus.ui.screen.AbsPresenter
import com.lvivbus.ui.utils.BusPreferences
import io.realm.Realm
import java.util.*

class BusListPresenter(val activity: BusListActivity) : AbsPresenter() {

    private var maxRecentCount: Int = 3
    private val realm: Realm = Realm.getDefaultInstance()

    override fun onActivityAttached(savedInstanceState: Bundle?) {
        loadData()
    }

    override fun onActivityDetached() {
        realm.close()
    }

    fun onBusClicked(bus: Bus) {
        BusDAO.setRecentDate(bus.id, Date())
        BusPreferences(activity).putBusId(bus.id)
        activity.finish()
    }

    fun onToolbarBackClicked() {
        activity.finish()
    }

    private fun loadData() {
        val busList = BusDAO.getAll(realm)
        var recentBusList = busList.filter { it.recentDate != null }.take(maxRecentCount)
        val allBusList = busList.subList(recentBusList.size, busList.size).sortedBy { it.name }

        activity.setDate(recentBusList, allBusList);
    }
}