package com.android.iceprice.ui.list

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.iceprice.R
import com.android.iceprice.SingleAction
import com.android.iceprice.error.toIcException
import com.android.iceprice.extensions.add
import com.android.iceprice.extensions.gone
import com.android.iceprice.extensions.observe
import com.android.iceprice.extensions.replace
import com.android.iceprice.extensions.runViewModel
import com.android.iceprice.extensions.visible
import com.android.iceprice.ui.DetailFragment
import com.android.iceprice.ui.DetailItem
import com.android.iceprice.ui.ResultWrapper
import com.android.iceprice.ui.settings.SettingsFragment

class ListFragment : Fragment(R.layout.fragment_list) {

    private var adapter: ListAdapter? = null
    private val viewModel = ListViewModel()
    private var countView: TextView? = null
    private var content: View? = null
    private var progressBar: ProgressBar? = null
    private var errorContent: View? = null
    private var errorTitle: TextView? = null
    private var selectCity: TextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tab: Tab = (arguments?.getSerializable(KEY_TAB) as? Tab) ?: Tab.SHOPS
        viewModel.loadItems(tab)
        viewModel.loadCity()
        countView = view.findViewById(R.id.countTitle)
        selectCity = view.findViewById(R.id.selectCity)
        errorTitle = view.findViewById(R.id.errorTitle)
        content = view.findViewById(R.id.content)
        progressBar = view.findViewById(R.id.progressBar)
        errorContent = view.findViewById(R.id.errorGroup)
        view.findViewById<View>(R.id.btnRetry).setOnClickListener {
            viewModel.retry()
        }

        val butSetting = view.findViewById<ImageView>(R.id.settingsButton)
        butSetting?.setOnClickListener { replace(SettingsFragment.newInstance()) }

        initRecycler(view)
        bindTo(tab)

        setFragmentResultListener(SettingsFragment.REQUEST_CHANGE_CITY) { _, bundle ->
            val cityChanged: Boolean = bundle.getBoolean(SettingsFragment.KEY_CITY_CHANGED)
            if (cityChanged) {
                viewModel.changeCity()
            }
        }

    }

    private fun bindTo(tab: Tab) {
        runViewModel(viewModel) {
            observe(state) { handleState(tab, it) }
            observe(openDetail, ::handlePath)
            observe(cityChanges, ::handleCityName)
        }
    }

    private fun handleState(tab: Tab, result: ResultWrapper<List<DetailItem>, Throwable>?) {
        when (result?.state) {
            ResultWrapper.State.FAILURE -> {
                progressBar?.gone()
                errorContent?.visible()
                errorTitle?.text =
                    result.error?.toIcException()?.getLocalizedMessage(resources) ?: ""
            }
            ResultWrapper.State.LOADING -> {
                content?.gone()
                errorContent?.gone()
                progressBar?.visible()
            }
            ResultWrapper.State.SUCCESS -> {
                val items = result.data ?: emptyList()
                adapter?.updates(items)
                countView?.text = getCountTitle(tab, items.size)
                progressBar?.gone()
                content?.visible()
            }
        }
    }

    private fun handlePath(event: SingleAction<DetailItem>?) {
        event?.getContentIfNotHandled()?.let {
            replace(DetailFragment.newInstance(it))
        }
    }

    private fun handleCityName(cityName: String?) {
        cityName?.let {
            selectCity?.text = it
        }
    }

    private fun initRecycler(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ListAdapter(requireContext()) { onItemClick(it) }
        recyclerView.adapter = adapter
    }


    private fun onItemClick(position: Int) {
        viewModel.onClickPosition(position)
    }

    private fun getCountTitle(tab: Tab, count: Int): String {
        return when (tab) {
            Tab.SHOPS -> "${getString(R.string.title_shop)} $count"
            Tab.PROMOCODES -> "${getString(R.string.title_promocodes)} $count"
            Tab.GIFTS -> "${getString(R.string.title_gifts)} $count"
            Tab.EVENTS -> "${getString(R.string.tittle_events)} $count"
        }
    }

    companion object {

        fun newInstance(tab: Tab) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_TAB, tab)
                }
            }
    }

    enum class Tab {
        EVENTS,
        GIFTS,
        PROMOCODES,
        SHOPS
    }
}

private const val KEY_TAB = "key_tab"