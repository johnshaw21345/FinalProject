package my.bytecamp.finalprojectxxxx.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import my.bytecamp.finalprojectxxxx.MainActivity
import my.bytecamp.finalprojectxxxx.R

class EditDataActivity : AppCompatActivity() {
    lateinit var name_info: EditText
    lateinit var email_info: EditText
    lateinit var school_info: EditText
    lateinit var stuID_info: EditText
    lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_data)
        name_info = findViewById(R.id.name)
        email_info = findViewById(R.id.email)
        school_info = findViewById(R.id.school)
        stuID_info = findViewById(R.id.stuID)
        saveButton = findViewById(R.id.btn_save)
        saveButton.setOnClickListener{
            finish()
        }

    }

}