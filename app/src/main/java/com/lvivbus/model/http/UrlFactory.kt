package com.lvivbus.model.http

class UrlFactory {

    companion object {

        private val BASE_URL = "http://82.207.107.126:13541/SimpleRIDE/LAD/SM.WebApi/api"

        fun createBusLocationUrl(code: String?) = "$BASE_URL/RouteMonitoring/?code=$code"
        fun createBusListUrl() = "$BASE_URL/CompositeRoute"
    }
}