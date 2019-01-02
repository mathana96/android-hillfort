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
import org.mathana.hillfort.helpers.readImageFromPath
import org.mathana.hillfort.main.MainApp

class HillfortsMapActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

  lateinit var map: GoogleMap
  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillforts_map)
    setSupportActionBar(toolbarMaps)
    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync {
      map = it
      configureMap()
    }

    app = application as MainApp

  }

  override fun onMarkerClick(marker: Marker): Boolean {
    val tag = marker.tag as Long
    val hillfort = app.users.findById(tag)
    currentTitle.text = hillfort!!.title
    currentDescription.text = hillfort!!.description
    if (hillfort.images.size > 0)
      imageView.setImageBitmap(readImageFromPath(this, hillfort.images.get(0)))
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

  fun configureMap() {
    map.uiSettings.setZoomControlsEnabled(true)
    map.setOnMarkerClickListener(this)
    app.users.findAllUsers().forEach { user ->
      user.hillforts.forEach {
        val loc = LatLng(it.lat, it.lng)
        val options = MarkerOptions().title(it.title).position(loc)
        map.addMarker(options).tag = it.id
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
      }
    }
  }
}
