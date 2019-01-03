package org.mathana.hillfort.views.hillfort

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_hillfort.*

import org.jetbrains.anko.intentFor
import org.mathana.hillfort.R.id.map
import org.mathana.hillfort.helpers.checkLocationPermissions
import org.mathana.hillfort.helpers.createDefaultLocationRequest
import org.mathana.hillfort.helpers.isPermissionGranted
import org.mathana.hillfort.helpers.showImagePicker
import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.models.Location
import org.mathana.hillfort.models.UserModel
import org.mathana.hillfort.views.BasePresenter
import org.mathana.hillfort.views.IMAGE_REQUEST
import org.mathana.hillfort.views.LOCATION_REQUEST
import org.mathana.hillfort.views.VIEW
import org.mathana.hillfort.views.editlocation.EditLocationView
import java.text.SimpleDateFormat
import java.util.*


class HillfortPresenter(view: HillfortView): BasePresenter(view) {


  var hillfort = HillfortModel()
  var current_user = UserModel()

  var map: GoogleMap? = null
  var defaultLocation = Location(52.245696, -7.139102, 15f)

  var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)

  val locationRequest = createDefaultLocationRequest()

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
    else {
      if (checkLocationPermissions(view)) {
        doSetCurrentLocation()
      }
    }

  }

  @SuppressLint("MissingPermission")
  fun doResartLocationUpdates() {
    var locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult?) {
        if (locationResult != null && locationResult.locations != null) {
          val l = locationResult.locations.last()
          locationUpdate(l.latitude, l.longitude)
        }
      }
    }
    if (!edit) {
      locationService.requestLocationUpdates(locationRequest, locationCallback, null)
    }
  }
  override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    if (isPermissionGranted(requestCode, grantResults)) {
      doSetCurrentLocation()
    } else {
      locationUpdate(defaultLocation.lat, defaultLocation.lng)
    }
  }

  @SuppressLint("MissingPermission")
  fun doSetCurrentLocation() {
    locationService.lastLocation.addOnSuccessListener {
      locationUpdate(it.latitude, it.longitude)
    }
  }

  fun doConfigureMap(m: GoogleMap) {
    map = m
    locationUpdate(hillfort.lat, hillfort.lng)
  }

  fun locationUpdate(lat: Double, lng: Double) {
    hillfort.lat = lat
    hillfort.lng = lng
    hillfort.zoom = 15f
    map?.clear()
    map?.uiSettings?.setZoomControlsEnabled(true)
    val options = MarkerOptions().title(hillfort.title).position(LatLng(hillfort.lat, hillfort.lng))
    map?.addMarker(options)
    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(hillfort.lat, hillfort.lng), hillfort.zoom))
    view?.showHillfort(hillfort)
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
    view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(hillfort.lat, hillfort.lng, hillfort.zoom))
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
          locationUpdate(hillfort.lat, hillfort.lng)
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