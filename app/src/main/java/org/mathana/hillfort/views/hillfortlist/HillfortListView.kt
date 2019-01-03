package org.mathana.hillfort.views.hillfortlist

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onQueryTextListener
import org.mathana.hillfort.R
import org.mathana.hillfort.R.id.recyclerView
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


    switchFav.setOnCheckedChangeListener { buttonView, isChecked -> presenter.doFavSwitch(buttonView, isChecked) }

    btnAdd.setOnClickListener { presenter.doAddHillfort() }

    hillfortSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

      override fun onQueryTextChange(newText: String): Boolean {
        if (newText.isEmpty())
          presenter.loadHillforts()
        else
          presenter.doSearch(newText)
        return false
      }

      override fun onQueryTextSubmit(query: String): Boolean { return false }
    })
    
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
