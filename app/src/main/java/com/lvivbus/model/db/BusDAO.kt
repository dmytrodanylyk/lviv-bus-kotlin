package com.lvivbus.model.db

import com.lvivbus.extensions.executeAndClose
import com.lvivbus.ui.data.Bus
import io.realm.Realm
import io.realm.Sort
import java.util.*

class BusDAO {

    companion object {

        fun getAll(realm: Realm) = realm.where(Bus::class.java).findAllSorted("recentDate", Sort.DESCENDING);

        fun getAllCount(): Long = Realm.getDefaultInstance().use { return it.where(Bus::class.java).count() }

        fun save(busList: List<Bus>) = Realm.getDefaultInstance().executeAndClose { it.copyToRealm(busList) }

        fun setRecentDate(busId: Int, date: Date) = Realm.getDefaultInstance().executeAndClose {
            val bus: Bus? = it.where(Bus::class.java).equalTo("id", busId).findFirst();
            bus?.recentDate = date
        }

    }
}
