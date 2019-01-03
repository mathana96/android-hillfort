package org.mathana.hillfort.views.hillfortlist

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.*
import org.mathana.hillfort.R
import org.mathana.hillfort.adapters.HillfortAdapter
import org.mathana.hillfort.adapters.HillfortListener
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.views.BaseView


class HillfortListView: BaseView(), HillfortListener, AnkoLogger {

  lateinit var presenter: HillfortListPresenter


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort_list)
    init(toolbarMain, false)

    presenter = initPresenter(HillfortListPresenter(this)) as HillfortListPresenter


    val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager

    presenter.loadHillforts()

  }

  override fun showHillforts(hillforts: List<HillfortModel>) {
    recyclerView.adapter = HillfortAdapter(hillforts, this)
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
    presenter.loadHillforts()
    super.onActivityResult(requestCode, resultCode, data)
  }


}
