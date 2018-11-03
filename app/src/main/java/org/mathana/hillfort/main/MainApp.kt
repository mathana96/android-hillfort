package org.mathana.hillfort.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.mathana.hillfort.models.*

class MainApp : Application(), AnkoLogger {

  lateinit var hillforts: HillfortStore
  lateinit var users: UserStore

  override fun onCreate() {
    super.onCreate()
    hillforts = HillfortJSONStore(applicationContext)
    info("Hillfort started")

    users = UserJSONStore(applicationContext)
    info("User started")

//    users.create(UserModel(1, "abba", "cool"))
//    users.create(UserModel(2, "babba", "cool"))
//    users.create(UserModel(3, "cabba", "cool"))

  }
}
