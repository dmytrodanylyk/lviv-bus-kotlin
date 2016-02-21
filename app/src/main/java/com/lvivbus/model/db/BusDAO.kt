package com.lvivbus.model.db

import com.lvivbus.ui.data.Bus
import io.realm.Realm

class BusDAO {

    companion object {
        fun getAll(realm: Realm) = realm.allObjects(Bus::class.java)

        fun save(busList: List<Bus>) = Realm.getDefaultInstance().executeTransaction { it.copyToRealm(busList) }

        fun getAllCount(): Long = Realm.getDefaultInstance().use { return it.where(Bus::class.java).count() }
    }
}
