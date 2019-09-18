package com.lambdaschool.readinglist

import com.lambdaschool.readinglist.model.Book

// Extract behavior from Prefs to an interface
interface BookRepoInterface {

    fun createEntry(entry: Book)
    fun readAllEntries(): MutableList<Book>
    fun updateEntry(entry: Book)
    fun deleteEntry(entry: Book)
}