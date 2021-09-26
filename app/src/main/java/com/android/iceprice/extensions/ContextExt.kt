package com.android.iceprice.extensions

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

fun Context.applyNewLocale(locale: Locale): Context {
    val config = Configuration().apply {
        setTo(resources.configuration)
        setLocale(locale)
    }
    return createConfigurationContext(config)
}