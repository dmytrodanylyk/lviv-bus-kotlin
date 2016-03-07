package com.lvivbus.model.http

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lvivbus.model.data.BusLocationResult
import com.lvivbus.model.data.BusResult
import com.lvivbus.utils.L
import okhttp3.OkHttpClient
import okhttp3.Request

class BusAPI {

    fun getBusList(): List<BusResult> {
        val busList = mutableListOf<BusResult>()

        val url = "http://82.207.107.126:13541/SimpleRIDE/LAD/SM.WebApi/api/CompositeRoute"
        val header = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"

        L.d("Performing request $url")

        val request = Request.Builder().url(url).addHeader("Accept", header).build()
        val response = OkHttpClient().newCall(request).execute()
        if (response.isSuccessful) {
            val json = toJson(response.body().string())
            val type = object : TypeToken<MutableList<BusResult>>() {}.type
            busList.addAll(Gson().fromJson(json, type))
        }

        return busList
    }

    fun getBusLocation(code: String?): List<BusLocationResult> {
        val busList = mutableListOf<BusLocationResult>()

        val url = "http://82.207.107.126:13541/SimpleRIDE/LAD/SM.WebApi/api/RouteMonitoring/?code=$code"
        val header = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"

        L.d("Performing request $url")

        val request = Request.Builder().url(url).addHeader("Accept", header).build()
        val response = OkHttpClient().newCall(request).execute()
        if (response.isSuccessful) {
            val json = toJson(response.body().string())
            val type = object : TypeToken<MutableList<BusLocationResult>>() {}.type
            busList.addAll(Gson().fromJson(json, type))
        }

        return busList
    }

    private fun toJson(xml: String): String {
        val start = xml.indexOf('[')
        val end = xml.indexOf(']')
        if (start != -1 && end != -1 && end > start) {
            return xml.substring(start, end + 1)
        }

        return xml
    }
}