package id.niteroomcreation.calculatorcamera.domain.repositories

import id.niteroomcreation.calculatorcamera.domain.entity.InOut

/**
 * Created by Septian Adi Wijaya on 08/03/2023.
 * please be sure to add credential if you use people's code
 */
interface RepositoryImpl {

    suspend fun getFromDB(): List<InOut>
    suspend fun getFromInternal(): ArrayList<InOut>
    suspend fun postToDB()
    suspend fun postToInternal(inStr: String, outStr: String)
}