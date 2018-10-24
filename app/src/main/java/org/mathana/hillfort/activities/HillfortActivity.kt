package org.mathana.hillfort.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.mathana.hillfort.R
import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.HillfortModel

class HillfortActivity : AppCompatActivity(), AnkoLogger {

  var hillfort = HillfortModel()
  lateinit var app : MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)
    app = application as MainApp

    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)

    btnAdd.setOnClickListener {
      hillfort.title = hillfortTitle.text.toString()
      hillfort.description = hillfortDescription.text.toString()

      if (hillfort.title.isNotEmpty() && hillfort.description.isNotEmpty()) {
        app.hillforts.add(hillfort.copy())
        info("Add Button Pressed: $hillfort")
        app.hillforts.forEach{ info("add button pressed: ${it.title} ${it.description}")}
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      }
      else {
        val toastTitleDesc: String = getString(R.string.toast_enterTitleandDesc)
        toast (toastTitleDesc)
      }
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
}
