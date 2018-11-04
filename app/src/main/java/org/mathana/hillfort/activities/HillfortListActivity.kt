package org.mathana.hillfort.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.mathana.hillfort.R
import org.mathana.hillfort.R.id.info
import org.mathana.hillfort.R.id.recyclerView
import org.mathana.hillfort.R.string.checkbox_explored
import org.mathana.hillfort.adapters.HillfortAdapter
import org.mathana.hillfort.adapters.HillfortListener
import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.models.UserModel

class HillfortListActivity: AppCompatActivity(), HillfortListener, AnkoLogger {

  lateinit var app: MainApp

  var current_user = UserModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort_list)
    app = application as MainApp

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager

    if (intent.hasExtra("current_user")) {
      current_user = intent.extras.getParcelable<UserModel>("current_user")
      info("This is the logged in user: $current_user")
      loadHillforts()
    }


    toolbarMain.title = title
    setSupportActionBar(toolbarMain)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_add -> startActivityForResult(intentFor<HillfortActivity>().putExtra("current_user", current_user), 0)
      R.id.item_settings -> startActivityForResult(intentFor<SettingsActivity>().putExtra("current_user", current_user), 0)

    }
    return super.onOptionsItemSelected(item)
  }

  override fun onHillfortClick(hillfort: HillfortModel) {
    startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort).putExtra("current_user", current_user), 0)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    loadHillforts()
    super.onActivityResult(requestCode, resultCode, data)
  }

  private fun loadHillforts() {
    showHillforts(app.users.findAllHillforts(current_user))
  }

  fun showHillforts (hillforts: List<HillfortModel>) {
    recyclerView.adapter = HillfortAdapter(hillforts, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

}
