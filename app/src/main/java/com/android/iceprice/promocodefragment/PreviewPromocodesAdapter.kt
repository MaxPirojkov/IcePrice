package com.android.iceprice.promocodefragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.iceprice.R
import com.android.iceprice.loadImage
import com.android.iceprice.promocodefragment.model.PromocodeItem

class PreviewPromocodesAdapter(
    context: Context,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<PreviewPromocodesAdapter.PreviewHolder>() {
    private var promocodesList: List<PromocodeItem> = emptyList()

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewHolder {
        return PreviewHolder(inflater.inflate(R.layout.preview_item, parent, false))
    }

    override fun onBindViewHolder(holder: PreviewHolder, position: Int) {
        holder.bind(promocodesList.get(position))
    }

    override fun getItemCount(): Int = promocodesList.size


    inner class PreviewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val discountTitle: TextView = item.findViewById(R.id.discountTitle)
        val shopTitle: TextView = item.findViewById(R.id.shopTitle)
        val imagePic: ImageView = item.findViewById(R.id.picPreview)
//        val countEvents: TextView = item.findViewById(R.id.countDiscountNumb)

        fun bind(previewPromocode: PromocodeItem) {
            discountTitle.setText(previewPromocode.title)
            shopTitle.setText(previewPromocode.shop)
            imagePic.loadImage(previewPromocode.image.toString())
//            countEvents.setText(numbCount).toString()

            itemView.setOnClickListener { onItemClick.invoke(adapterPosition) }
        }
    }

    fun updates(promocodesList: List<PromocodeItem>) {
        this.promocodesList = promocodesList
        notifyDataSetChanged()
    }
}