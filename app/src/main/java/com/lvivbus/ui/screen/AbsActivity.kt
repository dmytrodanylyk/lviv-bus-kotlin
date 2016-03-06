package com.lvivbus.ui.screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

abstract class AbsActivity<T : AbsPresenter> : AppCompatActivity() {

    lateinit var presenter: T

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onOptionsItemClicked(item)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        presenter = createPresenter()
        presenter.onActivityAttached(savedInstanceState)
    }

    override fun onDestroy() {
        presenter.onActivityDetached()
        super.onDestroy()
    }

    abstract fun onOptionsItemClicked(item: MenuItem)
    abstract fun createPresenter(): T
    abstract fun initView()
}