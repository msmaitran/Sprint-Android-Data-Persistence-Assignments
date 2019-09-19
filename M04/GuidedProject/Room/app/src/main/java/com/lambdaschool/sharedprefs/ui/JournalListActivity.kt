package com.lambdaschool.sharedprefs.ui

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lambdaschool.sharedprefs.R
import com.lambdaschool.sharedprefs.entity.Journal.Companion.createJournalEntry
import com.lambdaschool.sharedprefs.model.JournalEntry
import com.lambdaschool.sharedprefs.repo
import com.lambdaschool.sharedprefs.viewmodel.EntriesViewModel
import kotlinx.android.synthetic.main.activity_journal_list.*
import kotlinx.android.synthetic.main.content_journal_list.*
import timber.log.Timber.i
import java.lang.ref.WeakReference

class JournalListActivity : AppCompatActivity() {

    companion object {
        const val NEW_ENTRY_REQUEST = 2
        const val EDIT_ENTRY_REQUEST = 1
    }

    // TODO 27a: We don't need entryList anymore, but we do need a viewModel
    //var entryList: MutableList<JournalEntry> = ArrayList()
    lateinit var viewModel: EntriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_list)
        setSupportActionBar(toolbar)

        // TODO 27: Get a ViewModel
        viewModel = ViewModelProviders.of(this).get(EntriesViewModel::class.java)

        fab.setOnClickListener { view ->
            val intent = Intent(this@JournalListActivity, DetailsActivity::class.java)
            val entry = createJournalEntry()
            intent.putExtra(JournalEntry.TAG, entry)
            startActivityForResult(
                intent,
                NEW_ENTRY_REQUEST
            )
        }

        i("onCreate")

        // TODO 17: Replace the call here by an AsyncTask
        //entryList = repo.readAllEntries()
        ReadAllEntriesAsyncTask(this).execute()

        // TODO 28: Replace the call above by observing LiveData from the ViewModel
    }

    override fun onStart() {
        super.onStart()
        i("onStart")
    }

    override fun onResume() {
        super.onResume()

        i("onResume")

        // TODO 29: Do we need this code anymore?
    }

    // TODO 22: Extract update functionality
    private fun updateForEntries(entries: List<JournalEntry>) {
        listLayout.removeAllViews()
        entries.forEach { entry ->
            listLayout.addView(createEntryView(entry))
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

    private fun createEntryView(entry: JournalEntry): TextView {
        val view = TextView(this@JournalListActivity)

        view.text = getString(R.string.entry_label, entry.id, entry.date, entry.dayRating)

        view.setPadding(15, 15, 15, 15)
        view.textSize = 22f

        view.setOnClickListener {
            val viewDetailIntent = Intent(this@JournalListActivity, DetailsActivity::class.java)
            viewDetailIntent.putExtra(JournalEntry.TAG, entry)
            startActivityForResult(
                viewDetailIntent,
                EDIT_ENTRY_REQUEST
            )
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == NEW_ENTRY_REQUEST) {
                if (data != null) {
                    val entry = data.getSerializableExtra(JournalEntry.TAG) as JournalEntry
                    CreateAsyncTask(viewModel).execute(entry)
                    //repo.createEntry(entry) // TODO 16a: Notice the call here, replace with AsyncTask
                }
            } else if (requestCode == EDIT_ENTRY_REQUEST) {
                if (data != null) {
                    val entry = data.getSerializableExtra(JournalEntry.TAG) as JournalEntry
                    UpdateAsyncTask(viewModel).execute(entry)
                    //repo.updateEntry(entry) // TODO 16b. Notice the call here, replace with AsyncTask
                }
            }
        }
    }

    // TODO 19: Create AsyncTask
    class CreateAsyncTask(viewModel: EntriesViewModel) : AsyncTask<JournalEntry, Void, Unit>() {
        private val viewModel = WeakReference(viewModel)
        override fun doInBackground(vararg entries: JournalEntry?) {
            if (entries.isNotEmpty()) {
                entries[0]?.let {
                    viewModel.get()?.createEntry(it)
                }
            }
        }
    }
    // TODO 20: Update AsyncTask
    class UpdateAsyncTask(viewModel: EntriesViewModel) : AsyncTask<JournalEntry, Void, Unit>() {
        private val viewModel = WeakReference(viewModel)
        override fun doInBackground(vararg entries: JournalEntry?) {
            if (entries.isNotEmpty()) {
                entries[0]?.let {
                    viewModel.get()?.updateEntry(it)
                }
            }
        }
    }
    // TODO 21: ReadAll AsyncTask
    class ReadAllEntriesAsyncTask(activity: JournalListActivity) :
        AsyncTask<Void, Void, LiveData<List<JournalEntry>>?>() {

        private val activity = WeakReference(activity)

        override fun doInBackground(vararg entries: Void?): LiveData<List<JournalEntry>>? {
            return activity.get()?.viewModel?.entries
        }

        override fun onPostExecute(result: LiveData<List<JournalEntry>>?) {
            activity.get()?.let { act ->
                result?.let { entries ->
                    entries.observe(act,
                        Observer<List<JournalEntry>> { t ->
                            t?.let {
                                act.updateForEntries(t)
                            }
                        }
                    )
                }
            }
        }
    }
}
