package org.mathana.hillfort.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.mathana.hillfort.models.HillfortMemStore
import org.mathana.hillfort.models.HillfortStore

class MainApp : Application(), AnkoLogger {

  lateinit var hillforts: HillfortStore

  override fun onCreate() {
    super.onCreate()
    hillforts = HillfortMemStore()
    info("Hillfort started")

  }
}
