package id.niteroomcreation.calculatorcamera.custom.scanner

/**
 * Created by Septian Adi Wijaya on 02/03/2023.
 * please be sure to add credential if you use people's code
 */
interface ScannerListener {

    fun onDetected(detections:String)
    fun onStateChanged(state:String, i:Int)

}