package id.niteroomcreation.calculatorcamera.custom.scanner

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer

/**
 * Created by Septian Adi Wijaya on 02/03/2023.
 * please be sure to add credential if you use people's code
 */
class Scanner() {

    companion object {
        val REQUEST_CAMERA = 12
        val TAG = Scanner::class.java.simpleName
    }

    private lateinit var act: Activity
    private lateinit var camera: SurfaceView
    private lateinit var listener: ScannerListener
    private var showToast: Boolean = true
    private var scanning: Boolean = false
    var state: String = "loading"

    constructor(act: Activity, surfaceView: SurfaceView) : this() {
        this.act = act
        setSurfaceView(surfaceView)
    }

    constructor(
        act: Activity,
        scannerView: SurfaceView,
        scannerListener: ScannerListener
    ) : this() {
        this.act = act

        setSurfaceView(surfaceView = scannerView)
        setListener(scannerListener)
        scan()
    }

    fun setSurfaceView(surfaceView: SurfaceView) {
        this.camera = surfaceView
    }

    fun setListener(listener: ScannerListener) {
        this.listener = listener
    }

    fun scan() {
        prepareScanning()
    }

    private fun prepareScanning() {
        var textRecognizer = TextRecognizer.Builder(act).build()

        if (!textRecognizer.isOperational) {
            state = "OCR not ready yet"

            Toast.makeText(act, state, Toast.LENGTH_LONG).show()
            Log.e(TAG, "prepareScanning: $state")

            listener.onStateChanged(state, 3)
        }

        var cameraSource = CameraSource.Builder(act, textRecognizer)
            .setFacing(CameraSource.CAMERA_FACING_BACK)
            .setRequestedPreviewSize(1200, 1024)
            .setRequestedFps(2.0f)
            .setAutoFocusEnabled(true)
            .build()

        camera.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            act,
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            act,
                            arrayOf(Manifest.permission.CAMERA),
                            REQUEST_CAMERA
                        )
                        return
                    }
                    cameraSource.start(camera.holder)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
//                TODO("Not yet implemented")

                Log.d(TAG, "surfaceChanged: w: $width x h: $height on format $format")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }

        })

        textRecognizer.setProcessor(object : Detector.Processor<TextBlock> {
            override fun release() {
                //do nothing
            }

            override fun receiveDetections(detections: Detector.Detections<TextBlock>) {
                state = "running"
                listener.onStateChanged(state, 1)

                var items: SparseArray<TextBlock> = detections.detectedItems

                if (items.size() != 0) {

                    var stringBuilder = StringBuilder()

                    var ind = 0
                    while (ind < items.size()) {
                        var item: TextBlock = items.valueAt(ind)
                        stringBuilder.append(item.value).append("\n")
                    }

                    act.runOnUiThread({ listener.onDetected(stringBuilder.toString()) })
                    Log.e(TAG, "receiveDetections: $stringBuilder")
                }
            }

        })
    }

    fun setScanning(scanning: Boolean) {
        if (scanning) {
            prepareScanning()
            this.scanning = true
        } else {
            @Suppress("DEPRECATION")
            camera.destroyDrawingCache()
            this.scanning = false
        }
    }

}