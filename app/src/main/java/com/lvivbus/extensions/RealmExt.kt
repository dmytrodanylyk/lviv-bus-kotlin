package com.lvivbus.extensions

import io.realm.Realm

fun Realm.executeAndClose(code: (Realm) -> Unit) {
    executeTransaction {
        code(this)
    }
    close()
}

