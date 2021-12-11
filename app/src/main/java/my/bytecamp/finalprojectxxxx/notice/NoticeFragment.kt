package my.bytecamp.finalprojectxxxx.notice

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import my.bytecamp.finalprojectxxxx.R
import my.bytecamp.finalprojectxxxx.imageselector.ImageSelectActivity
import my.bytecamp.finalprojectxxxx.user.AboutActivity
import my.bytecamp.finalprojectxxxx.user.EditDataActivity
import my.bytecamp.finalprojectxxxx.user.SettingsActivity

class NoticeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notice, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.findViewById<View>(R.id.notice_0)?.setOnClickListener{
            val intent = Intent(activity, Notice0Activity::class.java)
            startActivity(intent)
        }
        activity?.findViewById<View>(R.id.notice_1)?.setOnClickListener{
            val intent = Intent(activity, Notice1Activity::class.java)
            startActivity(intent)
        }
        activity?.findViewById<View>(R.id.notice_2)?.setOnClickListener{
            val intent = Intent(activity, Notice2Activity::class.java)
            startActivity(intent)
        }
        activity?.findViewById<View>(R.id.notice_3)?.setOnClickListener{
            val intent = Intent(activity, Notice3Activity::class.java)
            startActivity(intent)
        }
        activity?.findViewById<View>(R.id.notice_4)?.setOnClickListener{
            val intent = Intent(activity, Notice4Activity::class.java)
            startActivity(intent)
        }
        activity?.findViewById<View>(R.id.notice_5)?.setOnClickListener{
            val intent = Intent(activity, Notice5Activity::class.java)
            startActivity(intent)
        }
        activity?.findViewById<View>(R.id.notice_6)?.setOnClickListener{
            val intent = Intent(activity, Notice6Activity::class.java)
            startActivity(intent)
        }
        activity?.findViewById<View>(R.id.notice_7)?.setOnClickListener{
            val intent = Intent(activity, Notice7Activity::class.java)
            startActivity(intent)
        }
    }



    companion object {
        const val TAG = "Notice"
    }
}