package com.lvivbus.ui.data

import com.lvivbus.model.data.BusLocationResult

data class BusMarker(
        val lat: Float,
        val lon: Float,
        val angle: Float,
        val routeId: Int,
        val vehicleId: Int,
        val vehicleName: String) {

    constructor(result: BusLocationResult) : this(
            result.y,
            result.x,
            result.angle,
            result.routeId,
            result.vehicleId,
            result.vehicleName
    )
}