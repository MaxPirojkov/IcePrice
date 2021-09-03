package com.android.iceprice

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.android.iceprice.giftfragment.ListEventFragment
import com.android.iceprice.giftfragment.ListGiftFragment
import com.android.iceprice.promocodefragment.ListPromocodesFragment
import com.android.iceprice.shopfragment.ListShopFragment


class MainActivity : AppCompatActivity() {
    private var switchMain: LinearLayout? = null

//    private val currentFragment: Fragment
//        get() = supportFragmentManager.findFragmentById(R.id.content)!!

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateUi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            openFragment(SettingsFragment.newInstance())
        }
        clickGift()
        clickSale()
        clickEvent()
        clickPromocodes()
        switchMain = findViewById(R.id.switcherMain)

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    private fun clickPromocodes() {
        val ivClickGift = findViewById<ImageView>(R.id.promocodes)
        ivClickGift.setOnClickListener {
            openFragment(ListPromocodesFragment())

        }
    }


    private fun clickGift() {
        val ivClickGift = findViewById<ImageView>(R.id.gifts)
        ivClickGift.setOnClickListener {
            openFragment(ListGiftFragment())
        }
    }

    fun clickSale() {
        val ivclickSale = findViewById<ImageView>(R.id.sale)
        ivclickSale.setOnClickListener {
            openFragment(ListShopFragment())
        }
    }

    fun clickEvent() {
        val ivClickEvent = findViewById<ImageView>(R.id.events)
        ivClickEvent.setOnClickListener {
            openFragment(ListEventFragment())

        }
    }


    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment)
            .commit()
    }


    fun updateUi() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            switchMain?.visibility = View.GONE
        } else {
            switchMain?.visibility = View.VISIBLE

        }

    }
}