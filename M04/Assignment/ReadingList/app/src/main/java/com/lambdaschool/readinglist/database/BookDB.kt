package com.lambdaschool.readinglist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lambdaschool.readinglist.model.Book

// Define the Room database (abstract class with an abstract method returning the DAO)
@Database(entities = [Book::class], version = 2, exportSchema = false)
abstract class BookDB : RoomDatabase() {
    abstract fun bookDao(): BookDAO
}