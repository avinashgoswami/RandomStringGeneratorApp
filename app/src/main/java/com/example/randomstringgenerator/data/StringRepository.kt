package com.example.randomstringgenerator.data

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import com.example.randomstringgenerator.model.RandomString
import org.json.JSONObject

class StringRepository(private val contentResolver: ContentResolver) {

    private val uri = Uri.parse("content://com.iav.contestdataprovider/text")

    fun getRandomString(maxLength: Int): RandomString? {
        val bundle = Bundle().apply {
            putInt(ContentResolver.QUERY_ARG_LIMIT, maxLength)
        }

        val cursor = contentResolver.query(uri, null, bundle, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val json = it.getString(it.getColumnIndexOrThrow("data"))
                val jsonObject = JSONObject(json).getJSONObject("randomText")

                return RandomString(
                    value = jsonObject.getString("value"),
                    length = jsonObject.getInt("length"),
                    created = jsonObject.getString("created")
                )
            }
        }
        return null
    }
}