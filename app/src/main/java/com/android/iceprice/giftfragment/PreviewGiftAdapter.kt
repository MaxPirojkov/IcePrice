package com.android.iceprice.giftfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.iceprice.R
import com.android.iceprice.giftfragment.model.GiftItem
import com.android.iceprice.loadImage

class PreviewGiftAdapter(
    context: Context,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<PreviewGiftAdapter.PreviewHolder>() {
    private var giftsList: List<GiftItem> = emptyList()

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewHolder {
        return PreviewHolder(inflater.inflate(R.layout.preview_item, parent, false))
    }

    override fun onBindViewHolder(holder: PreviewHolder, position: Int) {
        holder.bind(giftsList.get(position))
    }

    override fun getItemCount(): Int = giftsList.size

    inner class PreviewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val discountTitle: TextView = item.findViewById(R.id.discountTitle)
        val shopTitle: TextView = item.findViewById(R.id.shopTitle)
        val imagePic: ImageView = item.findViewById(R.id.picPreview)

        fun bind(previewGift: GiftItem) {
            discountTitle.setText(previewGift.title)
            shopTitle.setText(previewGift.shop)
            imagePic.loadImage(previewGift.image.toString())

            itemView.setOnClickListener { onItemClick.invoke(adapterPosition) }
        }
    }

    fun updates(giftsList: List<GiftItem>) {
        this.giftsList = giftsList
        notifyDataSetChanged()
    }
}