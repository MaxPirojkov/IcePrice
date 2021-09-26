package com.android.iceprice

import com.chibatching.kotpref.KotprefModel

/**
 * The name of the file should describe what the code in the file does.
 * Therefore, you should avoid using meaningless words such as “Util” in file names.”
 * - Official Kotlin Style Guide
 */

object UserLocalInfo : KotprefModel() {
    var language by nullableStringPref(default = "ru")
    var country by intPref(default = 0)
    var countryName by stringPref(default = "Россия")
    var citySlug by stringPref("moscow")
    var cityName by stringPref("Москва")

}