package com.android.iceprice.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.iceprice.SingleAction
import com.android.iceprice.UserLocalInfo
import com.android.iceprice.network.Result
import com.android.iceprice.network.api.NetworkModule
import com.android.iceprice.network.model.City
import com.android.iceprice.network.model.Country
import com.chibatching.kotpref.bulk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val _secondLanguage = MutableLiveData<String>()
    val secondLanguage: LiveData<String> = _secondLanguage

    private var currentCountry: Int = UserLocalInfo.country
    private var currentCountryName: String = UserLocalInfo.countryName
    private var currentCitySlug: String = UserLocalInfo.citySlug

    private val countriesMap = HashMap<Int, List<City>>()

    init {
        getCountries()
        getCities(UserLocalInfo.country)
        _secondLanguage.value = getSecondLanguageName()
    }

    fun onChangeLocale(){
        val index = _citySelection.value ?: 0
        val cityItem = cityItems[if (index > 0) index else 0]
        UserLocalInfo.bulk {
            countryName = currentCountryName
            country = currentCountry
            citySlug = cityItem.slug
            cityName = cityItem.name
        }
    }

    fun onCountryClick(position: Int) {
        if (countryItems.isNotEmpty()) {
            val country = countryItems[position]
            currentCountry = country.code
            currentCountryName = country.name
            getCities(country.code)
            _secondLanguage.value = getSecondLanguageName()
        }
    }

    fun onCityClick(position: Int) {
        if (cityItems.isNotEmpty()) {
            val cityItem = cityItems[position]
            UserLocalInfo.bulk {
                countryName = currentCountryName
                country = currentCountry
                citySlug = cityItem.slug
                cityName = cityItem.name
            }
            _saveCity.postValue(SingleAction(Unit))
        }
    }

    fun isCityChanged(): Boolean {
        val index = _citySelection.value ?: 0
        val city = cityItems[if (index > 0) index else 0]
        return if (UserLocalInfo.citySlug != city.slug) {
            UserLocalInfo.bulk {
                countryName = currentCountryName
                country = currentCountry
                citySlug = city.slug
                cityName = city.name
            }
            true
        } else {
            false
        }
    }

    fun getSecondLocale() = when (currentCountry) {
        1 -> "kk"
        2 -> "ky"
        else -> "en"
    }

    private fun getSecondLanguageName() = when (currentCountry) {
        1 -> "Қазақ"
        2 -> "Кыргыз"
        else -> "English"
    }

    private fun getCountries() {
        viewModelScope.launch {
            val result = try {
                requestCountries()
            } catch (e: Exception) {
                Result.Error(e)
            }

            when (result) {
                is Result.Success<List<Country>> -> {
                    _countries.value = result.data.map { it.name }
                    countryItems = result.data
                    _countrySelection.value =
                        countryItems.indexOfFirst { it.code == currentCountry }
                }
                is Result.Error -> Timber.e("error ${result.exception}")
            }
        }
    }

    private fun getCities(code: Int) {
        if (countriesMap.isEmpty()) {
            viewModelScope.launch {
                val result = try {
                    requestCities()
                } catch (e: Exception) {
                    Result.Error(e)
                }
                when (result) {
                    is Result.Success<List<City>> -> {
                        result.data.forEach {
                            val cities: MutableList<City> =
                                countriesMap[it.country]?.toMutableList() ?: mutableListOf()
                            cities.add(it)
                            countriesMap[it.country] = cities
                        }
                        updateCities(code, true)

                    }
                    is Result.Error -> Timber.e("error ${result.exception}")
                }
            }
        } else {
            updateCities(code)
        }
    }

    private fun updateCities(code: Int, firstChoice: Boolean = false) {
        _cities.value = countriesMap[code]?.map { it.name } ?: emptyList()
        cityItems = countriesMap[code] ?: emptyList()
        _citySelection.value = cityItems.indexOfFirst { it.slug == currentCitySlug && firstChoice }
    }

    private suspend fun requestCountries(): Result.Success<List<Country>> {
        return withContext(Dispatchers.IO) {
            Result.Success(NetworkModule.api.getCountries())
        }
    }

    private suspend fun requestCities(): Result.Success<List<City>> {
        return withContext(Dispatchers.IO) {
            Result.Success(NetworkModule.api.getCities())
        }
    }

}