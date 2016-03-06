package com.lvivbus.ui.screen

import android.os.Bundle

abstract class AbsPresenter {

    abstract fun onActivityAttached(savedInstanceState: Bundle?)
    abstract fun onActivityDetached()
}