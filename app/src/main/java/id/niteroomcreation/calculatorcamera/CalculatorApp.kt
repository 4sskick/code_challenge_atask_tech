package id.niteroomcreation.calculatorcamera

import android.app.Application
import android.content.Context

/**
 * Created by Septian Adi Wijaya on 08/03/2023.
 * please be sure to add credential if you use people's code
 */
class CalculatorApp:Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {

        private lateinit var context: Context

        fun getContext() = context
    }
}