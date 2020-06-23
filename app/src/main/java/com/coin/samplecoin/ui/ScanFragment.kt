package com.coin.samplecoin.ui


import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.coin.samplecoin.R
import com.coin.samplecoin.getCameraId
import com.coin.samplecoin.navigateBrowser
import com.coin.samplecoin.widget.FinderView
import com.google.zxing.Result
import me.dm7.barcodescanner.core.IViewFinder
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanFragment : Fragment(), ZXingScannerView.ResultHandler {

    private var scannerView: ZXingScannerView? = null

    private val scanRunnable: Runnable = Runnable { scannerView?.resumeCameraPreview(this) }

    private val scanHandler: Handler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.qr_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScannerView()
    }

    override fun onResume() {
        super.onResume()
        scannerView?.apply {
            setResultHandler(this@ScanFragment)
            startCamera(getCameraId(CameraCharacteristics.LENS_FACING_BACK))
            flash = false
            setAutoFocus(true)
        }
    }

    override fun onPause() {
        super.onPause()
        scannerView?.stopCamera()
    }

    override fun handleResult(result: Result?) {
        val text = result?.text?.toString() ?: return
        navigateBrowser(text)

        // resume scan if result text invalid
        //scanHandler.postDelayed(scanRunnable, 300)
    }


    private fun initScannerView() {
        val ctx = context ?: return
        val finderView = FinderView(ctx)
        scannerView = object : ZXingScannerView(ctx) {
            override fun createViewFinderView(context: Context): IViewFinder {
                return finderView!!
            }
        }.also {
            it.id = View.generateViewId()
            it.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
            (view as ConstraintLayout).addView(it, 0)
        }
    }

}
