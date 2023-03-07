package id.niteroomcreation.calculatorcamera.domain.repositories

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import id.niteroomcreation.archcomponent.util.LogHelper
import id.niteroomcreation.calculatorcamera.domain.data.InOut
import id.niteroomcreation.calculatorcamera.domain.di.Injector
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.*

/**
 * Created by Septian Adi Wijaya on 08/03/2023.
 * please be sure to add credential if you use people's code
 */
class Repository : RepositoryImpl {

    companion object {
        val TAG = Repository::class.java.simpleName
    }

    override suspend fun getFromDB(): List<InOut> {
        return emptyList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getFromInternal(): List<InOut> {

        //load data to read from file
        //if doesn't create yet, init process & return empty
        //otherwise, read data -> decode from base 64 -> json mapping into list -> return

        var result = emptyList<InOut>()
        var gson = GsonBuilder().create()

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
            val itemType = object : TypeToken<List<InOut>>() {}.type
            result = gson.fromJson<List<InOut>>(decodeValue, itemType)

            LogHelper.e(TAG, "result gonna be", result)

        } catch (e: FileNotFoundException) {
            //if null because file not created yet, then create it
            //by write an empty
            try {
                val fileOutputStream = Injector.provideContext()
                    .openFileOutput("internal.txt", Context.MODE_PRIVATE)

                //convert into json
                val temp = gson.toJson(
                    listOf<InOut>(
                        InOut("2+3", "5"),
                        InOut("3+4", "7"),
                        InOut("56x4", "224"),
                    )
                )


//                val encodeValue = Base64.getEncoder().encodeToString(temp.toByteArray())
                val encodeValue =
                    android.util.Base64.encode(
                        temp.toByteArray(),
                        android.util.Base64.DEFAULT
                    )
//                fileOutputStream.write("".toByteArray())
                fileOutputStream.write(encodeValue)

                LogHelper.e(TAG, "list gonna encode", temp, "encode to", encodeValue)

            } catch (e1: FileNotFoundException) {
                e1.printStackTrace()
            } catch (e2: Exception) {
                e2.printStackTrace()
            }
        }

        return result
    }
}