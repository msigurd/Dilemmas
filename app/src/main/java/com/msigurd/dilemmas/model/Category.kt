package com.msigurd.dilemmas.model

import android.content.res.Resources
import com.msigurd.dilemmas.R
import com.msigurd.dilemmas.utils.getTranslatedString
import com.msigurd.dilemmas.utils.readObjectsDataFromJSONFile
import org.json.JSONException
import org.json.JSONObject

data class Category(
    val id: Int = -1,
    val name: String = ""
) {
    constructor(jsonCategory: JSONObject) :
        this(
            id = jsonCategory.optInt("id"),
            name = getTranslatedString(jsonCategory, "name")
        )

    companion object {
        @JvmStatic
        @Throws(JSONException::class, NullPointerException::class)
        fun createCategoriesList(res: Resources): ArrayList<Category> {
            val categoriesList = ArrayList<Category>()
            try {
                val jsonData: String = readObjectsDataFromJSONFile(res, R.raw.categories)
                val jsonAllCategories = JSONObject(jsonData)
                val jsonCategoriesArray = jsonAllCategories.optJSONArray("categories")

                for (i in 0 until jsonCategoriesArray.length())
                    categoriesList.add(Category(jsonCategoriesArray[i] as JSONObject))

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return categoriesList
        }

    }

}
