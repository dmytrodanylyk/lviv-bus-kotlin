package com.lvivbus.ui.screen.selectbus

import android.app.SearchManager
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import com.lvivbus.R
import com.lvivbus.extensions.setSearchListener
import com.lvivbus.ui.data.Bus
import com.lvivbus.ui.screen.AbsActivity
import com.lvivbus.ui.utils.DividerItemDecoration
import kotlinx.android.synthetic.main.bus_list_activity.*

class BusListActivity : AbsActivity<BusListPresenter>() {

    private val busAdapter by lazy {
        BusListAdapter(this) {
            presenter.onBusClicked(it)
        }
    }

    override fun createPresenter(): BusListPresenter = BusListPresenter(this)

    override fun initView() {
        setContentView(R.layout.bus_list_activity)
        setSupportActionBar(toolbar)

        recyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(createDecorator())
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = busAdapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_bus, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem.actionView as SearchView

        with(searchView) {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            inputType = InputType.TYPE_TEXT_FLAG_AUTO_CORRECT
            isFocusable = true
            isIconified = false
            requestFocusFromTouch()
            setSearchListener() { busAdapter.filter(it) }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemClicked(item: MenuItem) {
        when (item.itemId) {
            android.R.id.home -> {
                presenter.onToolbarBackClicked()
            }
        }
    }

    fun setDate(recentBusList: List<Bus>, allBusList: List<Bus>) {
        busAdapter.setData(recentBusList, allBusList);
        busAdapter.notifyDataSetChanged()
    }

    private fun createDecorator(): DividerItemDecoration {
        val decoration = DividerItemDecoration(applicationContext, R.drawable.divider)
        val leftPadding = resources.getDimension(R.dimen.decorator_left_padding).toInt()
        val rightPadding = resources.getDimension(R.dimen.decorator_right_padding).toInt()
        decoration.setPadding(leftPadding, rightPadding)
        return decoration
    }
}