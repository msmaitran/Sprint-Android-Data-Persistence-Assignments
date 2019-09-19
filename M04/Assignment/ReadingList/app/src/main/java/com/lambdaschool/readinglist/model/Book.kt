package com.lambdaschool.readinglist.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.lang.NumberFormatException

// Annotate the Entity
@Entity
class Book : Serializable {

    companion object {
        const val TAG = "Book"
        const val INVALID_ID = 0

        fun createBook(): Book {
            return Book(Book.INVALID_ID)
        }
    }

    var title: String? = null
    var reasonToRead: String? = null
    var hasBeenRead: Boolean = false

    // Make the id the primary key
    @PrimaryKey(autoGenerate = true) @NonNull
    var id: Int = 0

    constructor(id: Int) {
        this.title = ""
        this.reasonToRead = ""
        this.hasBeenRead = false
        this.id = id
    }

    constructor(jsonObject: JSONObject) {
        try {
            this.title = jsonObject.getString("title")
        } catch (e: JSONException) {
            this.title = ""
        }
        try {
            this.reasonToRead = jsonObject.getString("reason_to_read")
        } catch (e: JSONException) {
            this.reasonToRead = ""
        }
        try {
            this.hasBeenRead = jsonObject.getBoolean("has_been_read")
        } catch (e: JSONException) {
            this.hasBeenRead = false
        }
        try {
            this.id = jsonObject.getInt("id")
        } catch (e: JSONException) {
            this.id = 0
        }
    }

    fun toJsonObject(): JSONObject? {
        try {
            return JSONObject().apply {
                put("title", title)
                put("reason_to_read", reasonToRead)
                put("has_been_read", hasBeenRead)
                put("id", id)
            }
        } catch (e1: JSONException) {
            return try {
                JSONObject("{\"title\" : \"$title\", \"reason_to_read\" : \"$reasonToRead\", \"has_been_read\" : \"$hasBeenRead\", \"id\" : \"$id\"}")
            } catch (e2: JSONException) {
                e2.printStackTrace()
                return null
            }
        }
    }

    constructor(csvString: String) {
        val values = csvString.split(",")
        if (values.size == 4) {
            this.title = values[0]
            this.reasonToRead = values[1].replace("~@", ",")
            this.hasBeenRead = values[2].toBoolean()
            try {
                this.id = Integer.parseInt(values[3])
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
        }
    }

    internal fun toCsvString(): String {
        return String.format(
            "%s,%s,%b,%d",
            title,
            reasonToRead?.replace(",", "~@"),
            hasBeenRead,
            id
        )
    }
}