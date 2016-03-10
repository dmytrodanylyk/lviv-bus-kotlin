package com.lvivbus.ui.screen.map

import android.os.Bundle
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
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MapPresenter(val activity: MapActivity) : AbsPresenter() {

    private var selectedBus: Bus? = null
    private var subscription: Subscription? = null
    private var markerMap: SparseArray<Marker> = SparseArray()

    private val preferences by lazy { BusPreferences(activity) }

    override fun onActivityAttached(savedInstanceState: Bundle?) {
    }

    override fun onActivityDetached() {
        subscription?.unsubscribe()
    }

    fun onActivityVisible() {
        val storedBus = BusDAO.get(preferences.getBusId())
        if (selectedBus?.id != storedBus?.id) {
            selectedBus = storedBus
            activity.setToolbarTitle(selectedBus?.name)
            activity.clearMarker()
            markerMap.clear()
        }
        loadMarkers()
    }

    fun onActivityNotVisible() {
        subscription?.unsubscribe()
    }

    fun onSelectBusClicked() {
        activity.startActivity(activity.intentFor<BusListActivity>())
    }

    fun onMapLoaded(googleMap: GoogleMap) {
        val lvivCenter = LatLng(49.842465, 24.026625)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lvivCenter, 12f))
    }

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
            subscription = Observable.interval(5, TimeUnit.SECONDS)
                    .startWith(0)
                    .map { BusAPI().getBusLocation(selectedBus?.code) }
                    .map { it.map { BusMarker(it) } }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe { displayMarkers(it) }
        }
    }
}