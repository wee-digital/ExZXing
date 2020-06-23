package com.coin.samplecoin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.coin.samplecoin.R
import com.coin.samplecoin.getQRCodeImage
import kotlinx.android.synthetic.main.qr_code.*


class CodeFragment : Fragment() {

    companion object {
        const val GEN_TEXT = "www.pornhub.com"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.qr_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bitmap = getQRCodeImage(GEN_TEXT) ?: return
        imageViewQR.setImageBitmap(bitmap)
    }


}

