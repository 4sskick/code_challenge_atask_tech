package id.niteroomcreation.calculatorcamera.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Septian Adi Wijaya on 22/02/2023.
 * please be sure to add credential if you use people's code
 */
@Entity(tableName = "in_out")
data class InOut(
    @SerializedName("inStr")
    val inStr: String,
    @SerializedName("outStr")
    val outStr: String
) {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int = 0
}
