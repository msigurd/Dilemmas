package com.msigurd.dilemmas.model

import android.content.res.Resources
import com.msigurd.dilemmas.R
import com.msigurd.dilemmas.utils.readObjectsDataFromJSONFile
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

data class Dilemma(
    val id: Int = -1,
    val optionOne: String = "",
    val optionTwo: String = "",
    val categories: JSONArray? = null
) {
    constructor(jsonDilemma: JSONObject) :
        this(
            id = jsonDilemma.optInt("id"),
            optionOne = jsonDilemma.optString("optionOne"),
            optionTwo = jsonDilemma.optString("optionTwo"),
            categories = jsonDilemma.optJSONArray("categories")
        )

    companion object {
        @JvmStatic
        @Throws(JSONException::class, NullPointerException::class)
        fun createDilemmasList(res: Resources): ArrayList<Dilemma> {
            val dilemmasList = ArrayList<Dilemma>()
            try {
                val jsonData: String = readObjectsDataFromJSONFile(res, R.raw.dilemmas)
                val jsonAllDilemmas = JSONObject(jsonData)
                val jsonDilemmasArray = jsonAllDilemmas.optJSONArray("dilemmas")

                for (i in 0 until jsonDilemmasArray.length())
                    dilemmasList.add(Dilemma(jsonDilemmasArray[i] as JSONObject))

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return dilemmasList
        }

    }

}
