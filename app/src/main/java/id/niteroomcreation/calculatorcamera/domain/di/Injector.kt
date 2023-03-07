package id.niteroomcreation.calculatorcamera.domain.di

import android.content.Context
import id.niteroomcreation.calculatorcamera.CalculatorApp
import id.niteroomcreation.calculatorcamera.domain.repositories.Repository

/**
 * Created by Septian Adi Wijaya on 08/03/2023.
 * please be sure to add credential if you use people's code
 */
object Injector {

    fun provideRepository(): Repository {
        return Repository()
    }

    fun provideContext(): Context {
        return CalculatorApp.getContext()
    }
}