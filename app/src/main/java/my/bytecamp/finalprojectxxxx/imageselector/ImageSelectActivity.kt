package my.bytecamp.finalprojectxxxx.imageselector

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import my.bytecamp.finalprojectxxxx.R
import kotlinx.android.synthetic.main.activity_image_selector.*
import kotlinx.android.synthetic.main.activity_image_selector.btn_close
import java.io.File

class ImageSelectActivity: AppCompatActivity()  {

    companion object {
        const val PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000011
    }

    lateinit var targetOutputCropImageFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_selector)
        checkPermission()
        btn_close.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra("path", targetOutputCropImageFile.absolutePath)
            })
            finish()
        }

    }

    private fun selectImage() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, ImageSelectUtil.SELECT_PHOTO)
        } else {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, ImageSelectUtil.SELECT_PHOTO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, resIntent)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ImageSelectUtil.SELECT_PHOTO -> {
                    targetOutputCropImageFile = FileUtils.createImageFile(this)!!
                    resIntent?.let {
                        val photoUri = it.data
                        val  bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(it.data!!))
                        if (bitmap != null) {
                            iv_image.setImageBitmap(bitmap)
                        }
                        val pathString = ImageSelectUtil.getPicturePath(this, it.data)
                        tv_image_path.text = pathString
                        ImageSelectUtil.clipPhoto(this, photoUri!!, targetOutputCropImageFile)
//                        ImageSelectUtil.preView(this, photoUri!!)
                    }
                }
                ImageSelectUtil.CROP_PHOTO -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        if (FileUtils.uri != null) {  //Android11以上通过存储的uri 查询File
                            targetOutputCropImageFile = FileUtils.getCropFile(this, FileUtils.uri)!!
                        }
                    }
                    tv_crop_image_path.text = targetOutputCropImageFile.absolutePath
                    val bitmap = BitmapFactory.decodeFile(targetOutputCropImageFile.absolutePath)
                    if (bitmap != null) {
                        iv_crop_image.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    private fun checkPermission() {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            Toast.makeText(this, R.string.sd_card_not_mounted, Toast.LENGTH_LONG).show();
            return
        }
        val hasWriteExternalPermission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (hasWriteExternalPermission == PackageManager.PERMISSION_GRANTED) {
            selectImage()
        } else {
            ActivityCompat.requestPermissions(this@ImageSelectActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),PERMISSION_WRITE_EXTERNAL_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_WRITE_EXTERNAL_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                selectImage()
            } else {
                showExceptionDialog()
            }
        }
    }

    private fun showExceptionDialog() {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(R.string.selector_hint)
            .setMessage(R.string.selector_permissions_hint)
            .setNegativeButton(R.string.selector_cancel) { dialog, _ ->
                dialog.cancel()
                finish()
            }.setPositiveButton(R.string.selector_confirm) { dialog, _ ->
                dialog.cancel()
                startAppSettings()
            }.show()
    }

    private fun startAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }

}