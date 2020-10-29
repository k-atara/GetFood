package mx.tec.getfood.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import mx.tec.getfood.R
import mx.tec.getfood.elemento.adapter.CustomAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val mContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val v = inflater.inflate(R.layout.fragment_home, container, false)

        /*val bottomNavigation: BottomNavigationView = v.findViewById(R.id.btn_nav)
        val navController = Navigation.findNavController(
            requireActivity(),
            R.id.buton_nav
        )
       val navigationController = getActivity()?.let {
            findNavController(
                it,
                R.id.buton_nav
            )
        }
        if (navController != null) {
            bottomNavigation.setupWithNavController(navController)
        }*/

        return v
    }
}