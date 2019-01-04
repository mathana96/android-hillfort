package org.mathana.hillfort.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.*
import org.mathana.hillfort.R

import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.UserModel
import org.mathana.hillfort.views.hillfortlist.HillfortListView

class SettingsActivity: AppCompatActivity(), AnkoLogger {


  lateinit var app : MainApp


  val user = FirebaseAuth.getInstance().currentUser

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)

    setSupportActionBar(toolbarSettings)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)



    settings_username.setText(user!!.email)
//      settings_password.setText(current_user.password)
//      total_hillforts.setText(getTotalHillforts(current_user))
//      hillforts_visited.setText(getHillfortsVisited(current_user))
//      info("This is the logged in user: $current_user")


//    btn_save_settings.setOnClickListener {
//      info ("Saved settings clicked")
//      current_user.username = settings_username.text.toString()
//      current_user.password = settings_password.text.toString()
//      app.users.updateUser(current_user.copy())
//      toast("Details updated")
//      startActivityForResult(intentFor<HillfortListView>().putExtra("current_user", current_user), 0)
//      finish()
//    }
//
//    btn_delete_account.setOnClickListener {
//      info ("Delete button clicked")
//      app.users.deleteUser(current_user.copy())
//      finishAffinity();
//      startActivity<LoginView>()
//      finish()
//    }

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

//  fun getTotalHillforts(user: UserModel) : String {
//    val foundUser: UserModel? = app.users.findAllUsers().find { u -> u.id == user.id }
//    return foundUser!!.hillforts.size.toString()
//  }
//
//  fun getHillfortsVisited(user: UserModel) : String {
//    val foundUser: UserModel? = app.users.findAllUsers().find { u -> u.id == user.id }
//    var count = 0
//    for (hillfort in foundUser!!.hillforts) {
//      if (hillfort.explored == true)
//        count ++
//    }
//    info("COUNT: $count")
//    return count.toString()
//  }

}