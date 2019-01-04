package org.mathana.hillfort.views.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.*
import org.mathana.hillfort.R

import org.mathana.hillfort.views.BaseView
import org.mathana.hillfort.views.settings.SettingsPresenter


class SettingsView: BaseView(), AnkoLogger {


  lateinit var presenter: SettingsPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)

    setSupportActionBar(toolbarSettings)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    presenter = initPresenter(SettingsPresenter(this)) as SettingsPresenter

    presenter.doSetEmail()

    btn_save_settings.setOnClickListener {
      presenter.doSaveSettings()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_settings, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_cancel -> {
        finish()
      }

      android.R.id.home -> {
        finish()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }


}