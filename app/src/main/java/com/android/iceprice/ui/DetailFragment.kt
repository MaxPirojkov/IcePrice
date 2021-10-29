package com.android.iceprice.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.iceprice.R
import com.android.iceprice.extensions.fromHtml
import com.android.iceprice.extensions.gone
import com.android.iceprice.extensions.goneIf
import com.android.iceprice.extensions.loadImage
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.showAlignTop
import java.util.Locale
import kotlinx.android.synthetic.main.fragment_detail.textWebsite

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var detailItem: DetailItem? = null
    private var backButton: LinearLayout? = null
    private var addressButton: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailItem = arguments?.getParcelable(KEY_ITEM)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonQr = view.findViewById<TextView>(R.id.qrButton)
        buttonQr.setOnClickListener {

            val balloon = Balloon.Builder(requireContext())
                .setLayout(R.layout.qr_code_view)
                .setArrowSize(10)
                .setArrowPosition(0.5f)
                .setWidthRatio(0.55f)
                .setCornerRadius(4f)
                .setBackgroundColorResource(R.color.white)
                .setMarginBottom(12)
                .setArrowSize(10)
                .setBalloonAnimation(BalloonAnimation.FADE)
                .setLifecycleOwner(viewLifecycleOwner)
                .build()


            buttonQr.showAlignTop(balloon)

            val imageView: ImageView = balloon.getContentView().findViewById(R.id.qrCodeView)
            imageView.setImageBitmap(generateQRCode(detailItem?.shopName ?: ""))
        }

        detailItem?.let {
            view.findViewById<ImageView>(R.id.picShopDetail).loadImage(it.image)
            view.findViewById<TextView>(R.id.nameShop).text = it.title
            view.findViewById<TextView>(R.id.descripInfo).text = it.description
            view.findViewById<TextView>(R.id.textShop).text = it.shopName

            if (it.type == Type.SHOP || it.type == Type.GIFT || it.type == Type.EVENT) {
                view.findViewById<TextView>(R.id.textPhone).text = it.phone
                view.findViewById<TextView>(R.id.textAddress).text = it.address
            } else {
                view.findViewById<View>(R.id.phoneGroup).gone()
                view.findViewById<View>(R.id.addressGroup).gone()
            }

            view.findViewById<View>(R.id.webGroup)
                .goneIf(it.type == Type.SHOP || it.type == Type.GIFT)

            if (it.type == Type.EVENT || it.type == Type.PROMOCODE) {
                textWebsite.text = generateLink(it.website)
                textWebsite.movementMethod = LinkMovementMethod.getInstance()
            }
        }
        backButton = view.findViewById(R.id.backToListShop)
        backButton?.setOnClickListener {
            requireActivity().supportFragmentManager
                .popBackStack()
        }

        addressButton = view.findViewById(R.id.textAddress)
        addressButton?.setOnClickListener {
            val addrss = (addressButton!!.text).toString()
            if (addrss.isNotEmpty()) openMap(addrss)
        }


    }

    private fun openMap(address: String) {
        val uri = Uri.parse("geo:0,0?q=$address")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun generateLink(website: String?): Spanned {
        return "<a href=\"$website\">${getString(R.string.open_link)}</a>".fromHtml()
    }

    private fun generateQRCode(name: String): Bitmap {
        val content = "ICEPRICE $name DISCOUNT"

        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 200, 200)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }

    companion object {

        fun newInstance(detailItem: DetailItem) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_ITEM, detailItem)
                }
            }

        private const val KEY_ITEM = "key_item"
    }

}
