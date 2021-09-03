package com.android.iceprice.shopfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.iceprice.R
import com.android.iceprice.loadImage
import com.android.iceprice.shopfragment.model.ShopItem

class PreviewShopAdapter(
    context: Context,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<PreviewShopAdapter.PreviewHolder>() {
    private var shopsList: List<ShopItem> = emptyList()

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewHolder {
        return PreviewHolder(inflater.inflate(R.layout.preview_item, parent, false))
    }

    override fun onBindViewHolder(holder: PreviewHolder, position: Int) {
        holder.bind(shopsList.get(position))
    }

    override fun getItemCount(): Int = shopsList.size

    inner class PreviewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val discountTitle: TextView = item.findViewById(R.id.discountTitle)
        val shopTitle: TextView = item.findViewById(R.id.shopTitle)
        val imagePicShop: ImageView = item.findViewById(R.id.picPreview)


        fun bind(previewShop: ShopItem) {
            discountTitle.setText(previewShop.title)
            shopTitle.setText(previewShop.shop)
            imagePicShop.loadImage(previewShop.image.toString())

            itemView.setOnClickListener { onItemClick.invoke(adapterPosition) }
        }
    }

    fun updates(shopsList: List<ShopItem>) {
        this.shopsList = shopsList
        notifyDataSetChanged()
    }
}