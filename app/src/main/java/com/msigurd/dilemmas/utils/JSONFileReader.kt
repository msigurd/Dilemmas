package com.msigurd.dilemmas.utils

import android.content.res.Resources
import androidx.compose.ui.text.intl.Locale
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

fun readObjectsDataFromJSONFile(res: Resources, resourceFile: Int): String {
    var oneLine: String?
    var entireFile = StringBuilder()

    try {
        val inputStream = res.openRawResource(resourceFile)
        val reader = BufferedReader(InputStreamReader(inputStream))

        while (reader.readLine().also { oneLine = it } != null)
            entireFile = entireFile.append(oneLine)

    } catch (e: IOException) {
        e.printStackTrace()
    }
    return entireFile.toString()
}

fun getTranslatedString(jsonObject: JSONObject, key: String): String {
    val jsonOption = jsonObject.optJSONObject(key)

    return if (Locale.current.language == "nb") {
        jsonOption?.optString("nb") ?: ""
    } else {
        jsonOption?.optString("en") ?: ""
    }
}
