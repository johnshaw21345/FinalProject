package my.bytecamp.finalprojectxxxx

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import my.bytecamp.finalprojectxxxx.home.HomeFragment
import my.bytecamp.finalprojectxxxx.notice.NoticeFragment
import my.bytecamp.finalprojectxxxx.user.UserFragment

class MainActivity : AppCompatActivity() {

    private val fragmentsFactory by lazy { MainFragmentsFactory() }

    private val homeTab: View by lazy { findViewById<View>(R.id.ll_home) }
    private val noticeTab: View by lazy { findViewById<View>(R.id.ll_notice) }
    private val userTab: View by lazy { findViewById<View>(R.id.ll_user) }

    val avatarURL: String = "noooo"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        switchFragment(HomeFragment.TAG)
        setTabListener()

    }

    private fun setTabListener() {
        val fm = supportFragmentManager
        homeTab.setOnClickListener {
            switchFragment(HomeFragment.TAG)
        }

        noticeTab.setOnClickListener {
            switchFragment(NoticeFragment.TAG)
        }

        userTab.setOnClickListener {
            switchFragment(UserFragment.TAG)
        }
    }

        private fun switchFragment(tag: String) {
            val fragment = fragmentsFactory.get(supportFragmentManager, tag)
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            //获取当前所有的fragment
            val childFragments = supportFragmentManager.fragments
            //先隐藏所有的fragment
            for (childFragment in childFragments) {
                fragmentTransaction.hide(childFragment)
            }
            //没有的话就添加，有就显示
            if (fragment !in childFragments) {
                //添加
                fragmentTransaction.add(R.id.fl_container, fragment, tag)
            } else {
                fragmentTransaction.show(fragment)
            }
            fragmentTransaction.commitAllowingStateLoss()
        }

        override fun onWindowFocusChanged(hasFocus: Boolean) {
            super.onWindowFocusChanged(hasFocus)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = Color.TRANSPARENT
            }
        }

}