package org.mathana.hillfort.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.startActivityForResult
import org.mathana.hillfort.R
import org.mathana.hillfort.adapters.HillfortAdapter
import org.mathana.hillfort.main.MainApp

class HillfortListActivity: AppCompatActivity() {

  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort_list)
    app = application as MainApp

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = HillfortAdapter(app.hillforts)

    toolbarMain.title = title
    setSupportActionBar(toolbarMain)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_add -> startActivityForResult<HillfortActivity>(0)
    }
    return super.onOptionsItemSelected(item)
  }
}
