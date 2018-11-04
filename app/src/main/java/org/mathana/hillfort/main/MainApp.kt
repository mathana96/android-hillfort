package org.mathana.hillfort.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.mathana.hillfort.models.*

class MainApp : Application(), AnkoLogger {

  lateinit var users: UserStore

  override fun onCreate() {
    super.onCreate()
    users = UserJSONStore(applicationContext)
    info("User started")

  }
}
