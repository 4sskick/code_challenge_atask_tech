package id.niteroomcreation.calculatorcamera.presenter.feature.scan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.niteroomcreation.calculatorcamera.databinding.AScanBinding
import id.niteroomcreation.calculatorcamera.presenter.util.view_custom.scanner.Scanner
import id.niteroomcreation.calculatorcamera.presenter.util.view_custom.scanner.ScannerListener

/**
 * Created by Septian Adi Wijaya on 03/03/2023.
 * please be sure to add credential if you use people's code
 */
class ScanActivity : AppCompatActivity() {
    companion object {
        val TAG = ScanActivity::class.java.simpleName

        val SCAN_DATA = "data"
        val URI_DATA = "data.uri"

        //if true scan using camera otherwise image
        val SCAN_MODE = "mode.scan"
    }

    private lateinit var binding: AScanBinding
    private lateinit var scanOutput: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Scanner(this,
            binding.scanSurface,
            if (intent.extras?.getBoolean(SCAN_MODE) == true) Scanner.MODE_DEFAULT else Scanner.MODE_IMAGE,
            if (intent.extras?.get(URI_DATA) != null) MediaStore.Images.Media.getBitmap(
                this.contentResolver,
                intent.extras?.getParcelable(URI_DATA)
            ) else null,
            object : ScannerListener {
                override fun onDetected(detections: String) {
                    Log.e(TAG, "onDetected: $detections")

                    scanOutput = detections.filter { !it.isWhitespace() }.apply { lowercase() }
                    if (scanOutput.contains('='))
                        scanOutput = scanOutput.split('=')[0]

                    binding.scanText.text = scanOutput

                }

                override fun onStateChanged(state: String, i: Int) {
                    Log.e(TAG, "onStateChanged: state: $state, code: $i")
                }
            })

        binding.scanButtonTake.setOnClickListener {
            Intent().apply {

                if (this@ScanActivity::scanOutput.isInitialized &&
                    "[1-9-+/:x=]+".toRegex().matches(scanOutput)
                ) {

                    putExtra(SCAN_DATA, scanOutput)

                    Log.e(TAG, "onDetected: before sent ${this.extras}")

                    setResult(Activity.RESULT_OK, this)
                    finish()
                } else {
                    Toast.makeText(
                        this@ScanActivity,
                        "Ekspresi tidak valid",
                        Toast.LENGTH_SHORT
                    ).show()

                    if (binding.scanText.text.equals("output: %s")) {
                        setResult(Activity.RESULT_CANCELED)
                        finish()
                    }
                }
            }
        }
    }
}