package my.bytecamp.finalprojectxxxx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText

class LoginActivity: AppCompatActivity() {
    lateinit var name_user: EditText
    lateinit var password_user: EditText
    lateinit var btn_login: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        name_user = findViewById(R.id.name_user)
        password_user = findViewById(R.id.password_user)
        btn_login = findViewById(R.id.btn_login)

        btn_login.setOnClickListener{
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        password_user.transformationMethod = (PasswordTransformationMethod.getInstance())
    }

}