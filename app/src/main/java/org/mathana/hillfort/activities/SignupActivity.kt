package org.mathana.hillfort.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.mathana.hillfort.R
import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.models.UserModel

class SignupActivity: AppCompatActivity(), AnkoLogger {


  lateinit var app : MainApp

  var newUser = UserModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_signup)

    app = application as MainApp


    btn_signup.setOnClickListener {
      info ("Login button clicked")

      val username = signup_username.text.toString()
      val password = signup_password.text.toString()

      val allUsers: List<UserModel> = app.users.findAll()

      var foundUser: UserModel? = allUsers.find { user -> user.username == username }

      if (foundUser == null) {
        newUser.username = username
        newUser.password = password

        if (newUser.username.isNotEmpty() && newUser.password.isNotEmpty()) {
          app.users.create(newUser.copy())

          startActivity<LoginActivity>()
          info ("Signup successful $")
        } else {
          toast("Please enter both username and password")
        }


      } else {
        toast("Please use a different username")
      }

//      info ("WOOP $allUsers")

    }

    btn_goto_login.setOnClickListener {
      info ("Clicked goto login")
      startActivity<LoginActivity>()
    }

  }


}