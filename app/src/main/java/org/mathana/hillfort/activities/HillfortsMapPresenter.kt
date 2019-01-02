package org.mathana.hillfort.activities

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.mathana.hillfort.R.id.map
import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.views.BasePresenter
import org.mathana.hillfort.views.BaseView

class HillfortsMapPresenter(view: BaseView): BasePresenter(view) {


  fun doPopulateMap(map: GoogleMap, hillforts: List<HillfortModel>) {
    map.uiSettings.setZoomControlsEnabled(true)
    app.users.findAllUsers().forEach { user ->
      user.hillforts.forEach {
        val loc = LatLng(it.lat, it.lng)
        val options = MarkerOptions().title(it.title).position(loc)
        map.addMarker(options).tag = it.id
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
      }
    }
  }

  fun doMarkerSelected(marker: Marker) {
    val tag = marker.tag as Long
    val hillfort = app.users.findById(tag)
    if (hillfort != null) view?.showHillfort(hillfort)
  }

  fun loadHillforts() {
    app.users.findAllUsers().forEach { user ->
      view?.showHillforts(user.hillforts)
    }
  }

}