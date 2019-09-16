package com.lambdaschool.readinglist.model

import java.lang.NumberFormatException

class Book {

    var title: String? = null
    var reasonToRead: String? = null
    var hasBeenRead: Boolean = false
    var id: Int = 0

    constructor(title: String, reasonToRead: String, hasBeenRead: Boolean, id: Int) {
        this.title = title
        this.reasonToRead = reasonToRead
        this.hasBeenRead = hasBeenRead
        this.id = id
    }

    constructor(csvString: String) {
        val values = csvString.split(",")
        if (values.size == 4) {
            this.title = values[0]
            this.reasonToRead = values[1]
            this.hasBeenRead = values[2].toBoolean()
            try {
                this.id = Integer.parseInt(values[3])
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
        }
    }

    fun toCsvString(): String {
        return "$title,$reasonToRead,$hasBeenRead,$id"
    }

}