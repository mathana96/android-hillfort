package org.mathana.hillfort.views.hillfortlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_hillfort.view.*
import org.mathana.hillfort.models.HillfortModel
import org.mathana.hillfort.R

interface HillfortListener {
  fun onHillfortClick(hillfort: HillfortModel)
}

class HillfortAdapter constructor(private var hillforts: List<HillfortModel>,
                                  private val listener: HillfortListener) : androidx.recyclerview.widget.RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_hillfort, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val hillfort = hillforts[holder.adapterPosition]
    holder.bind(hillfort, listener)
  }

  override fun getItemCount(): Int = hillforts.size

  class MainHolder constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    fun bind(hillfort: HillfortModel, listener: HillfortListener) {
      itemView.hillfortTitle.text = hillfort.title
      itemView.description.text = hillfort.description
      if (hillfort.explored == true)
        itemView.explored.text = "Explored!"
      else
        itemView.explored.text = ""

      itemView.setOnClickListener { listener.onHillfortClick(hillfort) }

      if (hillfort.images.size > 0) {
        Picasso.get()
            .load(hillfort.images[0])
            .resize(750, 750)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .centerCrop()
            .into(itemView.imageIcon)
      }
    }
  }
}