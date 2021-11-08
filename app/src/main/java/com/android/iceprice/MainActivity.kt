package com.android.iceprice

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.android.iceprice.extensions.applyNewLocale
import com.android.iceprice.extensions.openFragment
import com.android.iceprice.ui.list.ListFragment
import com.android.iceprice.ui.list.ListFragment.Companion.newInstance
import java.util.Locale
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var switchMain: LinearLayout? = null

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateUi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            openFragment(newInstance(ListFragment.Tab.SHOPS))
        }
        clickGift()
        clickSale()
        clickEvent()
        clickPromocodes()
        switchMain = findViewById(R.id.switcherMain)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    override fun attachBaseContext(newBase: Context?) {
        val locale = Locale(UserLocalInfo.language)
        super.attachBaseContext(newBase?.applyNewLocale(locale))
    }

    private fun clickPromocodes() {
        val ivClickGift = findViewById<ImageView>(R.id.promocodes)
        ivClickGift.setOnClickListener {
            openFragment(newInstance(ListFragment.Tab.PROMOCODES))
        }
    }

    private fun clickGift() {
        val ivClickGift = findViewById<ImageView>(R.id.gifts)
        ivClickGift.setOnClickListener {
            openFragment(newInstance(ListFragment.Tab.GIFTS))
        }
    }

    private fun clickSale() {
        val ivclickSale = findViewById<ImageView>(R.id.sale)
        ivclickSale.setOnClickListener {
            openFragment(newInstance(ListFragment.Tab.SHOPS))
        }
    }

    private fun clickEvent() {
        val ivClickEvent = findViewById<ImageView>(R.id.events)
        ivClickEvent.setOnClickListener {
            openFragment(newInstance(ListFragment.Tab.EVENTS))
        }
    }

    fun updateUi() {
        switchMain?.visibility = if (supportFragmentManager.backStackEntryCount == 1) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

}