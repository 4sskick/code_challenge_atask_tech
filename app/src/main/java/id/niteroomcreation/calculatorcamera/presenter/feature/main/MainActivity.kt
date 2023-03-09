package id.niteroomcreation.calculatorcamera.presenter.feature.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.niteroomcreation.archcomponent.util.LogHelper
import id.niteroomcreation.calculatorcamera.databinding.AMainBinding
import id.niteroomcreation.calculatorcamera.presenter.feature.scan.ScanActivity
import id.niteroomcreation.calculatorcamera.presenter.util.ViewModelFactory

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var selectedModeDataLoad: RadioButton

    private val mainViewModel by lazy {
        ViewModelProvider(
            owner = this,
            ViewModelFactory.getInstance(this)
        ).get(MainViewModel::class.java)
    }

    private lateinit var binding: AMainBinding
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedModeDataLoad = binding.mainLayoutRadioGroup
            .findViewById(binding.mainLayoutRadioGroup.checkedRadioButtonId) as RadioButton

        setupObserver()
        setupAdapter()

        binding.mainLayoutRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            selectedModeDataLoad = findViewById<RadioButton>(checkedId)

            if (selectedModeDataLoad.text.contains("storage")) {
                mainViewModel.dataInternal()
            } else
                mainViewModel.dataDB()
        }

        val scanRequest: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

                if (it.resultCode == Activity.RESULT_OK) {
                    var data: String = it.data?.extras?.get(ScanActivity.SCAN_DATA) as String

                    LogHelper.e(TAG, "before: data $data")

                    try {

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

                        } else if (data.contains('/')) {
                            firstNum = data.split('/')[0].toInt()
                            secNum = data.split('/')[1].toInt()
                            result = Math.floorDiv(firstNum, secNum)
                        }

                        LogHelper.e(TAG, "onCreate: result gonna be $result")
                        Toast.makeText(this, "result gonna be $result", Toast.LENGTH_LONG)
                            .show()

                        //expect everything that no crash is a success operation even the result is zero or expression's result not correct
                        //begin to post
                        if (selectedModeDataLoad.text.contains("storage")) {
                            mainViewModel.postInternal(
                                inStr = data,
                                outStr = "$result"
                            )
                        } else {
                            mainViewModel.postDataDB(
                                inStr = data,
                                outStr = "$result"
                            )
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this, "Failed to calculate, try simpler expression e.g (2+3)", Toast.LENGTH_LONG).show()
                    }

                } else
                    Toast.makeText(this, "Scan CANCELED", Toast.LENGTH_LONG).show()
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

            LogHelper.e(TAG, it)

            adapter.submit(it)
            adapter.notifyDataSetChanged()
        })

        if (selectedModeDataLoad.text.contains("storage")) {
            //gonna load data from file been written on internal dir 'data'
            mainViewModel.dataInternal()
        } else
            mainViewModel.dataDB()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        return super.onCreateOptionsMenu(menu)
    }
}