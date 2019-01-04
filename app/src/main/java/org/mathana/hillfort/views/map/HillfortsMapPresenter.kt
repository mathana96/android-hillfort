package org.mathana.hillfort.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.experimental.android.UI
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.views.BasePresenter
import org.mathana.hillfort.views.BaseView
import kotlinx.coroutines.experimental.async

class HillfortsMapPresenter(view: BaseView): BasePresenter(view) {


  fun doPopulateMap(map: GoogleMap, hillforts: List<HillfortModel>) {
    map.uiSettings.setZoomControlsEnabled(true)
    hillforts.forEach {
      val loc = LatLng(it.lat, it.lng)
      val options = MarkerOptions().title(it.title).position(loc)
      map.addMarker(options).tag = it
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
    }
  }

  fun doMarkerSelected(marker: Marker) {
    async(UI) {
      val hillfort = marker.tag as HillfortModel
      if (hillfort != null) view?.showHillfort(hillfort)
    }
  }

  fun loadPlacemarks() {
    async(UI) {
      view?.showHillforts(app.hillforts.findAll())
    }
  }

}