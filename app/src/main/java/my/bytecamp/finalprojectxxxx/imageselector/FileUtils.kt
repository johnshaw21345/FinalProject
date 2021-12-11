package my.bytecamp.finalprojectxxxx.imageselector

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.provider.MediaStore
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


object FileUtils {

    var uri: Uri? = null

    private fun getAppRootDirPath(context: Context): File {
        return context.getExternalFilesDir(null)!!.absoluteFile
    }

    fun createImageFile(context: Context): File? {
        return try {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            var fileName = ""
            fileName = "IMG_" + timeStamp + "_CROP.jpg"
            val rootFile = File(getAppRootDirPath(context).toString() + File.separator + "capture")
            if (!rootFile.exists()) {
                rootFile.mkdirs()
            }
            val imgFile: File
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                imgFile = File(
                    Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES)
                        .toString() + File.separator + fileName
                )
                // 通过 MediaStore API 插入file 为了拿到系统裁剪要保存到的uri（因为App没有权限不能访问公共存储空间，需要通过 MediaStore API来操作）
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, imgFile.absolutePath)
                values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            } else {
                imgFile = File(rootFile.absolutePath + File.separator + fileName)
            }
            imgFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getCropFile(context: Context, uri: Uri?): File? {
        uri?.let {
            val path = UriUtil.getPath(context, uri)
            return File(path!!)
        }
        return null
    }
}