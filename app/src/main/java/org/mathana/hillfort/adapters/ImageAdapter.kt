/**
 * Adapter from: https://www.raywenderlich.com/155-android-listview-tutorial-with-kotlin
 * https://www.raywenderlich.com/367-android-recyclerview-tutorial-with-kotlin
 * https://code.tutsplus.com/tutorials/code-an-image-gallery-android-app-with-picasso--cms-30966
 *
 */

package org.mathana.hillfort.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso
import org.mathana.hillfort.R



class ImageAdapter(private val images: ArrayList<String>,
                   private val context: Context) : BaseAdapter() {

  private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

  override fun getCount(): Int {
    return images.size
  }
  override fun getItem(position: Int): Any {
    return images[position]
  }
  override fun getItemId(position: Int): Long {
    return position.toLong()
  }
  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val rowView = inflater.inflate(R.layout.hillfort_image, parent, false)
    val image = rowView.findViewById(R.id.hillfortImage) as ImageView

    val getImage = getItem(position) as String
    Picasso.get().load(getImage).placeholder(R.mipmap.ic_launcher).into(image)
    return rowView
  }
}
