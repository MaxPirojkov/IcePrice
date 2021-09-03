package com.android.iceprice.giftfragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.android.iceprice.ClickCallBackVisible
import com.android.iceprice.ClickCallbackList
import com.android.iceprice.R
import com.android.iceprice.giftfragment.model.EventItem
import com.android.iceprice.loadImage


class DetailEventFragment : Fragment() {
    private var butBack: LinearLayout? = null

    private var eventItem: EventItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventItem = arguments?.getParcelable(KEY_EVENT)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_event, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventItem?.let {
            view.findViewById<ImageView>(R.id.picShopDetail).loadImage(it.image)
            view.findViewById<TextView>(R.id.nameShop).setText(it.title)
            view.findViewById<TextView>(R.id.descripInfo).setText(it.body)
            view.findViewById<TextView>(R.id.titleShopName).setText(it.shop)
            view.findViewById<TextView>(R.id.titleAddressName).setText(it.address)
            view.findViewById<TextView>(R.id.numberPhone).setText(it.phone)
            val text = view.findViewById<TextView>(R.id.webSite)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                text.setText(Html.fromHtml(it.website));
            } else {
                text.setText(Html.fromHtml("<br><p>Сайт не доступен</p>"));
            }
        }

    }


    companion object {
        @JvmStatic
        fun newInstance(eventItem: EventItem) =
            DetailEventFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_EVENT, eventItem)
                }
            }

        private const val KEY_EVENT = "key_event"
    }

}
