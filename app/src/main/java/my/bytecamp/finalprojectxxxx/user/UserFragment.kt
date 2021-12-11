package my.bytecamp.finalprojectxxxx.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import my.bytecamp.finalprojectxxxx.R
import my.bytecamp.finalprojectxxxx.imageselector.ImageSelectActivity

class UserFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.findViewById<View>(R.id.edit_data)?.setOnClickListener{
            val intent1 = Intent(activity,EditDataActivity::class.java)
            startActivity(intent1)
        }
        activity?.findViewById<View>(R.id.settings)?.setOnClickListener{
            val intent2 = Intent(activity,SettingsActivity::class.java)
            startActivity(intent2)
        }
        activity?.findViewById<View>(R.id.about)?.setOnClickListener{
            val intent3 = Intent(activity,AboutActivity::class.java)
            startActivity(intent3)
        }
        activity?.findViewById<ImageView>(R.id.avatar)?.setOnClickListener{
            val intent4 = Intent(activity,ImageSelectActivity::class.java)
            startActivity(intent4)
        }
    }


    companion object {
        const val TAG = "User"
    }

}