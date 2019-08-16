package azhengye.com.hodgepodge.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import azhengye.com.hodgepodge.R
import azhengye.com.hodgepodge.fragment.HomeFragment
import azhengye.com.hodgepodge.fragment.SecondFragment
import azhengye.com.hodgepodge.fragment.ThirdFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val HOME_FRAGMENT_INDEX = 0
        val SECOND_FRAGMENT_INDEX = 1
        val THIRD_FRAGMENT_INDEX = 2
    }

    lateinit var fragment1: Fragment
    lateinit var fragment2: Fragment
    lateinit var fragment3: Fragment
    var fragments = arrayListOf<Fragment>()
    var currentShowFragmentIndex = 0

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                showFragment(HOME_FRAGMENT_INDEX)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                showFragment(SECOND_FRAGMENT_INDEX)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                showFragment(THIRD_FRAGMENT_INDEX)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun showFragment(fragmentIndex: Int) {
        if (currentShowFragmentIndex != fragmentIndex) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.hide(fragments.get(currentShowFragmentIndex))
            if (!fragments.get(fragmentIndex).isAdded()) {
                transaction.add(R.id.fragment_container, fragments.get(fragmentIndex));
            }
            transaction.show(fragments.get(fragmentIndex)).commit()
            currentShowFragmentIndex = fragmentIndex
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        initFragments()
    }

    private fun initFragments() {
        fragment1 = HomeFragment()
        fragment2 = SecondFragment()
        fragment3 = ThirdFragment()

        fragments.add(fragment1)
        fragments.add(fragment2)
        fragments.add(fragment3)

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment1)
            .show(fragment1)
            .commit()
    }
}
