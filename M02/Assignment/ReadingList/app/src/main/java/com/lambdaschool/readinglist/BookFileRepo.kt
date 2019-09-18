package com.lambdaschool.readinglist

import android.content.Context
import android.os.Environment
import com.lambdaschool.readinglist.model.Book
import org.json.JSONException
import org.json.JSONObject
import java.io.*

// Implement the interface here
class BookFileRepo(var context: Context): BookRepoInterface {

    // createEntry implementation
    override fun createEntry(entry: Book) {
        val entryString = entry.toJsonObject()
        val filename = "bookEntry${entry.title}.json"
        writeToFile(filename, entryString.toString())
    }

    // writeToFile helper
    private fun writeToFile(filename: String, entryString: String) {
        val dir = storageDirectory
        val outputFile = File(dir, filename)

        // Open FileWriter
        var writer: FileWriter? = null
        try {
            // Write
            writer = FileWriter(outputFile)
            writer.write(entryString)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (writer != null) {
                try {
                    // Close
                    writer.close()
                } catch (e2: IOException) {
                    e2.printStackTrace()
                }
            }
        }
    }

    // Save storage directory as a member variable
    val storageDirectory: File
        get() {
            if (isExternalStorageWritable) {
                val directory = context.filesDir
                return if (!directory.exists() && !directory.mkdirs()) {
                    context.cacheDir
                } else {
                    directory
                }
            } else {
                return context.cacheDir
            }
        }

    // Check for external storage is writeable
    val isExternalStorageWritable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return state == Environment.MEDIA_MOUNTED
        }

    // readAllEntries implementation
    override fun readAllEntries(): MutableList<Book> {
        // Get fileList
        val entries = ArrayList<Book>()

        // Setup ArrayList
        // Read in files && convert to objects
        for (filename in fileList) {
            val json = readFromFile(filename)
            try {
                entries.add(Book(JSONObject(json)))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return entries
    }

    // Save fileList as a member variable
    val fileList: ArrayList<String>
        get() {
            val fileNames = arrayListOf<String>()
            val dir = storageDirectory

            val list = dir.list()
            if (list != null) {
                for (name in list) {
                    if (name.contains(".json")) {
                        fileNames.add(name)
                    }
                }
            }
            return fileNames
        }

    // readFromFile helper
    private fun readFromFile(filename: String): String {
        val inputFile = File(storageDirectory, filename)
        var readString: String? = null
        var reader: FileReader? = null
        try {
            reader = FileReader(inputFile)
            readString = reader.readText()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()

                }
            }
        }
        return readString ?: ""
    }

    override fun updateEntry(entry: Book) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteEntry(entry: Book) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}