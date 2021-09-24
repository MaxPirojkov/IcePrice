package com.android.iceprice.shopfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.android.iceprice.R
import com.android.iceprice.loadImage
import com.android.iceprice.shopfragment.model.ShopItem

/**
 * A fragment representing a list of Items.
 */
class DetailShopFragment : Fragment(R.layout.fragment_detail_shop) {

    private var shopItem: ShopItem? = null
    private var backButton: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shopItem = arguments?.getParcelable<ShopItem>(KEY_SHOP)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_shop, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shopItem?.let {
            view.findViewById<ImageView>(R.id.picShopDetail).loadImage(it.image)
            view.findViewById<TextView>(R.id.nameShop).setText(it.title)
            view.findViewById<TextView>(R.id.descripInfo).setText(it.body)
            view.findViewById<TextView>(R.id.titleShopName).setText(it.shop)
            view.findViewById<TextView>(R.id.titleAddressName).setText(it.address)
            view.findViewById<TextView>(R.id.numberPhone).setText(it.phone)
        }

        backButton = view.findViewById(R.id.backToListShop)
        backButton?.setOnClickListener() {
            requireActivity().supportFragmentManager
                .popBackStack()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(shopItem: ShopItem) =
            DetailShopFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_SHOP, shopItem)
                }
            }

        private const val KEY_SHOP = "key_shop"
    }

}
