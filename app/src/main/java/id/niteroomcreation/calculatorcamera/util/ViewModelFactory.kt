package id.niteroomcreation.calculatorcamera.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.niteroomcreation.calculatorcamera.domain.di.Injector
import id.niteroomcreation.calculatorcamera.feature.main.MainViewModel

/**
 * Created by Septian Adi Wijaya on 22/02/2023.
 * please be sure to add credential if you use people's code
 */
class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    companion object {

        @Volatile
        private lateinit var instance: ViewModelFactory

        @Synchronized
        fun getInstance(): ViewModelFactory {
            instance = ViewModelFactory()
            return instance
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(Injector.provideRepository()) as T

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}