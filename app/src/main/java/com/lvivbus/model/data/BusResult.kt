package com.lvivbus.model.data

import com.google.gson.annotations.SerializedName

data class BusResult(
        @SerializedName("Id")
        var id: Int,

        @SerializedName("Code")
        var code: String,

        @SerializedName("Name")
        var name: String
)