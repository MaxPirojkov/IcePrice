package com.android.iceprice.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.iceprice.SingleAction
import com.android.iceprice.UserLocalInfo
import com.android.iceprice.network.model.City
import com.android.iceprice.network.model.Country
import com.chibatching.kotpref.bulk
import timber.log.Timber

class SettingsViewModel : ViewModel() {

    private val _countries = MutableLiveData<List<String>>()
    val countries: LiveData<List<String>> = _countries

    private val _cities = MutableLiveData<List<String>>()
    val cities: LiveData<List<String>> = _cities

    private val _citySelection = MutableLiveData<Int>()
    val citySelection: LiveData<Int> = _citySelection

    private val _countrySelection = MutableLiveData<Int>()
    val countrySelection: LiveData<Int> = _countrySelection

    private var countryItems: List<Country> = emptyList()

    private var cityItems: List<City> = emptyList()

    private val _saveCity = MutableLiveData<SingleAction<Unit>>()
    val saveCity: LiveData<SingleAction<Unit>> = _saveCity

    private var currentCountry: Int = UserLocalInfo.country
    private var currentCountryName: String = UserLocalInfo.countryName
    private var currentCitySlug: String = UserLocalInfo.citySlug

    init {
        getCountries()
        getCities(UserLocalInfo.country)
    }

    fun onCountryClick(position: Int) {
        Timber.e("onCountryClick position=$position")
        val country = countryItems[position]
        currentCountry = country.code
        currentCountryName = country.name
        getCities(country.code)
    }

    fun onCityClick(position: Int) {
        Timber.e("onCityClick position=$position")
        val cityItem = cityItems[position]
        UserLocalInfo.bulk {
            countryName = currentCountryName
            country = currentCountry
            citySlug = cityItem.slug
            cityName = cityItem.name
        }
        _saveCity.postValue(SingleAction(Unit))
    }

    private fun getCountries() {
        //todo make request
        //start need change
        val list = listOf<Country>(
            Country("1", "Россия", "Russia", 0),
            Country("2", "Казахстан", "Kazahstan", 1),
            Country("1", "Кыргызстан", "Kyrgyzstan", 2)
        )
        //end
        _countries.value = list.map { it.name }
        countryItems = list
        _countrySelection.value = countryItems.indexOfFirst { it.code == currentCountry }
    }

    private fun getCities(code: Int) {
        //todo make request
        //start need change
        val list =
            if (code == 0) {
                listOf<City>(
                    City("1", "Москва", "Moscow", 1, "moscow"),
                    City("2", "Санкт-Петербург", "Saint-Petersburg", 2, "spb"),
                )
            } else {
                listOf<City>(
                    City("1", "Москва2", "Moscow2", 1, "moscow"),
                    City("2", "Санкт-Петербург2", "Saint-Petersburg2", 2, "spb")
                )
            }
        //end
        _cities.value = list.map { it.name }
        cityItems = list
        _citySelection.value = cityItems.indexOfFirst { it.slug == currentCitySlug }
    }

}