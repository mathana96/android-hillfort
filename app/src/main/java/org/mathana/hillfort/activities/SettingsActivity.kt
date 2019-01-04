package org.mathana.hillfort.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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


    btn_save_settings.setOnClickListener {
      info ("Saved settings clicked")

      user!!.updateEmail(settings_username.text.toString()).addOnCompleteListener { task ->
        if (task.isSuccessful) {
          toast("Details updated")
        } else {
          println("Error Update")
          toast("Error Update")
        }
      }
      startActivity(intentFor<HillfortListView>())
      finish()
    }

    btn_delete_account.setOnClickListener {
      user!!.delete().addOnCompleteListener { task ->
        if (task.isSuccessful) {
          toast("Account deleted")
        } else {
          println("Error Update")
          toast("Error Update")
        }
      }
      finishAffinity()
      startActivity<LoginView>()
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

      android.R.id.home -> {
        finish()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }


}