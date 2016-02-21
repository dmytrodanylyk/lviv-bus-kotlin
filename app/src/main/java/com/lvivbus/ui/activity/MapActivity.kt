package com.lvivbus.ui.activity

import android.app.Activity
import android.os.Bundle
import com.lvivbus.model.db.BusDAO
import com.lvivbus.utils.L
import io.realm.Realm

class MapActivity : Activity() {

    val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BusDAO().getAll(realm).forEach {
            L.v("Bus ${it.name} ${it.code}")
        }
    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }
}