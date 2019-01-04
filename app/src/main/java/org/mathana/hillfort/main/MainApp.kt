package org.mathana.hillfort.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.mathana.hillfort.models.*
import org.mathana.hillfort.models.firebase.HillfortFireStore

class MainApp : Application(), AnkoLogger {

  lateinit var hillforts: HillfortStore

  override fun onCreate() {
    super.onCreate()
    hillforts = HillfortFireStore(applicationContext)
    info("Placemark started")
  }
}
