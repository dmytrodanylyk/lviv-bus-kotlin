package com.lvivbus.ui.screen.map

import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lvivbus.R
import com.lvivbus.ui.screen.AbsActivity
import kotlinx.android.synthetic.main.map_activity.*

class MapActivity : AbsActivity<MapPresenter>() {

    private var googleMap: GoogleMap? = null

    override fun createPresenter() = MapPresenter(this)

    override fun initView() {
        setContentView(R.layout.map_activity)
        setSupportActionBar(toolbar)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { onMapLoaded(it) }
    }


    override fun onResume() {
        presenter.onActivityVisible()
        super.onResume()
    }

    override fun onPause() {
        presenter.onActivityNotVisible()
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        toolbar.inflateMenu(R.menu.map)
        return true
    }

    override fun onOptionsItemClicked(item: MenuItem) {
        when (item.itemId) {
            R.id.action_filter -> presenter.onSelectBusClicked()
        }
    }

    fun setToolbarTitle(title: String?) {
        toolbar.subtitle = title
    }


    fun addMarker(position: LatLng) = googleMap?.addMarker(MarkerOptions().position(position))

    fun clearMarker() {
        googleMap?.clear()
    }

    private fun onMapLoaded(map: GoogleMap) {
        googleMap = map
        presenter.onMapLoaded(map)
    }

}