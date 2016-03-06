package com.lvivbus.model.data

import com.google.gson.annotations.SerializedName

data class BusLocationResult(
        @SerializedName("X")
        var x: Float,

        @SerializedName("Y")
        var y: Float,

        @SerializedName("Angle")
        var angle: Float,

        @SerializedName("RouteId")
        var routeId: Int,

        @SerializedName("VehicleId")
        var vehicleId: Int,

        @SerializedName("VehicleName")
        var vehicleName: String
)