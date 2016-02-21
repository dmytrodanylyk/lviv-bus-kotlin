package com.lvivbus.ui.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Bus(@PrimaryKey open var id: Int = 0, open var code: String? = null, open var name: String? = null) : RealmObject()