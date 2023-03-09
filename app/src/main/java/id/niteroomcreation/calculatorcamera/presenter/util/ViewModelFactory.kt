package id.niteroomcreation.calculatorcamera.presenter.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.niteroomcreation.calculatorcamera.di.Injector
import id.niteroomcreation.calculatorcamera.presenter.feature.main.MainViewModel

/**
 * Created by Septian Adi Wijaya on 22/02/2023.
 * please be sure to add credential if you use people's code
 */
class ViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    companion object {

        @Volatile
        private lateinit var instance: ViewModelFactory

        @Synchronized
        fun getInstance(context: Context): ViewModelFactory {
            instance = ViewModelFactory(context)
            return instance
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(Injector.provideRepository(context)) as T

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}