package com.coin.samplecoin

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.net.Uri
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.util.*

fun getQRCodeImage(text: String): Bitmap? {
    val multiFormatWriter = MultiFormatWriter()
    return try {
        val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
        hints[EncodeHintType.MARGIN] = 0
        val bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 320, 320, hints)
        val barcodeEncoder = BarcodeEncoder()
        barcodeEncoder.createBitmap(bitMatrix)
    } catch (e: WriterException) {
        null
    }
}

/**
 * CameraCharacteristics.LENS_FACING_FRONT
 * CameraCharacteristics.LENS_FACING_BACK
 * CameraCharacteristics.LENS_FACING_EXTERNAL
 */
fun getCameraId(facing: Int): Int {
    val manager = App.instance.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    return manager.cameraIdList.first {
        manager.getCameraCharacteristics(it)
            .get(CameraCharacteristics.LENS_FACING) == facing
    }.toInt()
}

fun Fragment.onGrantedPermission(@RequiresPermission vararg permissions: String, onGranted: () -> Unit) {
    val list = mutableListOf<String>()
    permissions.forEach {
        if (ContextCompat.checkSelfPermission(context!!, it) != PackageManager.PERMISSION_GRANTED) {
            list.add(it)
        }
    }
    if (list.isNullOrEmpty()) {
        onGranted()
        return
    }
    requestPermissions(permissions, 1)
}

fun navigateBrowser(url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        App.instance.startActivity(intent)
    } catch (e: Throwable) {

    }
}