package id.niteroomcreation.calculatorcamera.presenter.feature.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.niteroomcreation.calculatorcamera.domain.entity.InOutModel
import id.niteroomcreation.calculatorcamera.domain.repositories.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Septian Adi Wijaya on 22/02/2023.
 * please be sure to add credential if you use people's code
 */
class MainViewModel(private val repo: RepositoryImpl) : ViewModel() {

    private var data_ = MutableLiveData<List<InOutModel>>()
    var data = data_

    init {
        getData()
    }

    fun dataInternal() {
        viewModelScope.launch(Dispatchers.IO) {
            data_.postValue(repo.getFromInternal())
        }
    }

    fun dataDB() {
        viewModelScope.launch(Dispatchers.IO) {
            data_.postValue(repo.getFromDB())
        }
    }

    fun postDataDB(inStr: String, outStr: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.postToDB(inStr, outStr)
            dataDB()
        }
    }

    fun postInternal(inStr: String, outStr: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.postToInternal(inStr, outStr)
            dataInternal()
        }
    }

    private fun getData() {
        data_.value = listOf(
            InOutModel(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOutModel(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOutModel(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOutModel(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOutModel(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOutModel(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOutModel(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOutModel(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOutModel(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOutModel(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
            InOutModel(String.format("Input %s", "2+3"), String.format("Output %s", 5)),
        )
    }
}