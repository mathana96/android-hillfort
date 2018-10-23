package org.mathana.hillfort.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.mathana.hillfort.R
import org.mathana.hillfort.models.HillfortModel

class HillfortActivity : AppCompatActivity(), AnkoLogger {

  var hillfort = HillfortModel()
  val hillforts = ArrayList<HillfortModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)
    info("Hillfort activity started")

    btnAdd.setOnClickListener {
      hillfort.title = hillfortTitle.text.toString()
      hillfort.description = hillfortDescription.text.toString()

      if (hillfort.title.isNotEmpty() && hillfort.description.isNotEmpty()) {

        hillforts.add(hillfort.copy())
        info("Add Button Pressed: $hillfort")
        hillforts.forEach{ info("add button pressed: ${it.title} ${it.description}")}
      }
      else {

        val toastTitleDesc: String = getString(R.string.toast_enterTitleandDesc)
        toast (toastTitleDesc)
      }
    }

  }
}
