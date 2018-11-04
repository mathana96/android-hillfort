package org.mathana.hillfort.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.*
import org.mathana.hillfort.R
import org.mathana.hillfort.R.id.settings_username

import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.UserModel

class SettingsActivity: AppCompatActivity(), AnkoLogger {


  lateinit var app : MainApp

  var current_user = UserModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)

    app = application as MainApp

    setSupportActionBar(toolbarSettings)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    if (intent.hasExtra("current_user")) {
      current_user = intent.extras.getParcelable<UserModel>("current_user")
      settings_username.setText(current_user.username)
      settings_password.setText(current_user.password)
      total_hillforts.setText(getTotalHillforts(current_user))
      hillforts_visited.setText(getHillfortsVisited(current_user))
      info("This is the logged in user: $current_user")

    }

    btn_save_settings.setOnClickListener {
      info ("Saved settings clicked")
      current_user.username = settings_username.text.toString()
      current_user.password = settings_password.text.toString()
      app.users.updateUser(current_user.copy())
      toast("Details updated")
      startActivityForResult(intentFor<HillfortListActivity>().putExtra("current_user", current_user), 0)
      finish()
    }

    btn_delete_account.setOnClickListener {
      info ("Delete button clicked")
      app.users.deleteUser(current_user.copy())
      finishAffinity();
      startActivity<LoginActivity>()
      finish()
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
      R.id.item_logout -> {
        info ("Logout button clicked")
        finishAffinity(); //https://stackoverflow.com/a/9580057/8083587
        startActivity<LoginActivity>()
        finish()
      }
      android.R.id.home -> {
        finish()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  fun getTotalHillforts(user: UserModel) : String {
    val foundUser: UserModel? = app.users.findAllUsers().find { u -> u.id == user.id }
    return foundUser!!.hillforts.size.toString()
  }

  fun getHillfortsVisited(user: UserModel) : String {
    val foundUser: UserModel? = app.users.findAllUsers().find { u -> u.id == user.id }
    var count = 0
    for (hillfort in foundUser!!.hillforts) {
      if (hillfort.explored == true)
        count ++
    }
    info("COUNT: $count")
    return count.toString()
  }

}