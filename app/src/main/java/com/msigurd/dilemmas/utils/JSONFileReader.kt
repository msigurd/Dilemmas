package com.msigurd.dilemmas.utils

import android.content.res.Resources
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
