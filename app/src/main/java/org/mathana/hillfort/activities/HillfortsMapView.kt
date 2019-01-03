package org.mathana.hillfort.activities

import android.os.Bundle
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.mathana.hillfort.R

import kotlinx.android.synthetic.main.activity_hillforts_map.*
import kotlinx.android.synthetic.main.content_hillforts_map.*
import org.mathana.hillfort.R.id.*
import org.mathana.hillfort.helpers.readImageFromPath
import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.views.BaseView

class HillfortsMapView : BaseView(), GoogleMap.OnMarkerClickListener {

  lateinit var map: GoogleMap
  lateinit var presenter: HillfortsMapPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillforts_map)


    presenter = HillfortsMapPresenter(this)

    super.init(toolbarMaps, false)

    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync {
      map = it
      map.setOnMarkerClickListener(this)
      presenter.loadHillforts()

    }
  }

  override fun showHillfort(hillfort: HillfortModel) {
    currentTitle.text = hillfort!!.title
    currentDescription.text = hillfort!!.description
    if (hillfort.images.size > 0)
      imageView.setImageBitmap(readImageFromPath(this, hillfort.images.get(0)))
  }

  override fun showHillforts(hillforts: List<HillfortModel>) {
    presenter.doPopulateMap(map, hillforts)
  }

  override fun onMarkerClick(marker: Marker): Boolean {
    presenter.doMarkerSelected(marker)
    return true
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }

}
