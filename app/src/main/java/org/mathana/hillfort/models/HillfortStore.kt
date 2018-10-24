package org.mathana.hillfort.models

interface HillfortStore {
  fun findAll(): List<HillfortModel>
  fun create(hillfort: HillfortModel)
  fun update(hillfort: HillfortModel)
}