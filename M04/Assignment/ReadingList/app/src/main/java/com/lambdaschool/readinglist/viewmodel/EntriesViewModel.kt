package com.lambdaschool.readinglist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdaschool.readinglist.model.Book
import com.lambdaschool.readinglist.repo

// Create a ViewModel for entries
class EntriesViewModel : ViewModel() {

    // Create a LiveData object for the entries
    val entries : LiveData<List<Book>> by lazy {
        readAllEntries()
    }

    // Recreate the repo calls to as functions here.
    fun createEntry(entry: Book) {
        repo.createEntry(entry)
    }

    fun readAllEntries(): LiveData<List<Book>> {
        return repo.readAllEntries()
    }

    fun updateEntry(entry: Book) {
        repo.updateEntry(entry)
    }
}