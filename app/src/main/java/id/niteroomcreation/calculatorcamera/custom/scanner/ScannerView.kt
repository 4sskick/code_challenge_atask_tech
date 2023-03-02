package id.niteroomcreation.calculatorcamera.custom.scanner

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceView

/**
 * Created by Septian Adi Wijaya on 02/03/2023.
 * please be sure to add credential if you use people's code
 */
class ScannerView : SurfaceView {

    companion object {
        val TAG = ScannerView::class.java.simpleName
    }

    var state: String = ""
    var scanning: Boolean = false
    private lateinit var scannerListener: ScannerListener

    constructor(context: Context?) : super(context)


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    fun setOnDetectedListener(act: Activity, scannerListener: ScannerListener) {
        this.scannerListener = scannerListener
        this.scanning = true
        Scanner(act, this, scannerListener)
    }

    fun onDetected(detections: String) {
        Log.e(TAG, "onDetected: ${detections}")
    }

    fun onStateChanged(state: String, i: Int) {
        Log.e(TAG, "onStateChanged: $state # $i")
    }

    
}