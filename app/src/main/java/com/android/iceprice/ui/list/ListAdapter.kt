package com.android.iceprice.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.iceprice.R
import com.android.iceprice.extensions.loadImage
import com.android.iceprice.ui.DetailItem

class ListAdapter(
    context: Context,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ListAdapter.PreviewHolder>() {

    private var list: List<DetailItem> = emptyList()

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewHolder {
        return PreviewHolder(inflater.inflate(R.layout.preview_item, parent, false))
    }

    override fun onBindViewHolder(holder: PreviewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class PreviewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val discountTitle: TextView = item.findViewById(R.id.discountTitle)
        private val shopTitle: TextView = item.findViewById(R.id.shopTitle)
        private val imagePic: ImageView = item.findViewById(R.id.picPreview)

        fun bind(item: DetailItem) {
            discountTitle.text = item.title
            shopTitle.text = item.shopName
            imagePic.loadImage(item.image)
            itemView.setOnClickListener { onItemClick.invoke(adapterPosition) }
        }
    }

    fun updates(list: List<DetailItem>) {
        this.list = list
        notifyDataSetChanged()
    }
}