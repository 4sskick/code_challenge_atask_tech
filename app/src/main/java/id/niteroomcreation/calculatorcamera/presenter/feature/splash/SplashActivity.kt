package id.niteroomcreation.calculatorcamera.presenter.feature.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import id.niteroomcreation.calculatorcamera.R
import id.niteroomcreation.calculatorcamera.presenter.util.NavUtils

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_splash)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            NavUtils.gotoMain(this@SplashActivity)
        }, 1000)
    }
}