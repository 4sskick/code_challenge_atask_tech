package id.niteroomcreation.calculatorcamera.feature.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.niteroomcreation.calculatorcamera.domain.InOut

/**
 * Created by Septian Adi Wijaya on 22/02/2023.
 * please be sure to add credential if you use people's code
 */
class MainViewModel : ViewModel() {

    private var data_ = MutableLiveData<List<InOut>>()
    var data = data_

    init {
        getData()
    }

    private fun getData() {
        data_.value = listOf(
            InOut(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOut(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOut(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOut(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOut(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOut(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOut(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOut(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOut(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOut(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOut(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
        )
    }
}