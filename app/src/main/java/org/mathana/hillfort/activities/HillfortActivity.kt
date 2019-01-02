package org.mathana.hillfort.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.*
import org.mathana.hillfort.R
import org.mathana.hillfort.adapters.ImageAdapter
import org.mathana.hillfort.helpers.showImagePicker
import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.models.Location
import org.mathana.hillfort.models.UserModel
import java.nio.file.Files.delete
import java.text.SimpleDateFormat
import java.util.*


class HillfortActivity : AppCompatActivity(), AnkoLogger {

  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2

  var hillfort = HillfortModel()
  var current_user = UserModel()

  lateinit var app : MainApp

  var edit = false

  private lateinit var listView : ListView

  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)
    app = application as MainApp

    listView = findViewById(R.id.hillfortImages)
    showImages(hillfort)

    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    if (intent.hasExtra("current_user")) {
      current_user = intent.extras.getParcelable<UserModel>("current_user")
    }

    if (intent.hasExtra("hillfort_edit") && intent.hasExtra("current_user")) {
      edit = true
      current_user = intent.extras.getParcelable<UserModel>("current_user")
      hillfort = intent.extras.getParcelable<HillfortModel>("hillfort_edit")
      hillfortTitle.setText(hillfort.title)
      hillfortDescription.setText(hillfort.description)
      dateExplored.setText(hillfort.date)
      addNotes.setText(hillfort.notes)

      btnAdd.setText(R.string.button_saveHillfort)



      if (hillfort.images.isNotEmpty())
        chooseImage.setText(R.string.button_changeImage)

      showImages(hillfort)
      checkBox.isChecked = hillfort.explored

    }

    btnAdd.setOnClickListener {
      hillfort.title = hillfortTitle.text.toString()
      hillfort.description = hillfortDescription.text.toString()
      hillfort.notes = addNotes.text.toString()


      if (hillfort.title.isNotEmpty() && hillfort.description.isNotEmpty()) {
        if (edit) {
          app.users.updateHillfort(current_user, hillfort.copy())
          info("CURRENT USER: $current_user")
        } else {
          app.users.createHillfort(current_user, hillfort.copy())

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

//    btnDelete.setOnClickListener {
//      app.users.deleteHillfort(current_user, hillfort.copy())
//      setResult(AppCompatActivity.RESULT_OK)
//      finish()
//    }

  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort, menu)
    if (edit && menu != null) menu.getItem(0).setVisible(true)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_delete -> {
        app.users.deleteHillfort(current_user, hillfort.copy())
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      }
      R.id.item_cancel -> {
        finish()
      }
      android.R.id.home -> { //https://stackoverflow.com/a/32401235/8083587
        finish()
        return true
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
          val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
          val currentDate = sdf.format(Date())
          if (checkBox.isChecked) {
            hillfort.explored = true
            hillfort.date = currentDate

          }

          if (checkBox.isChecked.not()) {
            hillfort.explored = false
            hillfort.date = ""
          }
          dateExplored.setText(hillfort.date)
        }
      }
    }
  }


  fun showImages (hillfort: HillfortModel) {
    val images: ArrayList<String> = hillfort.images
    listView.adapter = ImageAdapter(images, this)
  }

}
