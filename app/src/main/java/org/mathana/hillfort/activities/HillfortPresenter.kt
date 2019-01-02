package org.mathana.hillfort.activities

import android.content.Intent
import kotlinx.android.synthetic.main.activity_hillfort.*

import org.jetbrains.anko.intentFor
import org.mathana.hillfort.helpers.showImagePicker
import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.models.Location
import org.mathana.hillfort.models.UserModel
import java.text.SimpleDateFormat
import java.util.*


class HillfortPresenter(val activity: HillfortActivity) {

  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2

  var hillfort = HillfortModel()
  var current_user = UserModel()

  var app : MainApp

  var edit = false


  init {
    app = activity.application as MainApp
    if (activity.intent.hasExtra("current_user")) {
      current_user = activity.intent.extras.getParcelable<UserModel>("current_user")
    }
    if (activity.intent.hasExtra("hillfort_edit") && activity.intent.hasExtra("current_user")) {
      edit = true
      current_user = activity.intent.extras.getParcelable<UserModel>("current_user")
      hillfort = activity.intent.extras.getParcelable<HillfortModel>("hillfort_edit")
      activity.showHillfort(hillfort)
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
    activity.finish()
  }

  fun doCancel() {
    activity.finish()
  }

  fun doDelete() {
    app.users.deleteHillfort(current_user, hillfort)
    activity.finish()
  }

  fun doSelectImage() {
    showImagePicker(activity, IMAGE_REQUEST)
  }

  fun doSetLocation() {
    val location = Location(52.245696, -7.139102, 15f)
    if (hillfort.zoom != 0f) {
      location.lat =  hillfort.lat
      location.lng = hillfort.lng
      location.zoom = hillfort.zoom
    }
    activity.startActivityForResult(activity.intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
  }

  fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          hillfort.images.add(data.data.toString())

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
    } else {
      hillfort.explored = false
      hillfort.date = ""
    }

  }

}