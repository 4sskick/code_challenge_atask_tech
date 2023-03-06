package id.niteroomcreation.calculatorcamera.feature.scan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import id.niteroomcreation.calculatorcamera.custom.scanner.Scanner
import id.niteroomcreation.calculatorcamera.custom.scanner.ScannerListener
import id.niteroomcreation.calculatorcamera.databinding.AScanBinding

/**
 * Created by Septian Adi Wijaya on 03/03/2023.
 * please be sure to add credential if you use people's code
 */
class ScanActivity : AppCompatActivity() {
    companion object {
        val TAG = ScanActivity::class.java.simpleName
        val SCAN_DATA = "data"
    }

    private lateinit var binding: AScanBinding
    private lateinit var scanOutput: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Scanner(this, binding.scanSurface, object : ScannerListener {
            override fun onDetected(detections: String) {
                Log.e(TAG, "onDetected: $detections")

                scanOutput = detections.filter { !it.isWhitespace() }.apply { lowercase() }
                binding.scanText.text = scanOutput

            }

            override fun onStateChanged(state: String, i: Int) {
                Log.e(TAG, "onStateChanged: state: $state, code: $i")
            }
        })

        binding.scanButtonTake.setOnClickListener {
            Intent().apply {
                putExtra(SCAN_DATA, scanOutput)

                Log.e(TAG, "onDetected: before sent ${this.extras}")

                setResult(Activity.RESULT_OK, this)
                finish()
            }
        }
    }
}