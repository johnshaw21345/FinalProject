package my.bytecamp.finalprojectxxxx

import androidx.fragment.app.FragmentManager
import my.bytecamp.finalprojectxxxx.home.HomeFragment
import my.bytecamp.finalprojectxxxx.notice.NoticeFragment
import my.bytecamp.finalprojectxxxx.user.UserFragment


/**
 *  author : neo
 *  time   : 2021/06/15
 *  desc   :
 */
class MainFragmentsFactory {

    fun get(fm: FragmentManager, tag: String) =
        when(tag) {
            HomeFragment.TAG -> fm.findFragmentByTag(HomeFragment.TAG) ?: HomeFragment()
            NoticeFragment.TAG -> fm.findFragmentByTag(NoticeFragment.TAG) ?: NoticeFragment()
            UserFragment.TAG -> fm.findFragmentByTag(UserFragment.TAG) ?: UserFragment()
            else -> throw IllegalArgumentException("Illegal tag for fragment found or created.")
        }
}