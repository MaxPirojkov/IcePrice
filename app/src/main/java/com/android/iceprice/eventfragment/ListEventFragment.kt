package com.android.iceprice.giftfragment

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
import com.android.iceprice.giftfragment.model.EventItem

class ListEventFragment : Fragment() {

    private var list: List<EventItem> = emptyList()
    private var adapter: PreviewEventAdapter? = null
    private val viewModel = ListEventViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_event, container, false)
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rcViewEvent)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        adapter = PreviewEventAdapter(requireContext()) { onItemClick(it) }
        recyclerView.adapter = adapter
        viewModel.eventListMV.observe(viewLifecycleOwner, {
            adapter?.updates(it)
            list = it
            val countEvent = view.findViewById<TextView>(R.id.countDiscountNumbEvent)
            countEvent.setText("${list.size}")
        })
    }


    private fun onItemClick(position: Int) {
        list?.get(position)?.let { shopItem ->
            val fragment = DetailEventFragment.newInstance(shopItem)
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
            ListEventFragment().apply {
                arguments = Bundle().apply {
                }
            }


    }
}