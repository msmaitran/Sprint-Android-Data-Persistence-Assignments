package com.lambdaschool.readinglist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lambdaschool.readinglist.model.Book

// Define the DAO with methods from our repo interface
@Dao
interface BookDAO {

    // Insert with onConflict = Replace
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createEntry(entry: Book)

    // Query for all
    @Query("SELECT * FROM book")
    fun readAllEntries(): LiveData<List<Book>> // Return LiveData for VM

    // Update with onConflict = REPLACE
    @Update
    fun updateEntry(entry: Book)

    // Delete
    @Delete
    fun deleteEntry(entry: Book)
}