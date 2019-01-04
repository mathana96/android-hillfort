package org.mathana.hillfort.views.settings

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.*
import org.mathana.hillfort.views.BasePresenter
import org.mathana.hillfort.views.VIEW

class SettingsPresenter(view: SettingsView): BasePresenter(view), AnkoLogger {

  val user = FirebaseAuth.getInstance().currentUser

  fun doSetEmail() {

    view!!.settings_username.setText(user!!.email)
  }

  fun doSaveSettings() {
    info ("Saved settings clicked")

    user!!.updateEmail(view!!.settings_username.text.toString()).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        view!!.toast("Details updated")
      } else {
        println("Error Update")
        view!!.toast("Error Update")
      }
    }
    view?.navigateTo(VIEW.LIST)
  }

  fun doDeleteAccount() {
    info ("Saved settings clicked")
    val db = FirebaseDatabase.getInstance()
    info("UID: ${user!!.uid}")
    db.getReference().child("users").child(user!!.uid).removeValue().addOnCompleteListener { task ->
      if (task.isSuccessful) {
        info("DELETED FROM DB")
        user!!.delete().addOnCompleteListener { task ->
          if (task.isSuccessful) {
            view?.navigateTo(VIEW.LOGIN)
            view?.finish()
            app.hillforts.clear()
            view!!.toast("Account Deleted")
          } else {
            info("Error Deleting")
            view!!.toast("Error Deleting")
          }
        }

      } else {
        info("Error Deleting")
        view!!.toast("Error Deleting")
      }

    }

  }


}