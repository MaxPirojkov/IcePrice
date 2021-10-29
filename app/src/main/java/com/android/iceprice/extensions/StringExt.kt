package com.android.iceprice.extensions

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import java.util.Locale


fun String.fromHtml(): Spanned =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }



