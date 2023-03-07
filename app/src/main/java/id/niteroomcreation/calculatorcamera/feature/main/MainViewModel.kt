package id.niteroomcreation.calculatorcamera.feature.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.niteroomcreation.calculatorcamera.domain.data.InOut
import id.niteroomcreation.calculatorcamera.domain.repositories.RepositoryImpl
import kotlinx.coroutines.launch

/**
 * Created by Septian Adi Wijaya on 22/02/2023.
 * please be sure to add credential if you use people's code
 */
class MainViewModel(private val repo: RepositoryImpl) : ViewModel() {

    private var data_ = MutableLiveData<List<InOut>>()
    var data = data_

    init {
        getData()
    }

    fun dataInternal() {
        viewModelScope.launch {
            data_.postValue(repo.getFromInternal())
        }
    }

    fun dataDB() {
        viewModelScope.launch {
            data_.postValue(repo.getFromDB())
        }
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