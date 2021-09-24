package com.android.iceprice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.Toast
import java.util.Locale
import org.intellij.lang.annotations.Language

class SettingsFragment : Fragment() {

    private var spinnerCountry: Spinner? = null
    private var spinnerCity: Spinner? = null
    private var backButton: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var toggle = view.findViewById<RadioGroup>(R.id.toggle)

        backButton = view.findViewById(R.id.backToList)
        backButton?.setOnClickListener {
            requireActivity().supportFragmentManager
                .popBackStack()
        }

        val buttonRu = view.findViewById<RadioButton>(R.id.switchBut1)
        buttonRu.setOnClickListener {
            setLocale("ru")
        }
        val buttonEn = view.findViewById<RadioButton>(R.id.switchBut2)
        buttonEn.setOnClickListener {
            setLocale("en")
        }

        spinnerCountry = view.findViewById(R.id.spinnerCountry)
        val arrayCityRussia = resources.getStringArray(R.array.countryRussia)
        val arrayCityKazahstan = resources.getStringArray(R.array.countryKazahstan)
        val arrayCityKirgizstan = resources.getStringArray(R.array.countryKirgizstan)
        var adapter = activity?.let {
            ArrayAdapter(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                arrayCityKazahstan
            )
        }
        spinnerCity = view.findViewById(R.id.spinnerCity)
        spinnerCity?.adapter = adapter

        spinnerCountry?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> adapter = activity?.let {
                        ArrayAdapter(
                            it,
                            R.layout.support_simple_spinner_dropdown_item,
                            arrayCityRussia
                        )
                    }
                    1 -> adapter = activity?.let {
                        ArrayAdapter(
                            it,
                            R.layout.support_simple_spinner_dropdown_item,
                            arrayCityKazahstan
                        )
                    }
                    2 -> adapter = activity?.let {
                        ArrayAdapter(
                            it,
                            R.layout.support_simple_spinner_dropdown_item,
                            arrayCityKirgizstan
                        )
                    }
                }
                spinnerCity?.adapter = adapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        spinnerCity?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var currCity = spinnerCity?.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }


    private fun setLocale(languageName: String) {
        var locale = Locale(languageName)
        var res = resources
        var dm = res.displayMetrics
        var conf = res.configuration
        conf.locale = locale
        res.updateConfiguration(conf, dm)
        var refresh = Intent(context, MainActivity::class.java)
        startActivity(refresh)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}

