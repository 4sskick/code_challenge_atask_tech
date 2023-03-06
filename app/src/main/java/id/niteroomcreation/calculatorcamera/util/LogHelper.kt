package id.niteroomcreation.archcomponent.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.*

/**
 * Created by monta on 05/05/21
 * please make sure to use credit when using people code
 */
object LogHelper {
    private val STACK_TRACE_LEVELS_UP = 5
    private val LOGGING_ENABLED = true

    @JvmStatic
    fun e(tag: String?) {
        if (LOGGING_ENABLED) {
            Log.e(
                tag, """
     ${getClassNameMethodNameAndLineNumber()}
     
     
     """.trimIndent()
            )
        }
    }

    @JvmStatic
    fun e(tag: String?, message: Any) {
        if (LOGGING_ENABLED) {
            Log.e(
                tag, """
     ${getClassNameMethodNameAndLineNumber()}, $message
     
     """.trimIndent()
            )
        }
    }

    @JvmStatic
    fun e(tag: String?, vararg message: Any?) {
        if (LOGGING_ENABLED) {
            Log.e(
                tag, """
     ${getClassNameMethodNameAndLineNumber()}, ${Arrays.toString(message)}
     
     """.trimIndent()
            )
        }
    }

    //for a pretty print log
    @JvmStatic
    fun j(tag: String?) {
        if (LOGGING_ENABLED) {
            Log.e(
                tag, """
     ${getClassNameMethodNameAndLineNumber()}
     
     
     """.trimIndent()
            )
        }
    }

    private val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    @JvmStatic
    fun j(tag: String?, message: Any?) {
        if (LOGGING_ENABLED) {
            Log.e(
                tag, """
     ${getClassNameMethodNameAndLineNumber()}, ${gson.toJson(message)}
     
     """.trimIndent()
            )
        }
    }

    @JvmStatic
    fun j(tag: String?, vararg message: Any?) {
        if (LOGGING_ENABLED) {
            Log.e(
                tag, """
     ${getClassNameMethodNameAndLineNumber()}, ${gson.toJson(message)}
     
     """.trimIndent()
            )
        }
    }


    /**
     * Get the current line number. Note, this will only work as called from
     * this class as it has to go a predetermined number of steps up the stack
     * trace. In this case 5.
     *
     * @return int - Current line number.
     */
    private fun getLineNumber(): Int {
        return Thread.currentThread().stackTrace[STACK_TRACE_LEVELS_UP].lineNumber
    }

    /**
     * Get the current method name. Note, this will only work as called from
     * this class as it has to go a predetermined number of steps up the stack
     * trace. In this case 5.
     *
     * @return String - Current line number.
     */
    private fun getMethodName(): String {
        return Thread.currentThread().stackTrace[STACK_TRACE_LEVELS_UP].methodName
    }

    /**
     * Returns the class name, method name, and line number from the currently
     * executing log call in the form <class_name>.<method_name>()-<line_number>
     *
     * @return String - String representing class name, method name, and line
     * number.
    </line_number></method_name></class_name> */
    private fun getClassNameMethodNameAndLineNumber(): String {
        return "Line " + getLineNumber() + ", " + getMethodName() + "()"
    }
}