package id.niteroomcreation.calculatorcamera.data.sources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import id.niteroomcreation.calculatorcamera.data.entity.InOut
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by Septian Adi Wijaya on 09/03/2023.
 * please be sure to add credential if you use people's code
 */
@Database(entities = [InOut::class], version = 1, exportSchema = true)
abstract class CalculatorCameraDatabase : RoomDatabase() {

    abstract fun inOutDao(): InOutDAO

    companion object {
        @Volatile
        private var instances: CalculatorCameraDatabase? = null

        fun getInstance(
            context: Context,
            openHelperFactory: SupportSQLiteOpenHelper.Factory?
        ): CalculatorCameraDatabase =
            instances ?: synchronized(this) {
                instances ?: buildDB(context, openHelperFactory).also { instances = it }
            }

        private fun buildDB(
            context: Context,
            openHelperFactory: SupportSQLiteOpenHelper.Factory?
        ): CalculatorCameraDatabase {

            val builder = Room.databaseBuilder(
                context.applicationContext,
                CalculatorCameraDatabase::class.java,
                "calculator_camera.db"
            ).addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    GlobalScope.launch {
                        val appDB = getInstance(context, openHelperFactory)
                        appDB.inOutDao().insert(prepopulateDB())
                    }
                }
            })

            //paraphrase master key DB
//            builder.openHelperFactory(SupportFactory(SQLiteDatabase.getBytes("rahasia".toCharArray())))
            builder.openHelperFactory(openHelperFactory)
            return builder.build()
        }

        private fun prepopulateDB() = listOf(
            InOut(id = 1, inStr = "2+4", outStr = "6"),
            InOut(id = 2, inStr = "34x6", outStr = "204"),
            InOut(id = 3, inStr = "25-9", outStr = "16"),
        )
    }
}