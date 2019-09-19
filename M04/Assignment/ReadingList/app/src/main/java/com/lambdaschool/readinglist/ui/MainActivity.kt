package com.lambdaschool.readinglist.ui


import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.lambdaschool.readinglist.R
import com.lambdaschool.readinglist.model.Book
import com.lambdaschool.readinglist.model.Book.Companion.createBook
import com.lambdaschool.readinglist.repo

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import timber.log.Timber.i
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    companion object {
        const val NEW_ENTRY_REQUEST = 0
        const val EDIT_ENTRY_REQUEST = 1
    }

    private var entryList = mutableListOf<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        btn_add.setOnClickListener { view ->
            val newEntryIntent = Intent(this@MainActivity, EditBookActivity::class.java)
            val entry = createBook()
            newEntryIntent.putExtra(Book.TAG, entry)
            startActivityForResult(newEntryIntent, NEW_ENTRY_REQUEST)
        }

        i("onCreate")

        ReadAllEntriesAsyncTask(this).execute()
    }

    override fun onStart() {
        super.onStart()
        i("onStart")
    }

    override fun onResume() {
        super.onResume()

        i("onResume")

        ll_list.removeAllViews()
        entryList.forEach { entry ->
            ll_list.addView(buildItemView(entry))
        }
    }

    override fun onPause() {
        super.onPause()

        i("onPause")
    }

    override fun onStop() {
        super.onStop()

        i("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        i("onDestroy")
    }


    private fun buildItemView(entry: Book): TextView {
        val view = TextView(this@MainActivity)
        view.text = entry.title
        view.setPadding(15, 15, 15, 15)
        view.textSize = 24f
        view.setOnClickListener {
            val viewEditBookIntent = Intent(this@MainActivity, EditBookActivity::class.java)
            viewEditBookIntent.putExtra(Book.TAG, entry)
            startActivityForResult(viewEditBookIntent, EDIT_ENTRY_REQUEST)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == NEW_ENTRY_REQUEST) {
                if (data != null) {
                    val entry = data.getSerializableExtra(Book.TAG) as Book
                    CreateAsyncTask(viewModel).execute(entry)
                }
            } else if (requestCode == EDIT_ENTRY_REQUEST) {
                if (data != null) {
                    val entry = data.getSerializableExtra(Book.TAG) as Book
                    UpdateAsyncTask(viewModel).execute(entry)
                }
            }
        }
    }

    // Create AsyncTask
    class CreateAsyncTask(viewModel: EntriesViewModel) : AsyncTask<Book, Void, Unit>() {
        private val viewModel = WeakReference(viewModel)
        override fun doInBackground(vararg entries: Book?) {
            if (entries.isNotEmpty()) {
                entries[0]?.let {
                    viewModel.get()?.createEntry(it)
                }
            }
        }
    }

    // Update AsyncTask
    class UpdateAsyncTask(viewMode: EntriesViewModel) : AsyncTask<Book, Void, Unit>() {
        private val viewModel = WeakReference(viewModel)
        override fun doInBackground(vararg entries: Book?) {
            if (entries.isNotEmpty()) {
                entries[0]?.let {
                    viewModel.get()?.updateEntry(it)
                }
            }
        }
    }

    // ReadAll AsyncTask
    class ReadAllEntriesAsyncTask(activity: MainActivity) : AsyncTask<Void, Void, LiveData<List<Book>>?>() {
        private val activity = WeakReference(activity)
        override fun doInBackground(vararg entries: Void?): LiveData<List<Book>>? {
            return activity.get()?.viewModel?.entries
        }

        override fun onPostExecute(result: LiveData<List<Book>>?) {
            activity.get()?.let { act ->
                result?.let { entries ->
                    entries.observe(act, Observer<List<Book>> { t ->
                        t?.let {
                            act.updateForEntries(t)
                        }
                    })
                }
            }
        }
    }
}
