package com.lvivbus.model.db

import com.lvivbus.extensions.copyFromRealmSafe
import com.lvivbus.extensions.executeAndClose
import com.lvivbus.ui.data.Bus
import io.realm.Realm
import io.realm.Sort
import java.util.*

class BusDAO {

    companion object {

        fun getAll(realm: Realm) = realm.where(Bus::class.java).findAllSorted("recentDate", Sort.DESCENDING);

        fun get(id: Int?): Bus? = Realm.getDefaultInstance().use {
            val bus = it.where(Bus::class.java).equalTo("id", id).findFirst()
            it.copyFromRealmSafe(bus)
        }

        fun getAllCount(): Long = Realm.getDefaultInstance().use { it.where(Bus::class.java).count() }

        fun save(busList: List<Bus>) = Realm.getDefaultInstance().executeAndClose { copyToRealm(busList) }

        fun setRecentDate(busId: Int, date: Date) = Realm.getDefaultInstance().executeAndClose {
            val bus = where(Bus::class.java).equalTo("id", busId).findFirst();
            bus?.recentDate = date
        }

    }
}
