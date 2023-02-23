package id.niteroomcreation.calculatorcamera.feature.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.niteroomcreation.calculatorcamera.databinding.AMainBinding
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