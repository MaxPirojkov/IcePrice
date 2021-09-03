package com.android.iceprice.shopfragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.iceprice.ClickCallBackVisible
import com.android.iceprice.ClickCallbackList
import com.android.iceprice.R
import com.android.iceprice.shopfragment.model.ShopItem

class ListShopFragment : Fragment() {

    private var list: List<ShopItem> = emptyList()
    private var adapter: PreviewShopAdapter? = null
    private val viewModel = ListShopViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shop_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rcView)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        adapter = PreviewShopAdapter(requireContext()) { onItemClick(it) }
        recyclerView.adapter = adapter
        viewModel.shopsListMV.observe(viewLifecycleOwner, {
            adapter?.updates(it)
            list = it
            val count = view.findViewById<TextView>(R.id.countDiscountNumbShop)
            count.setText("${list.size}")
        })
    }


    private fun onItemClick(position: Int) {
        list?.get(position)?.let { shopItem ->
            val fragment = DetailShopFragment.newInstance(shopItem)
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ListShopFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}