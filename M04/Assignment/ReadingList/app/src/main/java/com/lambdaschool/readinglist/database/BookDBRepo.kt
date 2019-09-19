package com.lambdaschool.readinglist.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.lambdaschool.readinglist.BookRepoInterface
import com.lambdaschool.readinglist.model.Book

// Create the Database repo and implement the methods
class BookDBRepo(val context: Context) : BookRepoInterface {
    override fun createEntry(entry: Book) {
        database.bookDao().createEntry(entry)
    }

    override fun readAllEntries(): LiveData<List<Book>> {
        return database.bookDao().readAllEntries()
    }

    override fun updateEntry(entry: Book) {
        database.bookDao().updateEntry(entry)
    }

    override fun deleteEntry(entry: Book) {
        database.bookDao().deleteEntry(entry)
    }

    // Build the Room database
    private val database by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            BookDB::class.java, "entry_database"
        ).fallbackToDestructiveMigration().build()
    }
}