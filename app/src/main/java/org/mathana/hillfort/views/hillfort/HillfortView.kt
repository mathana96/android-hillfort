package org.mathana.hillfort.views.hillfort

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
import org.mathana.hillfort.R.id.*
import org.mathana.hillfort.adapters.ImageAdapter
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.views.BaseView
import java.text.SimpleDateFormat
import java.util.*


class HillfortView : BaseView(), AnkoLogger {

  lateinit var presenter : HillfortPresenter
  var hillfort = HillfortModel()

  private lateinit var listView : ListView

  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)

    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    listView = findViewById(R.id.hillfortImages)

    presenter = HillfortPresenter(this)

    btnDelete.setOnClickListener { presenter.doDelete() }

    chooseImage.setOnClickListener { presenter.doSelectImage() }

    hillfortLocation.setOnClickListener { presenter.doSetLocation() }

  }

  override fun showHillfort(hillfort: HillfortModel) {

    hillfortTitle.setText(hillfort.title)
    hillfortDescription.setText(hillfort.description)
    dateExplored.setText(hillfort.date)
    addNotes.setText(hillfort.notes)

    if (hillfort.images.isNotEmpty()) {
      chooseImage.setText(R.string.button_changeImage)
      showImages(hillfort)
    }

    checkBox.isChecked = hillfort.explored

  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort, menu)
//    if (presenter.edit && menu != null) menu.getItem(0).setVisible(true)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_save -> {
        if (hillfortTitle.text.toString().isNotEmpty() && hillfortDescription.text.toString().isNotEmpty()) {
          presenter.doAddOrSave(hillfortTitle.text.toString(), hillfortDescription.text.toString(), addNotes.text.toString())
          info("add button pressed: ${hillfort.title} ${hillfort.description}")
        }
        else {
          val toastTitleDesc: String = getString(R.string.toast_enterTitleandDesc)
          toast (toastTitleDesc)
        }
      }
      R.id.item_cancel -> {
        presenter.doCancel()
      }
      android.R.id.home -> { //https://stackoverflow.com/a/32401235/8083587
        presenter.doCancel()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null)
      presenter.doActivityResult(requestCode, resultCode, data)
  }

  fun onCheckboxClicked(view: View) {
    if (view is CheckBox) {
      when (view.id) {
        R.id.checkBox -> {
          val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
          val currentDate = sdf.format(Date())

          if (checkBox.isChecked) {
            presenter.doOnCheckBoxClicked(true)
            dateExplored.setText(currentDate)
          }
          if (checkBox.isChecked.not()) {
            presenter.doOnCheckBoxClicked(false)
            dateExplored.setText("")
          }

        }
      }
    }
  }

  override fun showImages (hillfort: HillfortModel) {
    if (hillfort.images.isNotEmpty()) {
      val images: ArrayList<String> = hillfort.images
      listView.adapter = ImageAdapter(images, this)
    }

  }

}
