package com.lvivbus.ui.activity

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import com.lvivbus.R
import com.lvivbus.extensions.setSearchListener
import com.lvivbus.model.db.BusDAO
import com.lvivbus.ui.adapter.BusListAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.bus_list_activity.*
import java.util.*

class BusListActivity : AppCompatActivity() {

    private var maxRecentCount: Int = 3
    private val realm: Realm = Realm.getDefaultInstance()
    private lateinit var adapter: BusListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bus_list_activity)

        setSupportActionBar(toolbar)
        initView()
        loadData()
    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_bus, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.inputType = InputType.TYPE_TEXT_FLAG_AUTO_CORRECT
        searchView.isFocusable = true
        searchView.isIconified = false
        searchView.requestFocusFromTouch()
        searchView.setSearchListener() {  adapter.filter(it) }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadData() {
        val busList = BusDAO.getAll(realm)
        var recentBusList = busList.filter { it.recentDate != null }.take(maxRecentCount)
        val allBusList = busList.subList(recentBusList.size, busList.size).sortedBy { it.name }

        adapter.setData(recentBusList, allBusList);
        adapter.notifyDataSetChanged()
    }

    private fun initView() {
        adapter = BusListAdapter(this) {
            BusDAO.setRecentDate(it.id, Date())
            finish()
        }

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}