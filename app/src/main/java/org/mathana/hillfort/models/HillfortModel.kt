package org.mathana.hillfort.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HillfortModel(var id: Long = 0,
                          var title: String = "",
                          var description: String = "",
                          var images: ArrayList<String> = ArrayList(),
                          var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f,
                          var explored: Boolean = false,
                          var fav: Boolean = false,
                          var date: String = "",
                          var notes: String = "") : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable