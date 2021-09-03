package com.android.iceprice.giftfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.iceprice.R
import com.android.iceprice.giftfragment.model.GiftItem
import com.android.iceprice.loadImage


class DetailGiftFragment : Fragment() {

    private var giftItem: GiftItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        giftItem = arguments?.getParcelable(KEY_GIFT)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_gift, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        giftItem?.let {
            view.findViewById<ImageView>(R.id.picShopDetail).loadImage(it.image)
            view.findViewById<TextView>(R.id.nameShop).setText(it.title)
            view.findViewById<TextView>(R.id.descripInfo).setText(it.body)
            view.findViewById<TextView>(R.id.titleShopName).setText(it.shop)
            view.findViewById<TextView>(R.id.titleAddressName).setText(it.address)
            view.findViewById<TextView>(R.id.numberPhone).setText(it.phone)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(giftItem: GiftItem) =
            DetailGiftFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GIFT, giftItem)
                }
            }

        private const val KEY_GIFT = "key_gift"
    }

}
