package mx.tec.getfood.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import mx.tec.getfood.R
import mx.tec.getfood.elemento.adapter.CustomAdapter
import mx.tec.getfood.elemento.model.Elemento

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
        /*val lista: RecyclerView = v.findViewById(R.id.lvLista)

        val datos = listOf(
            Elemento(1, "Element 1", "Descripcion 1", "$1", R.drawable.hamburguesa),
            Elemento(2, "Element 2", "Descripcion 2", "$2", R.drawable.ensalada),
            Elemento(3, "Element 3", "Descripcion 3", "$3", R.drawable.burrito),
            Elemento(3, "Element 4", "Descripcion 4", "$4", R.drawable.sandwich),
            Elemento(4, "Element 5", "Descripcion 5", "$5", R.drawable.pizza)
        )

        val adaptador = getActivity()?.let {
            CustomAdapter(
                it,
                R.layout.layout_elemento, datos, R.anim.bounce
            )
        }

        lista.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        lista.setHasFixedSize(true)
        lista.adapter=adaptador*/


        return v
    }
}