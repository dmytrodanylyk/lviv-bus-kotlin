package com.lvivbus.extensions

import io.realm.Realm
import io.realm.RealmObject

fun Realm.executeAndClose(func: Realm.() -> Unit) {
    executeTransaction {
        func()
    }
    close()
}

fun <E : RealmObject> Realm.copyFromRealmSafe(realmObject: E?): E? = realmObject?.let { copyFromRealm(it) }
