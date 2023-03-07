package id.niteroomcreation.calculatorcamera.feature.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.niteroomcreation.archcomponent.util.LogHelper
import id.niteroomcreation.calculatorcamera.databinding.AMainBinding
import id.niteroomcreation.calculatorcamera.feature.scan.ScanActivity
import id.niteroomcreation.calculatorcamera.util.ViewModelFactory

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private val mainViewModel by lazy {
        ViewModelProvider(
            owner = this,
            ViewModelFactory.getInstance()
        ).get(MainViewModel::class.java)
    }

    private lateinit var binding: AMainBinding
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupObserver()
        setupAdapter()


        val scanRequest: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

                if (it.resultCode == Activity.RESULT_OK) {
                    var data: String = it.data?.extras?.get(ScanActivity.SCAN_DATA) as String

                    LogHelper.e(TAG, "before: data $data data contain '=' ${data.contains('=')}")

                    if (data.contains('=')) {
                        data = data.split('=')[0]

                        LogHelper.e(TAG, "proceed into $data")
                    }

                    LogHelper.e(TAG, "after data $data")

                    //begin to process expression
                    var firstNum = 0
                    var secNum = 0
                    var result = 0

                    if (data.contains('+')) {
                        firstNum = data.split('+')[0].toInt()
                        secNum = data.split('+')[1].toInt()
                        result = Math.addExact(firstNum, secNum)

                    } else if (data.contains('-')) {
                        firstNum = data.split('-')[0].toInt()
                        secNum = data.split('-')[1].toInt()
                        result = Math.subtractExact(firstNum, secNum)

                    } else if (data.contains('x')) {

                        firstNum = data.split('x')[0].toInt()
                        secNum = data.split('x')[1].toInt()
                        result = Math.multiplyExact(firstNum, secNum)

                    } else if (data.contains(':')) {
                        firstNum = data.split(':')[0].toInt()
                        secNum = data.split(':')[1].toInt()
                        result = Math.floorDiv(firstNum, secNum)
                    }

                    LogHelper.e(TAG, "onCreate: result gonna be $result")
                    Toast.makeText(this, "result gonna be $result", Toast.LENGTH_LONG).show()

                } else {
                    LogHelper.e(TAG, "onCreate: CANCELED")
                    Toast.makeText(this, "Scan CANCELED", Toast.LENGTH_LONG).show()
                }
            }

        val imageRequest = registerForActivityResult(ActivityResultContracts.GetContent()) {
            LogHelper.e(TAG, it)

            scanRequest.launch(Intent(this, ScanActivity::class.java).apply {
                putExtra(ScanActivity.SCAN_MODE, false)
                putExtra(ScanActivity.URI_DATA, it)
            })
        }

        binding.mainBtnLoadOperationCamera.setOnClickListener {
            scanRequest.launch(Intent(this, ScanActivity::class.java).apply {
                putExtra(ScanActivity.SCAN_MODE, true)
            })
        }

        binding.mainBtnLoadOperationGallery.setOnClickListener {
            imageRequest.launch("image/*")
        }

    }

    private fun setupAdapter() {
        adapter = MainAdapter(emptyList())

        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter

    }

    private fun setupObserver() {
        mainViewModel.data.observe(this, Observer {

            Log.e(TAG, "setupObserver: " + it)

            adapter.submit(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        return super.onCreateOptionsMenu(menu)
    }
}