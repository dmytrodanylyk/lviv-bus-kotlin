package com.lvivbus.model.http

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lvivbus.utils.L
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Type

open class AbsBusAPI {

    protected inline fun <reified T : Any> get(url: String): T? {
        val header = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"

        L.d("Performing request: $url")

        val request = Request.Builder().url(url).addHeader("Accept", header).build()
        val response = OkHttpClient().newCall(request).execute()

        L.d("Result status code: ${response.code()}")

        if (response.isSuccessful) {
            val json = toJson(response.body().string())
            L.d("Result json: $json")
            return Gson().fromJson(json)
        }

        return null
    }

    protected fun toJson(xml: String): String {
        val start = xml.indexOf('[')
        val end = xml.indexOf(']')
        if (start != -1 && end != -1 && end > start) {
            return xml.substring(start, end + 1)
        }

        return xml
    }

    protected inline fun <reified T : Any> Gson.fromJson(json: String): T = fromJson(json, typeToken<T>())
    protected inline fun <reified T : Any> typeToken(): Type = object : TypeToken<T>() {}.type
}