package my.bytecamp.finalprojectxxxx.imageselector

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class ImageSelectUtil {

    companion object {
        const val SELECT_PHOTO = 1
        const val CROP_PHOTO = 2

        @JvmStatic
        fun getPicturePath(context: Context, photoUri: Uri?): String? {
            photoUri?.let {
                return if (!photoUri.toString().contains("file")) {
                    UriUtil.getPath(context, photoUri)
                } else {
                    photoUri.path
                }
            }
            return null
        }

        @JvmStatic
        fun clipPhoto(context: Activity, originalUri: Uri, targetOutputFile: File) {
            var uri = originalUri
            val intent = Intent("com.android.camera.action.CROP")
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                uri = FileProvider.getUriForFile(
//                    context,
//                    BuildConfig.APPLICATION_ID + ".fileprovider",
//                    File(uri.path)
//                )
//            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.setDataAndType(uri, "image/*")
            intent.putExtra("aspectX", 1)
            intent.putExtra("aspectY", 1)
            intent.putExtra("outputX", 150)
            intent.putExtra("outputY", 150)
            intent.putExtra("crop", true)
            intent.putExtra("noFaceDetection", true)
            intent.putExtra("return-data", true)
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { //android 11以上，将文件创建在公有目录
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtils.uri)
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(targetOutputFile))
            }
            context.startActivityForResult(intent, CROP_PHOTO)
        }

        @JvmStatic
        fun preView(activity: Activity, uri: Uri) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) //注意加上这句话
            intent.setDataAndType(uri, "image/*")
            activity.startActivity(intent)
        }

        @JvmStatic
        fun generateFile() : File {
            val format = SimpleDateFormat("yyyyMMdd_HHmmss")
            val timeStamp: String = format.format(Date())
            val fileName = "tiktok_$timeStamp.png"
            val path: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { //android 11以上，创建在公有目录
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path  //storage/emulated/0/Pictures
            } else {
                Environment.getExternalStorageDirectory().absolutePath //storage/emulated/0/Android/data/com.xxxxx/cache
            }
            return File(path, fileName)
        }

    }

}