package id.niteroomcreation.calculatorcamera.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.niteroomcreation.calculatorcamera.data.entity.InOut

/**
 * Created by Septian Adi Wijaya on 09/03/2023.
 * please be sure to add credential if you use people's code
 */
@Dao
interface InOutDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<InOut>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: InOut)

    @Query("select * from in_out")
    suspend fun getAll(): List<InOut>
}