package org.mathana.hillfort.views.hillfort

import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.activity_hillfort.*

import org.jetbrains.anko.intentFor
import org.mathana.hillfort.helpers.showImagePicker
import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.models.Location
import org.mathana.hillfort.models.UserModel
import org.mathana.hillfort.views.BasePresenter
import org.mathana.hillfort.views.IMAGE_REQUEST
import org.mathana.hillfort.views.LOCATION_REQUEST
import org.mathana.hillfort.views.editlocation.EditLocationView
import java.text.SimpleDateFormat
import java.util.*


class HillfortPresenter(view: HillfortView): BasePresenter(view) {


  var hillfort = HillfortModel()
  var current_user = UserModel()


  var edit = false


  init {
    app = view.application as MainApp
    if (view.intent.hasExtra("current_user")) {
      current_user = view.intent.extras.getParcelable<UserModel>("current_user")
      view.showHillfort(hillfort)
      view.btnDelete.visibility = View.INVISIBLE

    }
    if (view.intent.hasExtra("hillfort_edit") && view.intent.hasExtra("current_user")) {
      edit = true
      current_user = view.intent.extras.getParcelable<UserModel>("current_user")
      hillfort = view.intent.extras.getParcelable<HillfortModel>("hillfort_edit")
      view.showHillfort(hillfort)
      view.btnDelete.visibility = View.VISIBLE
    }

  }

  fun doAddOrSave(title: String, description: String, notes: String) {
    hillfort.title = title
    hillfort.description = description
    hillfort.notes = notes

    if (edit) {
      app.users.updateHillfort(current_user, hillfort)
    } else {
      app.users.createHillfort(current_user, hillfort)
    }
    view?.finish()
  }

  fun doCancel() {
    view?.finish()
  }

  fun doDelete() {
    app.users.deleteHillfort(current_user, hillfort)
    view?.finish()
  }

  fun doSelectImage() {
    showImagePicker(view!!, IMAGE_REQUEST)
  }

  fun doSetLocation() {
    val location = Location(52.245696, -7.139102, 15f)
    if (hillfort.zoom != 0f) {
      location.lat =  hillfort.lat
      location.lng = hillfort.lng
      location.zoom = hillfort.zoom
    }
    view?.startActivityForResult(view?.intentFor<EditLocationView>()?.putExtra("location", location), LOCATION_REQUEST)
  }

  override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          hillfort.images.add(data.data.toString())
          view?.showImages(hillfort)
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          val location = data.extras.getParcelable<Location>("location")
          hillfort.lat = location.lat
          hillfort.lng = location.lng
          hillfort.zoom = location.zoom
        }
      }
    }
  }

  fun doOnCheckBoxClicked(checked: Boolean) {
    if (checked) {
      val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
      val currentDate = sdf.format(Date())
      hillfort.explored = true
      hillfort.date = currentDate
    }
    if (!checked) {
      hillfort.explored = false
      hillfort.date = ""
    }
  }

}