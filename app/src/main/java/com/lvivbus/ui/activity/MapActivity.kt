package com.lvivbus.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.lvivbus.R
import kotlinx.android.synthetic.main.map_activity.*
import org.jetbrains.anko.intentFor

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_activity)

        setSupportActionBar(toolbar);
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        toolbar.inflateMenu(R.menu.map)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter -> startActivity(intentFor<BusListActivity>())
        }

        return super.onOptionsItemSelected(item)
    }
}