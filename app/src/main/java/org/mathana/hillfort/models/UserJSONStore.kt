//package org.mathana.hillfort.models
//
//import android.content.Context
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
//import com.google.gson.reflect.TypeToken
//import org.jetbrains.anko.AnkoLogger
//import org.mathana.hillfort.R.id.map
//import org.mathana.hillfort.helpers.exists
//import org.mathana.hillfort.helpers.read
//import org.mathana.hillfort.helpers.write
//import java.util.*
//
//val JSON_FILE_USER = "users.json"
//val gsonBuilderUser = GsonBuilder().setPrettyPrinting().create()
//val listTypeUser = object : TypeToken<ArrayList<UserModel>>() {}.type
//val listTypeHillfort = object : TypeToken<ArrayList<HillfortModel>>() {}.type
//
//fun generateRandomIdUser(): Long {
//  return Random().nextLong()
//}
//
//class UserJSONStore : UserStore, AnkoLogger {
//
//  val context: Context
//  var users = mutableListOf<UserModel>()
//
//
//  constructor (context: Context) {
//    this.context = context
//    if (exists(context, JSON_FILE_USER)) {
//      deserialize()
//    }
//  }
//
//  override fun findAllUsers(): MutableList<UserModel> {
//    return users
//  }
//
//  override fun createUser(user: UserModel) {
//    user.id = generateRandomIdUser()
//    users.add(user)
//    serialize()
//  }
//
//
//  override fun updateUser(user: UserModel) {
//    var founduser: UserModel? = users.find { u -> u.id == user.id }
//    if (founduser != null) {
//      founduser.username = user.username
//      founduser.password = user.password
//    }
//    serialize()
//  }
//
//  override fun deleteUser(user: UserModel) {
//    users.remove(user)
//    serialize()
//  }
//
//  override fun findAllHillforts(user: UserModel): List<HillfortModel> {
//    val founduser: UserModel? = users.find { u -> u.id == user.id }
//    return founduser!!.hillforts
//  }
//
//
//  override fun createHillfort(user: UserModel, hillfort: HillfortModel) {
//    var founduser = users.find { u -> u.id == user.id }
//    if (founduser != null) {
//      hillfort.id = generateRandomId()
//      founduser.hillforts.add(hillfort)
//      serialize()
//    }
//  }
//
//  override fun updateHillfort(user: UserModel, hillfort: HillfortModel) {
//    var founduser = users.find { u -> u.id == user.id }
//    var foundhillfort: HillfortModel? = founduser!!.hillforts.find { h -> h.id == hillfort.id }
//    if (foundhillfort != null) {
//      foundhillfort.title = hillfort.title
//      foundhillfort.description = hillfort.description
//      foundhillfort.images = hillfort.images
//      foundhillfort.lat = hillfort.lat
//      foundhillfort.lng = hillfort.lng
//      foundhillfort.zoom = hillfort.zoom
//      foundhillfort.rate = hillfort.rate
//      foundhillfort.explored = hillfort.explored
//      foundhillfort.fav = hillfort.fav
//      foundhillfort.date = hillfort.date
//      foundhillfort.notes = hillfort.notes
//    }
//    serialize()
//  }
//
//  override fun deleteHillfort(user: UserModel, hillfort: HillfortModel) {
//    var founduser = users.find { u -> u.id == user.id }
//    founduser!!.hillforts.remove(hillfort)
//    serialize()
//  }
//
//  override fun findById(id:Long) : HillfortModel? {
//    findAllUsers().forEach { user ->
//      user.hillforts.forEach {hillfort ->
//        if (hillfort.id == id) {
//          return hillfort
//        }
//      }
//    }
//    return null
//  }
//
//  private fun serialize() {
//    val jsonString = gsonBuilderUser.toJson(users)
//    write(context, JSON_FILE_USER, jsonString)
//  }
//
//  private fun deserialize() {
//    val jsonString = read(context, JSON_FILE_USER)
//    users = Gson().fromJson(jsonString, listTypeUser)
//  }
//}