package com.lambdaschool.readinglist

import android.app.Application
import com.lambdaschool.readinglist.database.BookDBRepo
import timber.log.Timber

// Change to the repo interface here
val repo: BookRepoInterface by lazy {
    App.repo!!
}

class MyDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return String.format(
            "[C:%s] [M:%s] [L:%s]",
            super.createStackElementTag(element),
            element.methodName,
            element.lineNumber
        )
    }
}

class App : Application() {

    companion object {
        var repo: BookRepoInterface? = null
    }

    override fun onCreate() {
        super.onCreate()

        // Instantiate the DB repo here
        repo = BookDBRepo(applicationContext)

        // "Timber" Library
        if (BuildConfig.DEBUG) {
            Timber.plant(MyDebugTree())
        }
    }
}