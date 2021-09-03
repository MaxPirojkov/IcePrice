package com.android.iceprice.promocodefragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.iceprice.ClickCallBackVisible
import com.android.iceprice.ClickCallbackList
import com.android.iceprice.R

import com.android.iceprice.promocodefragment.model.PromocodeItem

class ListPromocodesFragment : Fragment() {
    private var list: List<PromocodeItem> = emptyList()
    private var adapter: PreviewPromocodesAdapter? = null
    private val viewModel = ListPromocodesViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_promocode, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rcViewPromocode)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        adapter = PreviewPromocodesAdapter(requireContext()) { onItemClick(it) }
        recyclerView.adapter = adapter
        viewModel.promocodeListMV.observe(viewLifecycleOwner, {
            adapter?.updates(it)
            list = it
            val count = view.findViewById<TextView>(R.id.countDiscountNumbPromo)
            count.setText("${list.size}")
        })
    }


    private fun onItemClick(position: Int) {
        list?.get(position)?.let { promocodeItem ->
            val fragment = DetailPromocodesFragment.newInstance(promocodeItem)
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
            ListPromocodesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}