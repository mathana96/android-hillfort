package org.mathana.hillfort.views.hillfortlist

import android.widget.CompoundButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.mathana.hillfort.activities.HillfortsMapView

import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.models.UserModel
import org.mathana.hillfort.views.BasePresenter
import org.mathana.hillfort.views.BaseView
import org.mathana.hillfort.views.VIEW
import org.mathana.hillfort.views.hillfort.HillfortView
import java.util.Locale.filter

class HillfortListPresenter(view: BaseView): BasePresenter(view), AnkoLogger {

  fun loadHillforts() {
    async(UI) {
      view?.showHillforts(app.hillforts.findAll())
    }
  }

  fun doAddHillfort() {
    view?.navigateTo(VIEW.HILLFORT)
  }

  fun doShowHillfortMap() {
    view?.navigateTo(VIEW.MAPS)
  }

//  fun doSettings() { view?.navigateTo(VIEW.SETTINGS, 0, "current_user", current_user) }

  fun doEditHillfort(hillfort: HillfortModel) {
    view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
  }

  fun doFavSwitch(buttonView: CompoundButton, isChecked: Boolean) {
    if (isChecked) {
      info("fav switch selected on")
      async(UI) {
        view?.showHillforts(app.hillforts.findAll().filter { h -> h.fav == true })
      }

    } else {
      info("fav switch selected off")
      loadHillforts()
    }
  }

  fun doSearch(query: String) {
    async(UI) {
      view?.showHillforts(app.hillforts.findAll().filter { h -> h.title.contains(query) })
    }

  }

  fun doLogout() {
    FirebaseAuth.getInstance().signOut()
    app.hillforts.clear()
    view?.navigateTo(VIEW.LOGIN)
  }

}