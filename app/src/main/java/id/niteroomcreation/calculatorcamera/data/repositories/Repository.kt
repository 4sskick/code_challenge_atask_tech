package id.niteroomcreation.calculatorcamera.data.repositories

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import id.niteroomcreation.archcomponent.util.LogHelper
import id.niteroomcreation.calculatorcamera.data.sources.local.CalculatorCameraDatabase
import id.niteroomcreation.calculatorcamera.di.Injector
import id.niteroomcreation.calculatorcamera.domain.entity.InOut
import id.niteroomcreation.calculatorcamera.domain.repositories.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.*

/**
 * Created by Septian Adi Wijaya on 08/03/2023.
 * please be sure to add credential if you use people's code
 */
class Repository(private val database: CalculatorCameraDatabase) : RepositoryImpl {

    companion object {
        val TAG = Repository::class.java.simpleName
    }

    var gson = GsonBuilder().create()

    override suspend fun getFromDB(): List<InOut> {
        return database.inOutDao()
            .getAll()
            .map {
                InOut(inStr = it.inStr, outStr = it.outStr)
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getFromInternal(): ArrayList<InOut> {

        //load data to read from file
        //if doesn't create yet, init process & return empty
        //otherwise, read data -> decode from base 64 -> json mapping into list -> return

        var result = ArrayList<InOut>()

        try {
            var fileInputStream = Injector.provideContext().openFileInput("internal.txt")
            var inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            var stringBuilder = StringBuilder()
            var stringContent: String? = null

            while (
                { stringContent = bufferedReader.readLine(); stringContent }
                    () != null) {
                stringBuilder.append(stringContent)
            }

            //decode base 64
//            val decodeValue = String(Base64.getDecoder().decode(stringBuilder))
            val decodeValue = String(
                android.util.Base64.decode(
                    stringBuilder.toString(),
                    android.util.Base64.DEFAULT
                )
            )

            LogHelper.e(TAG, "file content", stringBuilder, "after decode", decodeValue)

            //json mapping
            val itemType = object : TypeToken<ArrayList<InOut>>() {}.type
            result = gson.fromJson(decodeValue, itemType)

            LogHelper.e(TAG, "result gonna be", result)

        } catch (e: FileNotFoundException) {
            //if null because file not created yet, then create it
            //by write an empty
            try {
                val fileOutputStream = Injector.provideContext()
                    .openFileOutput(
                        "internal.txt",
                        Context.MODE_PRIVATE
                    )

                //convert into json
                result = arrayListOf(
                    InOut("2+3", "5"),
                    InOut("3+4", "7"),
                    InOut("56x4", "224"),
                )
                val temp = gson.toJson(result)

                val encodeValue =
                    android.util.Base64.encode(
                        temp.toByteArray(),
                        android.util.Base64.DEFAULT
                    )

                withContext(Dispatchers.IO) {
                    fileOutputStream.write(encodeValue)
                }

                LogHelper.e(TAG, "list gonna encode", temp, "encode to", encodeValue)

            } catch (e1: FileNotFoundException) {
                e1.printStackTrace()
            } catch (e2: Exception) {
                e2.printStackTrace()
            }
        }

        return result
    }

    override suspend fun postToDB() {
//        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun postToInternal(inStr: String, outStr: String) {
        try {
            var existing = getFromInternal()
            existing.add(InOut(inStr, outStr))

            val temp = gson.toJson(existing)
            val encodeValue =
                android.util.Base64.encode(
                    temp.toByteArray(),
                    android.util.Base64.DEFAULT
                )

            val fileOutputStream = Injector.provideContext()
                .openFileOutput(
                    "internal.txt",
                    Context.MODE_PRIVATE
                )

            withContext(Dispatchers.IO) {
                fileOutputStream.write(encodeValue)
            }

            LogHelper.e(TAG, "list gonna encode", temp, "encode to", encodeValue)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }
}