package id.niteroomcreation.calculatorcamera.di

import android.content.Context
import id.niteroomcreation.calculatorcamera.CalculatorApp
import id.niteroomcreation.calculatorcamera.data.repositories.Repository
import id.niteroomcreation.calculatorcamera.data.sources.local.CalculatorCameraDatabase

/**
 * Created by Septian Adi Wijaya on 08/03/2023.
 * please be sure to add credential if you use people's code
 */
object Injector {

    fun provideRepository(context: Context): Repository {
        val database = CalculatorCameraDatabase.getInstance(
            context = context,
            openHelperFactory = null
        )
        return Repository(database)
    }

    fun provideContext(): Context {
        return CalculatorApp.getContext()
    }
}