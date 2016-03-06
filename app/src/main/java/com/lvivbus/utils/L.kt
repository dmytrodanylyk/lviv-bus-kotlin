package com.lvivbus.utils

import android.util.Log

class L {

    companion object {
        var TAG = "LvivBus"

        fun v(msg: String?) = Log.v(TAG, msg)
        fun d(msg: String?) = Log.v(TAG, msg)
    }

}