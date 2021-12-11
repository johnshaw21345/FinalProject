package my.bytecamp.finalprojectxxxx.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import my.bytecamp.finalprojectxxxx.NoteOperator
import my.bytecamp.finalprojectxxxx.R
import my.bytecamp.finalprojectxxxx.beans.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteViewHolder(@NonNull itemView: View, operator: NoteOperator) :
    RecyclerView.ViewHolder(itemView) {
    private val operator: NoteOperator
    private val contentText: TextView
    private val dateText: TextView
    private val deleteBtn: View
    private val imageView: ImageView

    fun bind(note: Note) {
        contentText.setText(note.getContent())
        dateText.setText(SIMPLE_DATE_FORMAT.format(note.date))
        deleteBtn.setOnClickListener { operator.deleteNote(note) }

        Log.i("@->", "@@" + note.photoURL)
        if(note.photoURL != null) {
            imageView.visibility = View.VISIBLE
            val bm = decodeBitmapFromFile(
                "/sdcard/Android/data/my.bytecamp.finalprojectxxxx/files/Pictures/" + note.photoURL,
                300,
                300
            )
            imageView.setImageBitmap(bm)

        }else{
            imageView.visibility = View.GONE
        }
    }

    companion object {
        private val SIMPLE_DATE_FORMAT =
            SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH)
    }

    init {
        this.operator = operator
        contentText = itemView.findViewById<TextView>(R.id.text_content)
        dateText = itemView.findViewById<TextView>(R.id.text_date)
        deleteBtn = itemView.findViewById(R.id.btn_delete)
        imageView = itemView.findViewById(R.id.image)
    }

    private fun decodeBitmapFromFile(path: String, reqWidth: Int, reqHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(path, options)
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        /**
         * todo calculate sampleSize
         */
        var ratio = 1

        while (reqWidth/ratio > 1024 || reqHeight/ratio > 1024){
            ratio *= 2
        }

        return ratio
    }

}