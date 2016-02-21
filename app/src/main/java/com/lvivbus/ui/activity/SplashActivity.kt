package com.lvivbus.ui.activity

import android.app.Activity
import android.os.Bundle
import com.lvivbus.R
import com.lvivbus.model.db.BusDAO
import com.lvivbus.model.http.BusAPI
import com.lvivbus.ui.data.Bus
import com.lvivbus.utils.L
import org.jetbrains.anko.async
import org.jetbrains.anko.intentFor
import java.util.concurrent.Executors

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        if (BusDAO.getAllCount() == 0L) {
            loadData()
        } else {
            onLoadingComplete()
        }
    }

    private fun loadData() {
        async(Executors.newSingleThreadExecutor()) {

            val busList = BusAPI().getBusList().map { it -> Bus(it.id, it.code, it.name) }
            BusDAO.save(busList)
            L.v("Buses saved: ${busList.size}")

            runOnUiThread {
                onLoadingComplete()
            }
        }
    }

    private fun onLoadingComplete() {
        startActivity(intentFor<MapActivity>())
        finish()
    }
}
