package org.mathana.hillfort.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.mathana.hillfort.helpers.exists
import org.mathana.hillfort.helpers.read
import org.mathana.hillfort.helpers.write
import java.util.*

val JSON_FILE_USER = "users.json"
val gsonBuilderUser = GsonBuilder().setPrettyPrinting().create()
val listTypeUser = object : TypeToken<ArrayList<UserModel>>() {}.type

fun generateRandomIdUser(): Long {
  return Random().nextLong()
}

class UserJSONStore : UserStore, AnkoLogger {

  val context: Context
//  var users = mutableListOf<UserModel>()
  var users = mutableListOf<UserModel>()

  constructor (context: Context) {
    this.context = context
    if (exists(context, JSON_FILE_USER)) {
      deserialize()
    }
  }

  override fun findAll(): MutableList<UserModel> {
    return users
  }

  override fun create(user: UserModel) {
    user.id = generateRandomIdUser()
    users.add(user)
    serialize()
  }


  override fun update(user: UserModel) {
    var founduser: UserModel? = users.find { u -> u.id == user.id }
    if (founduser != null) {
      founduser.username = user.username
      founduser.password = user.password
    }
    serialize()
  }

  override fun delete(user: UserModel) {
    users.remove(user)
    serialize()
  }

  private fun serialize() {
    val jsonString = gsonBuilderUser.toJson(users, listTypeUser)
    write(context, JSON_FILE_USER, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE_USER)
    users = Gson().fromJson(jsonString, listTypeUser)
  }
}