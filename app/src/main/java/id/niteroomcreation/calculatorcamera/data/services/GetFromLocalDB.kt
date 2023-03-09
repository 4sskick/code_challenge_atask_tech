package id.niteroomcreation.calculatorcamera.data.services

import id.niteroomcreation.calculatorcamera.domain.entity.InOutModel

/**
 * Created by Septian Adi Wijaya on 09/03/2023.
 * please be sure to add credential if you use people's code
 * for post a list of some entities from local DB
 */
class GetFromLocalDB {

    companion object {
        val TAG = GetFromLocalDB::class.java.simpleName
    }

    fun getDataItems(): List<InOutModel> {
        return emptyList()
    }

}