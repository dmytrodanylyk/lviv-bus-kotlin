package com.lvivbus.ui

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class BusApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.setDefaultConfiguration(RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build())
    }
}