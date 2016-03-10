package com.lvivbus.model.http

import com.lvivbus.model.data.BusLocationResult
import com.lvivbus.model.data.BusResult

class BusAPI : AbsBusAPI() {

    fun getBusList(): List<BusResult> {
        return get(UrlFactory.createBusListUrl()) ?: listOf()
    }

    fun getBusLocation(code: String?): List<BusLocationResult> {
        return get(UrlFactory.createBusLocationUrl(code)) ?: mutableListOf()
    }

}