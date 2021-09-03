package com.android.iceprice.giftfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.iceprice.R
import com.android.iceprice.giftfragment.model.GiftItem

class ListGiftFragment : Fragment() {

    private var list: List<GiftItem> = emptyList()
    private var adapter: PreviewGiftAdapter? = null
    private val viewModel = ListGiftViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_gift, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rcViewGift)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        adapter = PreviewGiftAdapter(requireContext()) { onItemClick(it) }
        recyclerView.adapter = adapter
        viewModel.giftListMV.observe(viewLifecycleOwner, {
            adapter?.updates(it)
            list = it
            val count = view.findViewById<TextView>(R.id.countDiscountNumbShop)
            count.setText("${list.size}")
        })

    }

    private fun onItemClick(position: Int) {
        list?.get(position)?.let { giftItem ->
            val fragment = DetailGiftFragment.newInstance(giftItem)
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
            ListGiftFragment().apply {
                arguments = Bundle().apply {
                }
            }


    }
}