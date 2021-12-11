package my.bytecamp.finalprojectxxxx.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import my.bytecamp.finalprojectxxxx.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        findViewById<TextView>(R.id.text_about).text = "小组成员:\n萧奕涵\n胡青松\n钟圳凯\n2021.12\n领养信息来自SJTU喵汪公众号"
    }
}