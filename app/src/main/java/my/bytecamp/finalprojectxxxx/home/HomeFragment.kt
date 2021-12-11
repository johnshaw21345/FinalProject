package my.bytecamp.finalprojectxxxx.home

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.provider.BaseColumns
import android.view.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import my.bytecamp.finalprojectxxxx.NoteOperator
import my.bytecamp.finalprojectxxxx.R
import my.bytecamp.finalprojectxxxx.beans.Note
import my.bytecamp.finalprojectxxxx.db.TodoContract
import my.bytecamp.finalprojectxxxx.db.TodoDbHelper
import my.bytecamp.finalprojectxxxx.ui.NoteListAdapter
import java.util.*

class HomeFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var notesAdapter: NoteListAdapter? = null
    private var dbHelper: TodoDbHelper? = null
    private var database: SQLiteDatabase? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        const val TAG = "Home"
        private const val REQUEST_CODE_ADD = 1002
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.findViewById<View>(R.id.fab)?.setOnClickListener {
            val intent = Intent(activity, UploadActivity::class.java)
            startActivity(intent)
        }

        dbHelper = TodoDbHelper(activity)
        database = dbHelper!!.writableDatabase
        recyclerView = activity?.findViewById(R.id.list_todo)
        recyclerView?.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        recyclerView?.addItemDecoration(
            DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        )
        notesAdapter = NoteListAdapter(object : NoteOperator {
            override fun deleteNote(note: Note) {
                this@HomeFragment.deleteNote(note)
            }

            override fun updateNote(note: Note) {
                updateNode(note)
            }
        })
        recyclerView?.adapter = notesAdapter
        notesAdapter!!.refresh(loadNotesFromDatabase())

    }

    override fun onDestroy() {
        super.onDestroy()
        database!!.close()
        database = null
        dbHelper!!.close()
        dbHelper = null
    }

    override fun onResume() {
        super.onResume()
        notesAdapter!!.refresh(loadNotesFromDatabase())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD
            && resultCode == AppCompatActivity.RESULT_OK
        ) {
            notesAdapter!!.refresh(loadNotesFromDatabase())
        }
    }

    @SuppressLint("Range")
    private fun loadNotesFromDatabase(): List<Note> {
        if (database == null) {
            return emptyList()
        }
        val result: MutableList<Note> = LinkedList<Note>()
        var cursor: Cursor? = null
        try {
            cursor = database!!.query(
                TodoContract.TodoNote.TABLE_NAME, null,
                null, null,
                null, null, TodoContract.TodoNote.COLUMN_DATE + " DESC"
            )
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID))
                val content = cursor.getString(cursor.getColumnIndex(TodoContract.TodoNote.COLUMN_CONTENT))
                val dateMs = cursor.getLong(cursor.getColumnIndex(TodoContract.TodoNote.COLUMN_DATE))
                val photo = cursor.getString(cursor.getColumnIndex(TodoContract.TodoNote.COLUMN_PHOTOURL))
                val note = Note(id)
                note.content = content
                note.date = Date(dateMs)
                note.photoURL = photo
                result.add(note)
            }
        } finally {
            cursor?.close()
        }
        return result
    }

    private fun deleteNote(note: Note) {
        if (database == null) {
            return
        }
        val rows = database!!.delete(
            TodoContract.TodoNote.TABLE_NAME, BaseColumns._ID + "=?", arrayOf(
                (note.id).toString()
            )
        )
        if (rows > 0) {
            notesAdapter!!.refresh(loadNotesFromDatabase())
        }
    }

    private fun updateNode(note: Note) {
        if (database == null) {
            return
        }
        val values = ContentValues()
        val rows = database!!.update(
            TodoContract.TodoNote.TABLE_NAME, values, BaseColumns._ID + "=?", arrayOf(
                (note.id).toString()
            )
        )
        if (rows > 0) {
            notesAdapter!!.refresh(loadNotesFromDatabase())
        }
    }




}