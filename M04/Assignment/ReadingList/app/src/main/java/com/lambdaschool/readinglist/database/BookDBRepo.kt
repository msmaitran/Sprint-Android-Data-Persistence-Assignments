package com.lambdaschool.readinglist.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.lambdaschool.readinglist.BookRepoInterface
import com.lambdaschool.readinglist.model.Book

// Create the Database repo and implement the methods
class BookDBRepo(val context: Context) : BookRepoInterface {
    override fun createEntry(entry: Book) {

    }

    override fun readAllEntries(): LiveData<List<Book>> {
        return
    }

    override fun updateEntry(entry: Book) {

    }

    override fun deleteEntry(entry: Book) {

    }
}