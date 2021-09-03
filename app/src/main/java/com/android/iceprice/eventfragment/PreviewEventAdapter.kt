package com.android.iceprice.giftfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.iceprice.R
import com.android.iceprice.giftfragment.model.EventItem
import com.android.iceprice.loadImage

class PreviewEventAdapter(
    context: Context,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<PreviewEventAdapter.PreviewHolder>() {
    private var eventsList: List<EventItem> = emptyList()

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewHolder {
        return PreviewHolder(inflater.inflate(R.layout.preview_item, parent, false))
    }

    override fun onBindViewHolder(holder: PreviewHolder, position: Int) {
        holder.bind(eventsList.get(position))
    }

    override fun getItemCount(): Int = eventsList.size


    inner class PreviewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val discountTitle: TextView = item.findViewById(R.id.discountTitle)
        val shopTitle: TextView = item.findViewById(R.id.shopTitle)
        val imagePic: ImageView = item.findViewById(R.id.picPreview)
//        val countEvents: TextView = item.findViewById(R.id.countDiscountNumb)

        fun bind(previewEvent: EventItem) {
            discountTitle.setText(previewEvent.title)
            shopTitle.setText(previewEvent.shop)
            imagePic.loadImage(previewEvent.image.toString())
//            countEvents.setText(numbCount).toString()

            itemView.setOnClickListener { onItemClick.invoke(adapterPosition) }
        }
    }

    fun updates(eventsList: List<EventItem>) {
        this.eventsList = eventsList
        notifyDataSetChanged()
    }
}