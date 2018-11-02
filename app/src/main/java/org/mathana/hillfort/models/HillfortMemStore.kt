package org.mathana.hillfort.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
  return lastId++
}

class HillfortMemStore : HillfortStore, AnkoLogger {

  val hillforts = ArrayList<HillfortModel>()

  override fun findAll(): List<HillfortModel> {
    return hillforts
  }

  override fun create(hillfort: HillfortModel) {
    hillfort.id = getId()
    hillforts.add(hillfort)
    logAll()
  }

  override fun update(hillfort: HillfortModel) {
    var foundhillfort: HillfortModel? = hillforts.find { h -> h.id == hillfort.id }
    if (foundhillfort != null) {
      foundhillfort.title = hillfort.title
      foundhillfort.description = hillfort.description
      foundhillfort.image = hillfort.image
      logAll()
    }
  }

  fun logAll() {
    hillforts.forEach { info("${it}") }
  }
}