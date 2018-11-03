package org.mathana.hillfort.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.mathana.hillfort.R
import org.mathana.hillfort.main.MainApp
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.models.UserModel

class LoginActivity: AppCompatActivity(), AnkoLogger {


  lateinit var app : MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    app = application as MainApp


    btn_login.setOnClickListener {
      info ("Login button clicked")

      val username = login_username.text.toString()
      val password = login_password.text.toString()

      if (username.isNotEmpty() && password.isNotEmpty()) {
        val allUsers: List<UserModel> = app.users.findAll()

        var foundUser: UserModel? = allUsers.find { user -> user.username == username && user.password == password }

        if (foundUser != null) {
          startActivity<HillfortListActivity>()
          info ("Login worked! $foundUser")
        } else {
          toast("Incorrect username or password")
        }

      } else {
        toast("Please enter both username and password")
      }

    }

    btn_goto_signup.setOnClickListener {
      info ("Clicked goto signup")
      startActivity<SignupActivity>()
    }

  }


}