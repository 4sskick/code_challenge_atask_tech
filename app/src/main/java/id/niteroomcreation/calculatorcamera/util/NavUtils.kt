package id.niteroomcreation.calculatorcamera.util

import android.app.Activity
import android.content.Intent
import id.niteroomcreation.calculatorcamera.feature.main.MainActivity

/**
 * Created by Septian Adi Wijaya on 22/02/2023.
 * please be sure to add credential if you use people's code
 */
object NavUtils {

    fun gotoMain(act: Activity) {
        act.finish().also {
            act.startActivity(Intent(act, MainActivity::class.java))
        }
    }
}