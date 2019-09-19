package com.lambdaschool.readinglist

import androidx.lifecycle.LiveData
import com.lambdaschool.readinglist.model.Book

// Database repo to implement this interface
interface BookRepoInterface {

    fun createEntry(entry: Book)
    // LiveData in the interface
    fun readAllEntries(): LiveData<List<Book>>
    fun updateEntry(entry: Book)
    fun deleteEntry(entry: Book)
}