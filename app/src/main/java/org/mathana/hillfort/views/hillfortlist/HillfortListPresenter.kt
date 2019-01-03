package org.mathana.hillfort.views.hillfortlist

import android.widget.CompoundButton
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.mathana.hillfort.activities.HillfortsMapView
import org.mathana.hillfort.activities.SettingsActivity
import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.models.UserModel
import org.mathana.hillfort.views.BasePresenter
import org.mathana.hillfort.views.BaseView
import org.mathana.hillfort.views.VIEW
import org.mathana.hillfort.views.hillfort.HillfortView

class HillfortListPresenter(view: BaseView): BasePresenter(view), AnkoLogger {

  var current_user = UserModel()

  init {
    app = view.application as MainApp
    if (view.intent.hasExtra("current_user")) {
      current_user = view.intent.extras.getParcelable<UserModel>("current_user")
    }

  }

  fun loadHillforts() = view?.showHillforts(app.users.findAllHillforts(current_user))

  fun doAddHillfort() { view?.navigateTo(VIEW.HILLFORT, 0, "current_user", current_user) }

  fun doShowHillfortMap() { view?.navigateTo(VIEW.MAPS) }

  fun doSettings() { view?.navigateTo(VIEW.SETTINGS, 0, "current_user", current_user) }

  fun doEditHillfort(hillfort: HillfortModel) { view?.navigateToHillfort(VIEW.HILLFORT, 0, "current_user", current_user, "hillfort_edit", hillfort) }

  fun doFavSwitch(buttonView: CompoundButton, isChecked: Boolean) {
    if (isChecked) {
      info("fav switch selected on")
      view?.showHillforts(app.users.findAllHillforts(current_user).filter { h -> h.fav == true })
    } else {
      info("fav switch selected off")
      loadHillforts()
    }
  }

}