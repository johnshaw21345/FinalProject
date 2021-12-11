package my.bytecamp.finalprojectxxxx.db

import android.provider.BaseColumns

object TodoContract {
    const val SQL_CREATE_NOTES = ("CREATE TABLE " + TodoNote.TABLE_NAME
            + " ( " + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TodoNote.COLUMN_DATE + " INTEGER, "
            + TodoNote.COLUMN_CONTENT + " TEXT, "
            + TodoNote.COLUMN_PHOTOURL + " TEXT)")

    object TodoNote : BaseColumns {
        const val TABLE_NAME = "note"
        const val COLUMN_DATE = "date"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_PHOTOURL = "photoURL"
    }
}