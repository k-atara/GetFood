package mx.tec.getfood.ui.QR.Activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import mx.tec.getfood.ui.QR.ScannerFragment


class MainPageAdapter(var fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ScannerFragment.newInstance()
            }


//            2 -> {
//                Historial.newInstance()
//            }

            else -> {
                ScannerFragment()
            }
        }
    }

}