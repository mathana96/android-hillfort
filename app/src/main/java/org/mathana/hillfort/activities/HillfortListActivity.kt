package org.mathana.hillfort.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.*
import org.mathana.hillfort.R
import org.mathana.hillfort.adapters.HillfortAdapter
import org.mathana.hillfort.adapters.HillfortListener
import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.models.UserModel

class HillfortListActivity: AppCompatActivity(), HillfortListener, AnkoLogger {

  lateinit var presenter: HillfortListPresenter

  var current_user = UserModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort_list)
    toolbarMain.title = title
    setSupportActionBar(toolbarMain)

    presenter = HillfortListPresenter(this)
    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager

    recyclerView.adapter = HillfortAdapter(presenter.getHillforts(), this)
    recyclerView.adapter?.notifyDataSetChanged()


  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_add -> presenter.doAddHillfort()
      R.id.item_settings -> presenter.doSettings()
      R.id.item_map -> presenter.doShowHillfortMap()

    }
    return super.onOptionsItemSelected(item)
  }

  override fun onHillfortClick(hillfort: HillfortModel) {
    presenter.doEditHillfort(hillfort)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    recyclerView.adapter?.notifyDataSetChanged()
    super.onActivityResult(requestCode, resultCode, data)
  }


}
