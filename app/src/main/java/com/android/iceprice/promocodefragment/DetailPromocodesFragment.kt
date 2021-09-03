package com.android.iceprice.promocodefragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.iceprice.ClickCallBackVisible
import com.android.iceprice.R
import com.android.iceprice.loadImage
import com.android.iceprice.promocodefragment.model.PromocodeItem

class DetailPromocodesFragment : Fragment() {
    private var promocodesItem: PromocodeItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        promocodesItem = arguments?.getParcelable(KEY_PROMOCODE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_promocode, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        promocodesItem?.let {
            view.findViewById<ImageView>(R.id.picShopDetail).loadImage(it.image)
            view.findViewById<TextView>(R.id.nameShop).setText(it.title)
            view.findViewById<TextView>(R.id.descripInfo).setText(it.body)
            view.findViewById<TextView>(R.id.titleShopName).setText(it.shop)
            val text = view.findViewById<TextView>(R.id.webSite)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                text.setText(Html.fromHtml("<br><p>Открыть сайт</p>", Html.FROM_HTML_MODE_COMPACT));
            } else {
                text.setText(Html.fromHtml("<br><p>Сайт не доступен</p>"));
            }
        }

    }


    companion object {
        @JvmStatic
        fun newInstance(promocodeItem: PromocodeItem) =
            DetailPromocodesFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_PROMOCODE, promocodeItem)
                }
            }

        private const val KEY_PROMOCODE = "key_promocode"
    }
}