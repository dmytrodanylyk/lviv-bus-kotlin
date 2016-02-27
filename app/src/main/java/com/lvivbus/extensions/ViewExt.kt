package com.lvivbus.extensions

import android.support.v7.widget.SearchView

fun SearchView.setSearchListener(body: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(text: String?): Boolean = true

        override fun onQueryTextChange(text: String): Boolean {
            body(text)
            return true
        }

    })
}

