package my.bytecamp.finalprojectxxxx.home

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import my.bytecamp.finalprojectxxxx.R
import my.bytecamp.finalprojectxxxx.db.TodoContract
import my.bytecamp.finalprojectxxxx.db.TodoDbHelper
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UploadActivity : AppCompatActivity() {
    private var editText: EditText? = null
    private var addBtn: Button? = null
    private var dbHelper: TodoDbHelper? = null
    private var database: SQLiteDatabase? = null

    private var mTakePhoto: Button? = null
    private var mImageView: ImageView? = null
    private var mCurrentPhotoPath: String? = null
    private var photofile: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)



        dbHelper = TodoDbHelper(this)
        database = dbHelper?.writableDatabase
        editText = findViewById(R.id.edit_text)
        editText?.isFocusable = true
        editText?.requestFocus()
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputManager?.showSoftInput(editText, 0)
        addBtn = findViewById(R.id.btn_add)
        addBtn!!.setOnClickListener(View.OnClickListener {
            val content: CharSequence = editText!!.text
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(
                    this@UploadActivity,
                    "No content to add", Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }
            val succeed = saveNote2Database(
                content.toString().trim { it <= ' ' }
            )
            if (succeed) {
                Toast.makeText(
                    this@UploadActivity,
                    "Note added", Toast.LENGTH_SHORT
                ).show()
                setResult(Activity.RESULT_OK)
            } else {
                Toast.makeText(
                    this@UploadActivity,
                    "Error", Toast.LENGTH_SHORT
                ).show()
            }
            finish()
        })





        mTakePhoto = findViewById(R.id.btn_photo)
        mImageView = findViewById(R.id.image_view)
        mTakePhoto?.setOnClickListener(View.OnClickListener {
            Log.i("@->","12345")
            if (ContextCompat.checkSelfPermission(
                    this@UploadActivity,
                    Manifest.permission.CAMERA

                )
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(
                    this@UploadActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                Log.i("@->","23456")
                ActivityCompat.requestPermissions(
                    this@UploadActivity,
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CAMERA_PERMISSION
                )
            } else {
                dispatchTakePictureIntent()
            }
        })



    }





    override fun onDestroy() {
        super.onDestroy()
        database?.close()
        database = null
        dbHelper?.close()
        dbHelper = null
    }

    private fun saveNote2Database(content: String): Boolean {
        if (database == null || TextUtils.isEmpty(content)) {
            return false
        }
        Log.i("@->",photofile.toString())
        val values = ContentValues()
        values.put(TodoContract.TodoNote.COLUMN_CONTENT, content)
        values.put(TodoContract.TodoNote.COLUMN_DATE, System.currentTimeMillis())
        values.put(TodoContract.TodoNote.COLUMN_PHOTOURL, photofile)
        val rowId: Long = database!!.insert(TodoContract.TodoNote.TABLE_NAME, null, values)
        return rowId != -1L
    }


    private fun dispatchTakePictureIntent() {
        Log.i("@->","34567")
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var photoFile: File? = null
        try {
            photoFile = createImageFile()
        } catch (ex: IOException) {
            // error
        }
        if (photoFile != null) {
            // 获取存储图片的URI
            val photoURI = FileProvider.getUriForFile(this,
                "my.bytecamp.finalprojectxxxx.fileprovider", photoFile
            )
            /**
             *   补充完整缺失代码 A2
             */
            Log.i("@->",photoURI.toString())
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // 获取当前时间作为文件名
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"

        // 获取应用文件存储路径 Android/data/com.bytedance.camera.demo/files/Pictures
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val image = File.createTempFile(imageFileName, ".jpeg", storageDir)
        // 保存文件路径
        photofile = image.name
        Log.i("@->",photofile.toString())
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            /**
             *   补充完整缺失代码 A1
             */




            val targetW = mImageView!!.width
            val targetH = mImageView!!.height

            val bmOptions = BitmapFactory.Options()
            bmOptions.inJustDecodeBounds = true

            BitmapFactory.decodeFile(mCurrentPhotoPath,bmOptions)
            val photoW = bmOptions.outWidth
            val photoH = bmOptions.outHeight
            var inSamplesize = 1
            if (photoH > targetH || photoW > targetW){
                val halfHeight = photoH / 2
                val halfWidth = photoW / 2

                while (halfHeight / inSamplesize >= targetH && halfWidth / inSamplesize >= targetW) {
                    inSamplesize *=2
                }
            }

            bmOptions.inJustDecodeBounds = false
            bmOptions.inSampleSize = inSamplesize
            bmOptions.inPurgeable = true

            val bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions)
            mImageView!!.setImageBitmap(bitmap)



        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            dispatchTakePictureIntent()
        }
    }


    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_CAMERA_PERMISSION = 2
    }


}