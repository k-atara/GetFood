package mx.tec.getfood.ui.QR.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main_q_r.*
import mx.tec.getfood.R

class MainQR : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_q_r)
        setViewPagerAdapter()
        setBottomNavigation()
        setViewPagerListener()
    }

    private fun setViewPagerAdapter() {
        viewPager.setOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        bottomNavigationView.selectedItemId = R.id.qrScanMenuId
                    }

//                    2 -> {
//                        bottomNavigationView.selectedItemId = R.id.favouriteScannedMenuId
//                    }
                }
            }
        })    }

    private fun setBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.qrScanMenuId -> {
                    viewPager.currentItem = 0
                }

//                R.id.favouriteScannedMenuId -> {
//                    viewPager.currentItem = 2
//                }
            }
            return@setOnNavigationItemSelectedListener true
        }    }

    private fun setViewPagerListener() {
        viewPager.adapter = MainPageAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = 2    }

}