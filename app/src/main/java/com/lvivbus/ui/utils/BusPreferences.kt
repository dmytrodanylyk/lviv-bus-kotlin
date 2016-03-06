package com.lvivbus.ui.utils

import android.content.Context

class BusPreferences(context: Context) {

    companion object {
        val PREF_NAME = "DEFAULT";
        val PREF_KEY_BUS_ID = "PREF_KEY_BUS_ID";
    }

    private val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun putBusId(busId: Int) {
        preference.edit().putInt(PREF_KEY_BUS_ID, busId).apply()
    }

    fun getBusId(): Int? {
        return preference.getInt(PREF_KEY_BUS_ID, -1)
    }

}