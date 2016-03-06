package com.lvivbus.ui.screen.map

import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.support.annotation.UiThread
import android.util.SparseArray
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.lvivbus.model.db.BusDAO
import com.lvivbus.model.http.BusAPI
import com.lvivbus.ui.data.Bus
import com.lvivbus.ui.data.BusMarker
import com.lvivbus.ui.screen.AbsPresenter
import com.lvivbus.ui.screen.selectbus.BusListActivity
import com.lvivbus.ui.utils.BusPreferences
import com.lvivbus.utils.L
import org.jetbrains.anko.intentFor
import java.util.concurrent.TimeUnit

class MapPresenter(val activity: MapActivity) : AbsPresenter() {

    private var selectedBus: Bus? = null
    private var timer: CountDownTimer? = null
    private var markerMap: SparseArray<Marker> = SparseArray()
    private var task: AsyncTask<Bus, Void, List<BusMarker>>? = null

    private val preferences by lazy { BusPreferences(activity) }

    override fun onActivityAttached(savedInstanceState: Bundle?) {
        timer = object : CountDownTimer(TimeUnit.MINUTES.toMillis(1), TimeUnit.SECONDS.toMillis(5)) {

            override fun onFinish() {
                start()
            }

            override fun onTick(millisUntilFinished: Long) {
                loadMarkers()
            }
        }
    }

    override fun onActivityDetached() {
        cancelMarkerLoading()
    }

    fun onActivityVisible() {
        val storedBus = BusDAO.get(preferences.getBusId())
        if (selectedBus?.id != storedBus?.id) {
            selectedBus = storedBus
            activity.setToolbarTitle(selectedBus?.name)
            activity.clearMarker()
            markerMap.clear()
        }
        timer?.start()
    }

    fun onActivityNotVisible() {
        cancelMarkerLoading()
    }

    fun onSelectBusClicked() {
        activity.startActivity(activity.intentFor<BusListActivity>())
    }

    fun onMapLoaded(googleMap: GoogleMap) {
        val lvivCenter = LatLng(49.842465, 24.026625)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lvivCenter, 12f))
    }

    @UiThread
    private fun displayMarkers(markerList: List<BusMarker>) {
        L.v("Displaying markers: ${markerList.size}")
        markerList.forEach {
            val storedMarker: Marker? = markerMap.get(it.vehicleId)
            if (storedMarker == null) {
                val position = LatLng(it.lat.toDouble(), it.lon.toDouble())
                val marker = activity.addMarker(position)
                markerMap.put(it.vehicleId, marker)
            } else {
                val position = LatLng(it.lat.toDouble(), it.lon.toDouble())
                storedMarker.position = position
            }
        }
    }

    private fun loadMarkers() {
        selectedBus?.let {
            task = object : AsyncTask<Bus, Void, List<BusMarker>>() {

                override fun doInBackground(vararg bus: Bus) = BusAPI().getBusLocation(bus[0].code).map { BusMarker(it) }

                override fun onPostExecute(result: List<BusMarker>) {
                    displayMarkers(result)
                }
            }

            task?.execute(it)
        }
    }

    private fun cancelMarkerLoading() {
        timer?.cancel()
        task?.cancel(false)
    }

}