package id.niteroomcreation.calculatorcamera.domain.repositories

import androidx.lifecycle.LiveData
import id.niteroomcreation.calculatorcamera.domain.data.InOut

/**
 * Created by Septian Adi Wijaya on 08/03/2023.
 * please be sure to add credential if you use people's code
 */
interface RepositoryImpl {

    suspend fun getFromDB(): List<InOut>
    suspend fun getFromInternal(): List<InOut>
}