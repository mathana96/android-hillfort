package org.mathana.hillfort.models

interface HillfortStore {
  suspend fun findAll(): List<HillfortModel>
  suspend fun findById(id:Long) : HillfortModel?
  suspend fun create(placemark: HillfortModel)
  suspend fun update(placemark: HillfortModel)
  suspend fun delete(placemark: HillfortModel)
  fun clear()

}