package com.android.iceprice.shopfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.iceprice.R
import com.android.iceprice.SettingsFragment
import com.android.iceprice.shopfragment.model.ShopItem

class ListShopFragment : Fragment(R.layout.fragment_list_shop) {

    private var list: List<ShopItem> = emptyList()
    private var adapter: PreviewShopAdapter? = null
    private val viewModel = ListShopViewModel()
    private var butSetting: ImageView? = null
    private var progressBar: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_shop, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.rcView)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        adapter = PreviewShopAdapter(requireContext()) { onItemClick(it) }
        recyclerView.adapter = adapter
        viewModel.shopsListMV.observe(viewLifecycleOwner, {
            adapter?.updates(it)
            list = it
            val count = view.findViewById<TextView>(R.id.countDiscountNumbShop)
            count.setText("${list.size}")
        })
        butSetting = view.findViewById(R.id.settingsButtonShop)
        butSetting?.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }
        progressBar = view.findViewById(R.id.progress_bar)
        viewModel.progressChanges.observe(viewLifecycleOwner) { showProgress ->
            showProgress?.let {
                if (showProgress) {
                    recyclerView.visibility = View.GONE
                    progressBar?.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.VISIBLE
                    progressBar?.visibility = View.GONE
                }
            }


        }
    }


    private fun onItemClick(position: Int) {
        list?.get(position)?.let { shopItem ->
            val fragment = DetailShopFragment.newInstance(shopItem)
            requireActivity().supportFragmentManager
                .beginTransaction()
//                .setCustomAnimations(R.anim.fade_out_1, R.anim.slide_out_left)
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