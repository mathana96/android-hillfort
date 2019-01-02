package org.mathana.hillfort.activities

import android.support.v4.app.ActivityCompat.startActivityForResult
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.models.UserModel

class HillfortListPresenter(val activity: HillfortListActivity) {

  var app: MainApp
  var current_user = UserModel()

  init {
    app = activity.application as MainApp
    if (activity.intent.hasExtra("current_user")) {
      current_user = activity.intent.extras.getParcelable<UserModel>("current_user")
    }

  }

  fun getHillforts() = app.users.findAllHillforts(current_user)

  fun doAddHillfort() { activity.startActivityForResult(activity.intentFor<HillfortActivity>().putExtra("current_user", current_user), 0) }

  fun doShowHillfortMap() { activity.startActivity<HillfortsMapActivity>() }

  fun doSettings() { activity.startActivityForResult(activity.intentFor<SettingsActivity>().putExtra("current_user", current_user), 0) }

  fun doEditHillfort(hillfort: HillfortModel) { activity. startActivityForResult(activity.intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort).putExtra("current_user", current_user), 0)}

}