package com.lvivbus.extensions

import io.realm.Realm

fun Realm.executeAndClose(func: Realm.() -> Unit) {
    executeTransaction {
        func()
    }
    close()
}

