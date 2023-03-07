package id.niteroomcreation.calculatorcamera.domain.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Septian Adi Wijaya on 22/02/2023.
 * please be sure to add credential if you use people's code
 */
data class InOut(
    @SerializedName("inStr")
    val inStr: String,
    @SerializedName("outStr")
    val outStr: String
)
