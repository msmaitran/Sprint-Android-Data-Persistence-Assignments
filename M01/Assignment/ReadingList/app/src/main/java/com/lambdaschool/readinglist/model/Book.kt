package com.lambdaschool.readinglist.model

import java.io.Serializable
import java.lang.NumberFormatException

class Book : Serializable {

    companion object {
        const val TAG = "Book"
        const val INVALID_ID = -1

        fun createBook(): Book {
            return Book(Book.INVALID_ID)
        }

        fun createBook(text: String): Book {
            val entry = createBook()
            entry.title = text
            return entry
        }
    }

    var title: String? = null
    var reasonToRead: String? = null
    var hasBeenRead: Boolean = false
    var id: Int = 0

    constructor(id: Int) {
        this.title = title
        this.reasonToRead = reasonToRead
        this.hasBeenRead = hasBeenRead
        this.id = id
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

    override fun toString(): String {
        return "Book Entry:\nTitle:$title\nReason to Read:$reasonToRead\nRead:$hasBeenRead\nID: $id"
    }
}