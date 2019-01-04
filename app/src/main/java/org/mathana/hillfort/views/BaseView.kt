package org.mathana.hillfort.views

import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.jetbrains.anko.AnkoLogger
import org.mathana.hillfort.activities.HillfortsMapView
import org.mathana.hillfort.activities.LoginView

import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.views.editlocation.EditLocationView
import org.mathana.hillfort.views.hillfort.HillfortView
import org.mathana.hillfort.views.hillfortlist.HillfortListPresenter
import org.mathana.hillfort.views.hillfortlist.HillfortListView

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2

enum class VIEW {
  LOCATION, HILLFORT, MAPS, LIST, SETTINGS, LOGIN
}

open abstract class BaseView() : AppCompatActivity(), AnkoLogger {

  var basePresenter: BasePresenter? = null

  fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
    var intent = Intent(this, HillfortListView::class.java)
    when (view) {
      VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
      VIEW.HILLFORT -> intent = Intent(this, HillfortView::class.java)
      VIEW.MAPS -> intent = Intent(this, HillfortsMapView::class.java)
      VIEW.LIST -> intent = Intent(this, HillfortListView::class.java)
//      VIEW.SETTINGS -> intent = Intent(this, SettingsActivity::class.java)
      VIEW.LOGIN -> intent = Intent(this, LoginView::class.java)
    }
    if (key != "") {
      intent.putExtra(key, value)
    }
    startActivityForResult(intent, code)
  }

  fun navigateToHillfort(view: VIEW, code: Int = 0, key1: String = "", value1: Parcelable? = null,
                         key2: String = "", value2: Parcelable? = null) {
    var intent = Intent(this, HillfortListView::class.java)
    when (view) {
      VIEW.HILLFORT -> intent = Intent(this, HillfortView::class.java)
    }
    if (key1 != "" && key2 != "") {
      intent.putExtra(key1, value1).putExtra(key2, value2)
    }
    startActivityForResult(intent, code)
  }

  fun initPresenter(presenter: BasePresenter): BasePresenter {
    basePresenter = presenter
    return presenter
  }

  fun init(toolbar: Toolbar, upEnabled: Boolean) {
    toolbar.title = title
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
  }

  override fun onDestroy() {
    basePresenter?.onDestroy()
    super.onDestroy()
  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      basePresenter?.doActivityResult(requestCode, resultCode, data)
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  open fun showHillfort(hillfort: HillfortModel) {}
  open fun showHillforts(hillforts: List<HillfortModel>) {}
  open fun showProgress() {}
  open fun hideProgress() {}

  open fun showImages (hillfort: HillfortModel) { }

}