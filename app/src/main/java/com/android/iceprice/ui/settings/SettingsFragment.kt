package com.android.iceprice.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.android.iceprice.R
import com.android.iceprice.SingleAction
import com.android.iceprice.UserLocalInfo
import com.android.iceprice.extensions.observe
import com.android.iceprice.extensions.runViewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var spinnerCountry: Spinner? = null
    private var spinnerCity: Spinner? = null
    private val viewModel = SettingsViewModel()
    private var cityAdapter: ArrayAdapter<String>? = null
    private var countryAdapter: ArrayAdapter<String>? = null
    private var buttonSecond: RadioButton? = null
    private var buttonRu: RadioButton? = null
    private var userAgreement: TextView? = null
    private var privacyPolicy: TextView? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton: LinearLayout = view.findViewById(R.id.backToList)
        backButton.setOnClickListener {
            setFragmentResult(
                REQUEST_CHANGE_CITY,
                bundleOf(KEY_CITY_CHANGED to viewModel.isCityChanged())
            )
            navigateBack()
        }

        userAgreement = view.findViewById<TextView>(R.id.titleUserAgree).apply {
            setOnClickListener { openUserAgreement() }
        }

        privacyPolicy = view.findViewById<TextView>(R.id.titlePrivacyPolice).apply {
            setOnClickListener { openPrivacy() }
        }

        buttonRu = view.findViewById<RadioButton>(R.id.switchBut1).apply {
            setOnClickListener { setLocale("ru") }
        }
        buttonSecond = view.findViewById<RadioButton>(R.id.switchBut2).apply {
            setOnClickListener { setLocale(viewModel.getSecondLocale()) }
            if (UserLocalInfo.language != "ru") {
                isChecked = true
            }
        }

        initCountrySpinner(view)
        initCitySpinner(view)
        bindTo()
    }

    private fun initCountrySpinner(view: View) {
        countryAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            listOf(UserLocalInfo.countryName)
        )
        spinnerCountry = view.findViewById<Spinner>(R.id.spinnerCountry).apply {
            adapter = countryAdapter
        }
        setCountryListener()
    }

    private fun setCountryListener() {
        spinnerCountry?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                viewModel.onCountryClick(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setCityListener() {
        spinnerCity?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                viewModel.onCityClick(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun initCitySpinner(view: View) {
        countryAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            listOf(UserLocalInfo.cityName)
        )
        spinnerCity = view.findViewById<Spinner>(R.id.spinnerCity).apply {
            adapter = cityAdapter
        }
        setCityListener()
    }

    private fun bindTo() {
        runViewModel(viewModel) {
            observe(countries, ::handleCountries)
            observe(cities, ::handleCities)
            observe(saveCity, ::handleChooseCity)
            observe(countrySelection, ::handleSelectionCountry)
            observe(citySelection, ::handleSelectionCity)
            observe(secondLanguage, ::handleSecondLanguage)
            observe(updateLanguage, ::handleUpdateLocale)
        }
    }


    private fun handleSecondLanguage(language: String?) {
        language?.let {
            buttonSecond?.text = language
        }
    }

    private fun handleUpdateLocale(locale: String?) {
        locale?.let {
            setLocale(it)
            buttonRu?.isChecked = true
        }
    }

    private fun handleSelectionCountry(position: Int?) {
        position?.let {
            spinnerCountry?.setSelection(position)
        }
    }

    private fun handleSelectionCity(position: Int?) {
        position?.let {
            spinnerCity?.setSelection(position)
        }
    }

    private fun handleChooseCity(event: SingleAction<Unit>?) {
        event?.getContentIfNotHandled()?.let {
            setFragmentResult(REQUEST_CHANGE_CITY, bundleOf(KEY_CITY_CHANGED to true))
            navigateBack()
        }
    }

    private fun handleCountries(countries: List<String>?) {
        spinnerCountry?.onItemSelectedListener = null
        countries?.let { items ->
            countryAdapter = ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                items
            )
            spinnerCountry?.adapter = countryAdapter
        }
        view?.postDelayed({ setCountryListener() }, DELAY_MS)
    }

    private fun handleCities(cities: List<String>?) {
        spinnerCity?.onItemSelectedListener = null
        cities?.let { items ->
            cityAdapter = ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                items
            )
            spinnerCity?.adapter = cityAdapter
        }
        view?.postDelayed({ setCityListener() }, DELAY_MS)
    }

    private fun setLocale(languageName: String) {
        viewModel.onChangeLocale()
        UserLocalInfo.language = languageName
        activity?.viewModelStore?.clear()
        ActivityCompat.recreate(requireActivity())
    }

    private fun navigateBack() {
        requireActivity().supportFragmentManager
            .popBackStack()
    }

    private fun openUserAgreement() {
        startActivity(Intent(Intent.ACTION_VIEW,
            Uri.parse("http://iceprice.app/user_agreement.html")))
    }

    private fun openPrivacy() {
        startActivity(Intent(Intent.ACTION_VIEW,
            Uri.parse("http://iceprice.app/privacy_policy.html")))

    }

    companion object {
        fun newInstance() = SettingsFragment()
        const val REQUEST_CHANGE_CITY = "request_change_city"
        const val KEY_CITY_CHANGED = "key_city_changed"
        const val DELAY_MS = 700L
    }
}

