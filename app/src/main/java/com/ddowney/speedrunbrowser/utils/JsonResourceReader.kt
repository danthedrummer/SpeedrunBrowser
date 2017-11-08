package com.ddowney.speedrunbrowser.utils

import android.content.res.Resources
import android.util.Log
import java.io.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Solution taken from:
 * https://stackoverflow.com/questions/6349759/using-json-file-in-android-app-resources
 *
 * Read from a resources file and create a [JsonResourceReader] object that
 * will allow the creation of objects from this source.
 *
 * @param resources An application {@link Resources} object
 * @param id The id of the resource to be loaded
 */
class JsonResourceReader (resources : Resources, val id : Int, val gson : Gson) {
    val LOG_TAG = "JsonResourceReader"
    private var jsonString : String

    init {
        val resourceReader : InputStream = resources.openRawResource(id)
        val writer : Writer = StringWriter()

        try {
            val reader = BufferedReader(InputStreamReader(resourceReader, "UTF-8"))
            var line : String? = reader.readLine()
            while (line != null) {
                writer.write(line)
                line = reader.readLine()
            }
        } catch (e : IOException) {
            Log.e(LOG_TAG, "Exception using JsonResourceReader", e)
        } finally {
            try {
                resourceReader.close()
            } catch (e : IOException) {

            }
        }

        jsonString = writer.toString()
    }

    /**
     * Builds a Java object from the specified JSON resource
     *
     * Currently this method is only being used to parse a json file where the root is a
     * [ResponseWrapperM] containing a list of [BulkGameModel]
     *
     * @param token Typetoken describing to type to be parsed into
     *
     * @return An object of type TypeToken<T> populated by Gson
     */
    fun <T> constructUsingGson(token : TypeToken<T>): T {
        return gson.fromJson(jsonString, token.type)
    }
}