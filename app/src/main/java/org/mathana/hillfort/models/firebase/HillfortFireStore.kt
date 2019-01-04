package org.mathana.hillfort.models.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.models.HillfortStore
import org.mathana.hillfort.views.VIEW

class HillfortFireStore(val context: Context) : HillfortStore, AnkoLogger {

  val hillforts = ArrayList<HillfortModel>()
  lateinit var userId: String
  lateinit var db: DatabaseReference

  suspend override fun findAll(): List<HillfortModel> {
    return hillforts
  }

  suspend override fun findById(id: Long): HillfortModel? {
    val foundhillfort: HillfortModel? = hillforts.find { p -> p.id == id }
    return foundhillfort
  }

  suspend override fun create(hillfort: HillfortModel){
    val key = db.child("users").child(userId).child("hillforts").push().key
    hillfort.fbId = key!!
    hillforts.add(hillfort)
    db.child("users").child(userId).child("hillforts").child(key).setValue(hillfort)
  }

  suspend override fun update(hillfort: HillfortModel) {
    var foundhillfort: HillfortModel? = hillforts.find { p -> p.fbId == hillfort.fbId }
    if (foundhillfort != null) {
      foundhillfort.title = hillfort.title
      foundhillfort.description = hillfort.description
      foundhillfort.images = hillfort.images
      foundhillfort.lat = hillfort.lat
      foundhillfort.lng = hillfort.lng
      foundhillfort.zoom = hillfort.zoom
      foundhillfort.rate = hillfort.rate
      foundhillfort.explored = hillfort.explored
      foundhillfort.fav = hillfort.fav
      foundhillfort.date = hillfort.date
      foundhillfort.notes = hillfort.notes
    }

    db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
  }

  suspend override fun delete(hillfort: HillfortModel) {
    db.child("users").child(userId).child("hillforts").child(hillfort.fbId).removeValue()
    hillforts.remove(hillfort)
  }

  override fun clear() {
    hillforts.clear()
  }

  fun fetchHillforts(hillfortsReady: () -> Unit) {
    val valueEventListener = object : ValueEventListener {
      override fun onCancelled(error: DatabaseError) {
      }
      override fun onDataChange(dataSnapshot: DataSnapshot) {
        dataSnapshot.children.mapNotNullTo(hillforts) { it.getValue<HillfortModel>(HillfortModel::class.java) }
        hillfortsReady()
      }
    }
    userId = FirebaseAuth.getInstance().currentUser!!.uid
    db = FirebaseDatabase.getInstance().reference
    hillforts.clear()
    db.child("users").child(userId).child("hillforts").addListenerForSingleValueEvent(valueEventListener)
  }
}