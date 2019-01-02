package org.mathana.hillfort.views.hillfortlist

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

class HillfortListPresenter(view: BaseView): BasePresenter(view) {

  var current_user = UserModel()

  init {
    app = view.application as MainApp
    if (view.intent.hasExtra("current_user")) {
      current_user = view.intent.extras.getParcelable<UserModel>("current_user")
    }

  }

  fun loadHillforts() = view?.showHillforts(app.users.findAllHillforts(current_user))

//  fun doAddHillfort() { view.startActivityForResult(view.intentFor<HillfortView>().putExtra("current_user", current_user), 0) }
  fun doAddHillfort() { view?.navigateTo(VIEW.HILLFORT, 0, "current_user", current_user) }

//  fun doShowHillfortMap() { view.startActivity<HillfortsMapView>() }
  fun doShowHillfortMap() { view?.navigateTo(VIEW.MAPS) }

//  fun doSettings() { view.startActivityForResult(view.intentFor<SettingsActivity>().putExtra("current_user", current_user), 0) }
  fun doSettings() { view?.navigateTo(VIEW.SETTINGS, 0, "current_user", current_user) }

//  fun doEditHillfort(hillfort: HillfortModel) { view. startActivityForResult(view.intentFor<HillfortView>().putExtra("hillfort_edit", hillfort).putExtra("current_user", current_user), 0)}
  fun doEditHillfort(hillfort: HillfortModel) { view?.navigateToHillfort(VIEW.HILLFORT, 0, "current_user", current_user, "hillfort_edit", hillfort) }

}