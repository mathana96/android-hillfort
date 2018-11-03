package org.mathana.hillfort.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ListView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.card_hillfort.view.*
import org.jetbrains.anko.*
import org.mathana.hillfort.R
import org.mathana.hillfort.R.id.*
import org.mathana.hillfort.adapters.HillfortAdapter
import org.mathana.hillfort.adapters.ImageAdapter
import org.mathana.hillfort.helpers.showImagePicker
import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.models.Location


class HillfortActivity : AppCompatActivity(), AnkoLogger {

  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2

  var hillfort = HillfortModel()
  lateinit var app : MainApp
  var edit = false

  private lateinit var listView : ListView

  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)
    app = application as MainApp

    listView = findViewById<ListView>(R.id.hillfortImages)
    showImages(hillfort)

    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    if (intent.hasExtra("hillfort_edit")) {
      edit = true
      hillfort = intent.extras.getParcelable<HillfortModel>("hillfort_edit")
      hillfortTitle.setText(hillfort.title)
      hillfortDescription.setText(hillfort.description)
      btnAdd.setText(R.string.button_saveHillfort)
      if (hillfort.images != null)
        chooseImage.setText(R.string.button_changeImage)

//      hillfortImages.adapter = ImageAdapter(hillfort.images, this)
      showImages(hillfort)
      checkBox.isChecked = hillfort.explored

    }

    btnAdd.setOnClickListener {
      hillfort.title = hillfortTitle.text.toString()
      hillfort.description = hillfortDescription.text.toString()
      hillfort.explored = checkBox.isChecked

      if (hillfort.title.isNotEmpty() && hillfort.description.isNotEmpty()) {
        if (edit) {
          app.hillforts.update(hillfort.copy())
        } else {
          app.hillforts.create(hillfort.copy())
        }
        info("add button pressed: ${hillfort.title} ${hillfort.description}")
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      }
      else {
        val toastTitleDesc: String = getString(R.string.toast_enterTitleandDesc)
        toast (toastTitleDesc)
      }
    }

    chooseImage.setOnClickListener {
      info ("Select image")
      showImagePicker(this, IMAGE_REQUEST)
    }

    hillfortLocation.setOnClickListener {
      info ("Set Location Pressed")
      val location = Location(52.245696, -7.139102, 15f)
      if (hillfort.zoom != 0f) {
        location.lat =  hillfort.lat
        location.lng = hillfort.lng
        location.zoom = hillfort.zoom
      }
      startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
    }

    btnDelete.setOnClickListener {
      app.hillforts.delete(hillfort.copy())
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }

  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          hillfort.images.add(data.data.toString())
//          hillfortImages.adapter = ImageAdapter(this, hillfort.images)
          showImages(hillfort)
          chooseImage.setText(R.string.button_changeImage)
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

  fun onCheckboxClicked(view: View) {
    if (view is CheckBox) {
      when (view.id) {
        R.id.checkBox -> {
          info ("YO YO YO $hillfort")
          hillfort.explored = checkBox.isChecked
        }
      }
    }
  }


  fun showImages (hillfort: HillfortModel) {
    val images: ArrayList<String> = hillfort.images
    listView.adapter = ImageAdapter(images, this)
  }

}
