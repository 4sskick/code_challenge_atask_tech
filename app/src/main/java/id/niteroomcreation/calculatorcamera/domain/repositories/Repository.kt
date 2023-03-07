package id.niteroomcreation.calculatorcamera.domain.repositories

import id.niteroomcreation.calculatorcamera.domain.data.InOut

/**
 * Created by Septian Adi Wijaya on 08/03/2023.
 * please be sure to add credential if you use people's code
 */
class Repository : RepositoryImpl {

    override suspend fun getFromDB(): List<InOut> {
        return emptyList()
    }

    override suspend fun getFromInternal(): List<InOut> {
        return emptyList()
    }
}