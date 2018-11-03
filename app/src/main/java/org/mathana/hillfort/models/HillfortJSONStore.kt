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

val JSON_FILE = "hillforts.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<HillfortModel>>() {}.type

fun generateRandomId(): Long {
  return Random().nextLong()
}

class HillfortJSONStore : HillfortStore, AnkoLogger {

  val context: Context
  var hillforts = mutableListOf<HillfortModel>()

  constructor (context: Context) {
    this.context = context
    if (exists(context, JSON_FILE)) {
      deserialize()
    }
  }

  override fun findAll(): MutableList<HillfortModel> {
    return hillforts
  }

  override fun create(hillfort: HillfortModel) {
    hillfort.id = generateRandomId()
    hillforts.add(hillfort)
    serialize()
  }


  override fun update(hillfort: HillfortModel) {
    var foundhillfort: HillfortModel? = hillforts.find { h -> h.id == hillfort.id }
    if (foundhillfort != null) {
      foundhillfort.title = hillfort.title
      foundhillfort.description = hillfort.description
      foundhillfort.images = hillfort.images
      foundhillfort.lat = hillfort.lat
      foundhillfort.lng = hillfort.lng
      foundhillfort.zoom = hillfort.zoom
    }
    serialize()
  }

  override fun delete(hillfort: HillfortModel) {
    hillforts.remove(hillfort)
    serialize()
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(hillforts, listType)
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE)
    hillforts = Gson().fromJson(jsonString, listType)
  }
}