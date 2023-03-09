package id.niteroomcreation.calculatorcamera.domain.repositories

import id.niteroomcreation.calculatorcamera.domain.entity.InOutModel

/**
 * Created by Septian Adi Wijaya on 08/03/2023.
 * please be sure to add credential if you use people's code
 */
interface RepositoryImpl {

    suspend fun getFromDB(): List<InOutModel>
    suspend fun getFromInternal(): ArrayList<InOutModel>
    suspend fun postToDB(inStr: String, outStr: String)
    suspend fun postToInternal(inStr: String, outStr: String)
}