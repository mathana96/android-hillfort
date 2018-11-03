package org.mathana.hillfort.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.mathana.hillfort.R
import org.mathana.hillfort.R.id.btn_logout
import org.mathana.hillfort.R.id.btn_save_settings

import org.mathana.hillfort.main.MainApp

class SettingsActivity: AppCompatActivity(), AnkoLogger {


  lateinit var app : MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)

    app = application as MainApp


    btn_logout.setOnClickListener {
      info ("Logout button clicked")
//      startActivity<LoginActivity>()
    }

    btn_save_settings.setOnClickListener {
      info ("Saved settings clicked")
    }

  }


}